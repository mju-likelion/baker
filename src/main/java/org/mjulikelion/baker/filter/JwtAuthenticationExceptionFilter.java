package org.mjulikelion.baker.filter;

import static org.mjulikelion.baker.constant.SecurityConstant.CONTENT_TYPE;
import static org.mjulikelion.baker.dto.response.ResponseDto.getFromCustomException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            handleJwtAuthenticationException(response, e);
        }
    }

    private void handleJwtAuthenticationException(HttpServletResponse response, CustomException e)
            throws IOException {
        int statusCode = Integer.parseInt(e.getErrorCode().getCode().substring(0, 3));
        response.setStatus(statusCode);
        response.setContentType(CONTENT_TYPE);

        ResponseDto<Void> responseDto = getFromCustomException(e);

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(responseDto));
        writer.flush();
        writer.close();
    }
}
