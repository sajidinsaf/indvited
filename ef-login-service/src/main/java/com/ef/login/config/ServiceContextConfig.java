package com.ef.login.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.login.service.member.LoginService;
import com.ef.login.service.validation.EmailNotNullOrEmptyValidator;
import com.ef.login.service.validation.PasswordNotNullOrEmptyValidator;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;

@Configuration
//@EnableWebMvc
//@PropertySource("classpath:controller.properties")
@ComponentScan(basePackages = { "com.ef.login", "com.ef.dataaccess" })
public class ServiceContextConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(ServiceContextConfig.class);

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();

  }

  @Bean
  public ViewResolver configureViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");

    return viewResolver;
  }

  @Bean
  public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
    List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
    converterList.add(jsonMessageConverter());
    adapter.setMessageConverters(converterList);
    return adapter;
  }

  public MappingJackson2HttpMessageConverter jsonMessageConverter() {
    return new MappingJackson2HttpMessageConverter();
  }

  @Bean
  public LoginService loginService(
      @Autowired @Qualifier("loginMember") Query<MemberLoginBindingModel, Member> loginMember) {

    List<Validator<MemberLoginBindingModel, String>> validators = memberDataValidators();
    return new LoginService(loginMember, validators);
  }

  private List<Validator<MemberLoginBindingModel, String>> memberDataValidators() {
    List<Validator<MemberLoginBindingModel, String>> validators = new ArrayList<Validator<MemberLoginBindingModel, String>>();

    validators.add(new EmailNotNullOrEmptyValidator());
    validators.add(new PasswordNotNullOrEmptyValidator());
    return validators;
  }
}
