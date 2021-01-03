package com.ef.eventservice.context.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ef.dataaccess.Insert;
import com.ef.eventservice.scheduler.MessagePacket;
import com.ef.eventservice.scheduler.PREventPublisher;
import com.ef.eventservice.scheduler.Publisher;
import com.ef.eventservice.scheduler.Subscriber;
import com.ef.eventservice.scheduler.worker.MailSenderWorker;
import com.ef.eventservice.scheduler.worker.SimpleEmailAddressProvider;
import com.ef.eventservice.scheduler.worker.Worker;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.response.Response;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
//@EnableWebMvc
//@PropertySource("classpath:controller.properties")
@ComponentScan(basePackages = { "com.ef.eventservice.controller", "com.ef.dataaccess" })
public class ServiceContextConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(ServiceContextConfig.class);

  public static final String AUTH_STRING = "6w1KiI5TVRHQcPTlvctgs2zgw1y2Lwro";

  private static final String PR_EVENT_CHANNEL_NAME = "prEventChannel";

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
  public ViewResolver configureViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");

    return viewResolver;
  }

  @Bean
  public Publisher<PREventBindingModel> prEventPublisher(@Autowired JedisPool jedisPool,
      @Autowired @Qualifier("insertPREvent") Insert<PREventBindingModel, PREvent> eventPersistor) {
    logger.info("starting subscribers");
    startSubscribers(getNewJedisInstance(jedisPool));
    logger.info("started subscribers successfully");
    logger.info("creating PREventPublisher instance");
    return new PREventPublisher(getNewJedisInstance(jedisPool), eventPersistor);

  }

  @Bean
  public String prEventChannelName() {
    return PR_EVENT_CHANNEL_NAME;
  }

  private Jedis getNewJedisInstance(JedisPool jedisPool) {
    Jedis jedis = jedisPool.getResource();
    jedis.auth(AUTH_STRING);
    return jedis;
  }

  @Bean
  public JedisPool jedisPool() {
    return new JedisPool("127.0.0.1", 41029);
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

  private void startSubscribers(final Jedis subscriberJedis) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          logger.info("Subscribing to \"commonChannel\". This thread will be blocked.");

          Subscriber subscriber = new Subscriber(workers(), p -> true);
          subscriberJedis.subscribe(subscriber, PR_EVENT_CHANNEL_NAME);
          logger.info("Subscription ended.");
        } catch (Exception e) {
          logger.error("Subscribing failed.", e);
        }
      }
    }).start();
  }

  private List<Worker<MessagePacket, Response<String>>> workers() {
    List<Worker<MessagePacket, Response<String>>> workers = new ArrayList<Worker<MessagePacket, Response<String>>>();

    MailSenderWorker mailsenderWorker = new MailSenderWorker(mailSender(), SENDER_EMAIL_ADDRESS,
        new SimpleEmailAddressProvider());

    workers.add(mailsenderWorker);
    return workers;
  }

  private JavaMailSender mailSender() {
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

    return mailSender;
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

}
