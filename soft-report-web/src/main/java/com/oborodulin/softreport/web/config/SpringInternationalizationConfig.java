package com.oborodulin.softreport.web.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Класс конфигурации интернационализации приложения
 * <p>
 * Включает поддержку интернационализации. Определяет местоположение ресурсных
 * пакетов, локализацию по умолчанию, параметр изменения локализации и кодировку
 * ресурсного пакета.
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
@Configuration
public class SpringInternationalizationConfig implements WebMvcConfigurer {
	private static final String MESSAGE_SOURCE = "i18n/messages";
	private static final String DEF_LOCALE = "ru";
	private static final String LOCALE_PARAM = "lang";
	private static final String DEF_ENCODING = "UTF-8";

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// localeResolver.setDefaultLocale(Locale.US);
		localeResolver.setDefaultLocale(new Locale(DEF_LOCALE));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(LOCALE_PARAM);
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean("messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(MESSAGE_SOURCE);
		messageSource.setDefaultEncoding(DEF_ENCODING);
		return messageSource;
	}

}