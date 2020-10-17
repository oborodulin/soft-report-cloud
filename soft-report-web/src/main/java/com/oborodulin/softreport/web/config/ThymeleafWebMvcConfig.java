package com.oborodulin.softreport.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
//import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

//import lombok.var;
//import nz.net.ultraq.thymeleaf.LayoutDialect;
//import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingRespectLayoutTitleStrategy;

@Configuration
public class ThymeleafWebMvcConfig implements WebMvcConfigurer {

	@Bean
	@Description("Thymeleaf template resolver serving HTML 5")
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		// templateResolver.setPrefix("templates/");
		templateResolver.setPrefix("templates/themes/gentelella/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");

		return templateResolver;
	}

	/*
	 * @Bean
	 * 
	 * @Description("Thymeleaf template engine with Spring integration") public
	 * SpringTemplateEngine templateEngine() {
	 * 
	 * var templateEngine = new SpringTemplateEngine();
	 * templateEngine.setTemplateResolver(templateResolver()); //
	 * templateEngine.setDialect(new LayoutDialect(new
	 * GroupingRespectLayoutTitleStrategy()));
	 * 
	 * return templateEngine; }
	 * 
	 * @Bean
	 * 
	 * @Description("Thymeleaf view resolver") public ViewResolver viewResolver() {
	 * 
	 * var viewResolver = new ThymeleafViewResolver();
	 * 
	 * viewResolver.setTemplateEngine(templateEngine());
	 * viewResolver.setCharacterEncoding("UTF-8");
	 * 
	 * return viewResolver; }
	 * 
	 * @Bean public LayoutDialect layoutDialect() { return new LayoutDialect(new
	 * GroupingRespectLayoutTitleStrategy()); }
	 */
}
