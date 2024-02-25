package org.mjulikelion.baker.util.security;

import static org.mjulikelion.baker.constant.EtcConstant.BLANK;
import static org.mjulikelion.baker.constant.EtcConstant.COMMA;
import static org.mjulikelion.baker.constant.SecurityConstant.AUTH;
import static org.mjulikelion.baker.constant.SecurityConstant.BEARER_WITHOUT_SPACE;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_CLAIMS_STRING_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_EXPIRED_TOKEN_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_INVALID_TOKEN_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_TOKEN_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_TOKEN_PROVIDER_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_UNKNOWN_ERROR;
import static org.mjulikelion.baker.errorcode.ErrorCode.JWT_UNSUPPORTED_TOKEN_ERROR;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.mjulikelion.baker.exception.JwtException;
import org.mjulikelion.baker.model.JwtTokenInfo;
import org.mjulikelion.baker.model.Manager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long expiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expire) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            key = Keys.hmacShaKeyFor(keyBytes);
            expiration = expire;
        } catch (Exception e) {
            throw new JwtException(JWT_TOKEN_PROVIDER_ERROR, e.getMessage());
        }
    }

    public JwtTokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(COMMA));

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + expiration);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTH, authorities)
                .setIssuedAt(new Date(now))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenInfo.builder()
                .grantType(BEARER_WITHOUT_SPACE)
                .accessToken(accessToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTH) == null) {
            throw new JwtException(JWT_TOKEN_ERROR, "권한 정보가 없는 토큰입니다.");
        }

        String authoritiesString = claims.get(AUTH).toString();

        if (StringUtils.hasText(authoritiesString)) {
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(authoritiesString.split(COMMA))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UserDetails principal = new Manager(claims.getSubject(), BLANK, authorities);
            return new UsernamePasswordAuthenticationToken(principal, BLANK, authorities);
        }
        throw new JwtException(JWT_TOKEN_ERROR, "권한 정보가 비어 있습니다.");
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtException(JWT_INVALID_TOKEN_ERROR, e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new JwtException(JWT_EXPIRED_TOKEN_ERROR, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtException(JWT_UNSUPPORTED_TOKEN_ERROR, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtException(JWT_CLAIMS_STRING_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new JwtException(JWT_UNKNOWN_ERROR, e.getMessage());
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
