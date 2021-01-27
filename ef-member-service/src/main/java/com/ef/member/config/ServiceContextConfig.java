package com.ef.member.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ef.common.EmailSender;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.validation.Validator;
import com.ef.common.work.Worker;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.member.login.service.LoginService;
import com.ef.member.login.service.TokenAuthService;
import com.ef.member.login.service.validation.EmailAndMemberTypeCombinationValidator;
import com.ef.member.login.service.validation.EmailNotNullOrEmptyValidator;
import com.ef.member.login.service.validation.MemberTokenAuthBindingModelPasswordValidator;
import com.ef.member.login.service.validation.PasswordNotNullOrEmptyValidator;
import com.ef.member.registration.model.RegistrationConfirmationMessageModel;
import com.ef.member.registration.service.RegistrationConfirmationService;
import com.ef.member.registration.service.RegistrationService;
import com.ef.member.registration.service.validation.MemberRegistrationBindingModelPasswordValidator;
import com.ef.member.registration.service.validation.UniqueValueValidator;
import com.ef.member.registration.service.worker.ConfirmEmailSenderWorker;
import com.ef.member.registration.service.worker.JavaMailEmailSender;
import com.ef.member.registration.service.worker.RegistrationConfirmationEmailSenderWorker;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.MemberTokenAuthBindingModel;
import com.ef.model.member.PreconfirmationMemberRegistrationModel;

@Configuration
//@EnableWebMvc
//@PropertySource("classpath:controller.properties")
@ComponentScan(basePackages = { "com.ef.member", "com.ef.dataaccess" })
public class ServiceContextConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(ServiceContextConfig.class);
  private static final String SENDER_EMAIL_ADDRESS = "indvited@codeczar.co.uk";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();

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
  public ViewResolver configureViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");

    return viewResolver;
  }

  @Bean
  public LoginService loginService(
      @Autowired @Qualifier("loginMember") Query<MemberLoginBindingModel, Member> loginMember,
      @Autowired @Qualifier("queryMemberIdByEmailAndMemberType") Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType) {

    List<Validator<MemberLoginBindingModel, String>> validators = loginDataValidators(
        queryMemberIdByEmailAndMemberType);
    return new LoginService(loginMember, validators);
  }

  @Bean
  public TokenAuthService tokenAuthService(
      @Autowired @Qualifier("loginMemberAuthToken") Query<MemberTokenAuthBindingModel, Member> loginMember) {

    List<Validator<MemberTokenAuthBindingModel, String>> validators = authTokenDataValidators();
    return new TokenAuthService(loginMember, validators);
  }

  private List<Validator<MemberLoginBindingModel, String>> loginDataValidators(
      Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType) {
    List<Validator<MemberLoginBindingModel, String>> validators = new ArrayList<Validator<MemberLoginBindingModel, String>>();

    validators.add(new EmailNotNullOrEmptyValidator());
    validators.add(new PasswordNotNullOrEmptyValidator());
    validators.add(new EmailAndMemberTypeCombinationValidator(queryMemberIdByEmailAndMemberType));
    return validators;
  }

  private List<Validator<MemberTokenAuthBindingModel, String>> authTokenDataValidators() {
    List<Validator<MemberTokenAuthBindingModel, String>> validators = new ArrayList<Validator<MemberTokenAuthBindingModel, String>>();

//    validators.add(new EmailNotNullOrEmptyValidator());
    validators.add(new MemberTokenAuthBindingModelPasswordValidator());
    return validators;
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
      @Autowired @Qualifier("insertMember") Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> insertMember,
      @Qualifier("queryMemberByUsername") Query<String, Member> queryMemberByUsername,
      @Qualifier("queryMemberByEmail") Query<String, Member> queryMemberByEmail,
      @Qualifier("queryMemberByPhone") Query<String, Member> queryMemberByPhone) {

    List<Validator<MemberRegistrationBindingModel, String>> validators = registrationDataValidators(
        queryMemberByUsername, queryMemberByEmail, queryMemberByPhone);
    return new RegistrationService(insertMember, validators, confirmEmailSenderWorker());
  }

  @Bean
  public RegistrationConfirmationService registrationConfirmationService(
      @Autowired @Qualifier("confirmMemberRegistration") Insert<String, Member> confirmMember) {

    List<Validator<String, String>> validators = new ArrayList<Validator<String, String>>();
    return new RegistrationConfirmationService(confirmMember, validators, registrationConfirmationEmailSenderWorker());
  }

  private Worker<MessagePacket<RegistrationConfirmationMessageModel>, Response<String>> registrationConfirmationEmailSenderWorker() {
    RegistrationConfirmationEmailSenderWorker worker = new RegistrationConfirmationEmailSenderWorker(mailSender(),
        mailSession(), SENDER_EMAIL_ADDRESS);
    return worker;
  }

  private ConfirmEmailSenderWorker confirmEmailSenderWorker() {
    ConfirmEmailSenderWorker worker = new ConfirmEmailSenderWorker(mailSender(), mailSession(), SENDER_EMAIL_ADDRESS);
    return worker;
  }

  private List<Validator<MemberRegistrationBindingModel, String>> registrationDataValidators(
      Query<String, Member> queryMemberByUsername, Query<String, Member> queryMemberByEmail,
      Query<String, Member> queryMemberByPhone) {
    List<Validator<MemberRegistrationBindingModel, String>> validators = new ArrayList<Validator<MemberRegistrationBindingModel, String>>();

    validators.add(new UniqueValueValidator(queryMemberByUsername, queryMemberByEmail, queryMemberByPhone));
    validators.add(new MemberRegistrationBindingModelPasswordValidator());

    return validators;
  }

  private EmailSender<MimeMessage, String> mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("mail.codeczar.co.uk");
    // mailSender.setPort(2525);
    mailSender.setUsername(SENDER_EMAIL_ADDRESS);
    mailSender.setPassword("@SilverGun95@");
    Properties props = new Properties();
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.smtp.socketFactory.port", "465");
    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.setProperty("mail.smtp.port", "465");
    // props.setProperty("mail.from", SENDER_EMAIL_ADDRESS);
    mailSender.setJavaMailProperties(props);

    return new JavaMailEmailSender(mailSender);
  }

  private Session mailSession() {
    final String username = SENDER_EMAIL_ADDRESS;// change accordingly
    final String password = "@SilverGun95@";// change accordingly

    // Assuming you are sending email through relay.jangosmtp.net
    String host = "mail.codeczar.co.uk";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "465");

    // Get the Session object.
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    return session;
  }
}
