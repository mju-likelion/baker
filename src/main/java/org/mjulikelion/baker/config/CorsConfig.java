package org.mjulikelion.baker.config;

import static org.mjulikelion.baker.constant.SecurityConstant.ALL;
import static org.mjulikelion.baker.constant.SecurityConstant.ALL_PATH;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Value("${client.additional.host}")
    private List<String> clientAdditionalHost;
    @Value("${client.host}")
    private String clientHost;

    @Bean
    public CorsFilter corsFilter() {
        List<String> clientHosts = clientAdditionalHost;
        clientHosts.add(clientHost);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(clientHosts);
        config.addAllowedHeader(ALL);
        config.addAllowedMethod(ALL);
        source.registerCorsConfiguration(ALL_PATH, config);
        return new CorsFilter(source);
    }
}
