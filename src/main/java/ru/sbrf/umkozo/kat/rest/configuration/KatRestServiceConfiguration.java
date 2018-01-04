package ru.sbrf.umkozo.kat.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.sbrf.umkozo.kat.rest")
public class KatRestServiceConfiguration {
	

}