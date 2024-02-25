package org.mjulikelion.baker.util.security;

import static org.mjulikelion.baker.constant.EtcConstant.WHITE_SPACE;
import static org.mjulikelion.baker.constant.SecurityConstant.BEARER;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JwtEncoder {
    public static String encodeJwtBearerToken(String token) {
        return URLEncoder.encode(BEARER + token,
                StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public static String decodeJwtBearerToken(String token) {
        return URLDecoder.decode(token, StandardCharsets.UTF_8).split(WHITE_SPACE)[1];
    }
}
