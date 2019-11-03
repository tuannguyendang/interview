package com.dangtuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dangtuan.utils.LocalDateConverter;

/**
 * The Class TuanNguyenDangApplication.
 * 
 * @author tuannguyen
 *
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
public class TuanNguyenDangApplication extends WebMvcConfigurerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addFormatters(org.springframework.format.FormatterRegistry)
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TuanNguyenDangApplication.class, args);
	}
	
	/**
	 * Rest template.
	 *
	 * @param builder the RestTemplateBuilder
	 * @return the rest template
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
