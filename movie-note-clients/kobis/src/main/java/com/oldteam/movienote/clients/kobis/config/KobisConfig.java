package com.oldteam.movienote.clients.kobis.config;

import com.oldteam.movienote.clients.kobis.constants.KobisConstatns;
import com.oldteam.movienote.clients.kobis.provider.KobisProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class KobisConfig {

    @Value("${kobis.key}")
    private String key;

    @Bean
    public WebClient kobisClient() {

        String baseUrl = String.format(KobisConstatns.KOBIS_API_BASE_URL, key);

        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(500 * 1024))
                .build();

    }

    @Bean
    public KobisProvider kobisProvider() {
        return new KobisProvider(kobisClient());
    }


}
