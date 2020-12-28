package com.youssef.testo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.youssef.testo.config.ShortUrlConfig;

@SpringBootApplication
@EnableConfigurationProperties({ ShortUrlConfig.class})
@ComponentScan({ "com.youssef.testo.config", "com.youssef.testo.controller", "com.youssef.testo.entity",
		"com.youssef.testo.repository", "com.youssef.testo.service", "com.youssef.testo.util" }
)
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
}
