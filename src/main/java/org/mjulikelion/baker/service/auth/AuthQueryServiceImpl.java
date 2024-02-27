package org.mjulikelion.baker.service.auth;

import static org.mjulikelion.baker.constant.SecurityConstant.ACCESS_TOKEN;
import static org.mjulikelion.baker.constant.SecurityConstant.ROOT_PATH;
import static org.mjulikelion.baker.errorcode.ErrorCode.AUTHENTICATION_ERROR;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.mjulikelion.baker.dto.request.auth.AuthLoginRequestDto;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.exception.AuthenticationException;
import org.mjulikelion.baker.util.security.JwtEncoder;
import org.mjulikelion.baker.util.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthQueryServiceImpl implements AuthQueryService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final int cookieMaxAge;

    public AuthQueryServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder,
                                JwtTokenProvider jwtTokenProvider,
                                @Value("${security.jwt.cookie.max-age}") int cookieMaxAge) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieMaxAge = cookieMaxAge;
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> login(AuthLoginRequestDto authLoginRequestDTO,
                                                   HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authLoginRequestDTO.getManagerId(),
                    authLoginRequestDTO.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            String jwtToken = jwtTokenProvider.generateToken(authentication).getAccessToken();

            ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN, JwtEncoder.encodeJwtBearerToken(jwtToken))
                    .secure(true)
                    .sameSite(String.valueOf(Cookie.SameSite.LAX))
                    .maxAge(Duration.ofMinutes(cookieMaxAge))
                    .httpOnly(true)
                    .path(ROOT_PATH)
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
        } catch (Exception e) {
            throw new AuthenticationException(AUTHENTICATION_ERROR, e.getMessage());
        }
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "OK", null), HttpStatus.OK);
    }
}