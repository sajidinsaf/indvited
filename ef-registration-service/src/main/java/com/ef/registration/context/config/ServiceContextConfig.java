package com.ef.registration.context.config;

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
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.registration.service.member.RegistrationService;
import com.ef.registration.service.member.validation.UniqueValueValidator;

@Configuration
//@EnableWebMvc
//@PropertySource("classpath:controller.properties")
@ComponentScan(basePackages = { "com.ef.registration", "com.ef.dataaccess.member" })
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

//  private Session mailSession() {
//    Properties props = new Properties();
//    props.put("mail.smtp.host", "mail.codeczar.co.uk");
//    props.setProperty("mail.smtp.auth", "true");
//    props.setProperty("mail.smtp.socketFactory.port", "465");
//    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//    props.setProperty("mail.smtp.port", "465");
//    
//    Authenticator authenticator = Authenticator.
//    Session session = Session.getInstance(props, null);
//  }

  @Bean
  public RegistrationService registrationService(
      @Autowired @Qualifier("insertMember") Insert<MemberRegistrationBindingModel, Member> insertMember,
      @Qualifier("queryMemberByUsername") Query<String, Member> queryMemberByUsername,
      @Qualifier("queryMemberByEmail") Query<String, Member> queryMemberByEmail,
      @Qualifier("queryMemberByPhone") Query<String, Member> queryMemberByPhone) {

    List<Validator<MemberRegistrationBindingModel, String>> validators = memberDataValidators(queryMemberByUsername,
        queryMemberByEmail, queryMemberByPhone);
    return new RegistrationService(insertMember, validators);
  }

  private List<Validator<MemberRegistrationBindingModel, String>> memberDataValidators(
      Query<String, Member> queryMemberByUsername, Query<String, Member> queryMemberByEmail,
      Query<String, Member> queryMemberByPhone) {
    List<Validator<MemberRegistrationBindingModel, String>> validators = new ArrayList<Validator<MemberRegistrationBindingModel, String>>();

    validators.add(new UniqueValueValidator(queryMemberByUsername, queryMemberByEmail, queryMemberByPhone));

    return validators;
  }
}
