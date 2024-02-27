package org.mjulikelion.baker.config;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static org.mjulikelion.baker.constant.SecurityConstant.ACCESS_TOKEN;
import static org.mjulikelion.baker.constant.SecurityConstant.ALL_PATH;
import static org.mjulikelion.baker.constant.SecurityConstant.CONTENT_TYPE;
import static org.mjulikelion.baker.errorcode.ErrorCode.ACCESS_DENIED_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.AUTHENTICATION_REQUIRED_ERROR;
import static org.mjulikelion.baker.model.Role.ROLE_ADMIN;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.errorcode.ErrorCode;
import org.mjulikelion.baker.filter.JwtAuthenticationExceptionFilter;
import org.mjulikelion.baker.filter.JwtFilter;
import org.mjulikelion.baker.util.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    @Value("${security.permit-all.url}")
    private String[] permitAllUrl;
    @Value("${security.logout.url}")
    private String logoutUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(permitAllUrl).permitAll()
                        .requestMatchers(ALL_PATH).hasRole(ROLE_ADMIN.getRoleName())
                        .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationExceptionFilter, JwtFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, authException) -> makeResponse(response,
                                        AUTHENTICATION_REQUIRED_ERROR)))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(
                                (request, response, accessDeniedException) -> makeResponse(response,
                                        ACCESS_DENIED_ERROR)))
                .logout(logout -> {
                    logout
                            .logoutUrl(logoutUrl)
                            .logoutSuccessHandler((request, response, authentication) -> {
                                ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN, "")
                                        .maxAge(ZERO)
                                        .path("/")
                                        .build();
                                response.addHeader("Set-Cookie", cookie.toString());

                                this.makeResponse(response, HttpStatus.OK, "로그아웃 되었습니다.");
                            })
                            .invalidateHttpSession(true);
                })
                .build();
    }

    private void makeResponse(HttpServletResponse response, HttpStatus status, String message)
            throws IOException {
        String jsonResponse = new ObjectMapper().writeValueAsString(
                ResponseDto.res(status, message));
        response.setStatus(status.value());
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    private void makeResponse(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        String jsonResponse = new ObjectMapper().writeValueAsString(
                ResponseDto.res(errorCode.getCode(), errorCode.getMessage()));
        response.setStatus(Integer.parseInt(errorCode.getCode().substring(0, 3)));
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
