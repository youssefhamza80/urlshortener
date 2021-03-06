package com.youssef.testo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.youssef.testo.config.ShortUrlAppConfig;

@SpringBootApplication
@EnableConfigurationProperties({ ShortUrlAppConfig.class})
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
}
