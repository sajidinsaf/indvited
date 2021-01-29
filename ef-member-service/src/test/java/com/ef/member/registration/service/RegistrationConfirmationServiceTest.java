package com.ef.member.registration.service;

import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.ef.common.EmailSender;
import com.ef.common.MapBasedContext;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.common.work.Worker;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.member.registration.model.RegistrationConfirmationMessageModel;
import com.ef.member.registration.service.worker.RegistrationConfirmationEmailSenderWorker;
import com.ef.model.member.Member;

public class RegistrationConfirmationServiceTest {
  private JdbcTemplate jdbcTemplate;

  private RegistrationConfirmationService service;

  @SuppressWarnings("resource")
  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigRegistrationConfirmationServiceTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    service = appContext.getBean(RegistrationConfirmationService.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void testSuccessfulConfirmation() {

    Response<Member> response = service.confirmMember("efjh-sdsdc-pont4");

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.OK));

    Member member = response.getResponseResult();

    assertThat(member.getFirstName(), Matchers.is("Alishba"));
    assertThat(member.getLastName(), Matchers.is("Insaf"));
    assertThat(member.getPhone(), Matchers.is("7984711178"));
    assertThat(member.getMemberType().getName(), Matchers.is("pr"));
  }

}

@Configuration
@ComponentScan("com.ef.member")
class HsqlDbConfigRegistrationConfirmationServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/registration/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberData.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberRegistrationControlData.sql").build();
  }

  @Bean
  public RegistrationConfirmationService registrationConfirmationService(
      @Autowired Insert<String, Member> confirmMember) {
    List<Validator<String, String>> validators = new ArrayList<Validator<String, String>>();
    return new RegistrationConfirmationService(confirmMember, validators, confirmEmailWorker());
  }

  public Worker<MessagePacket<RegistrationConfirmationMessageModel>, Response<String>, MapBasedContext> confirmEmailWorker() {
    return new RegistrationConfirmationEmailSenderWorker(mailSender(), mailSession(), "indvited@codeczar.co.uk");
  }

  public EmailSender<MimeMessage, String> mailSender() {
    EmailSender<MimeMessage, String> sender = new EmailSender<MimeMessage, String>() {

      @Override
      public Response<String> send(MimeMessage message) {
        return new Response<String>("success", StatusCode.OK);
      }
    };

    return sender;
  }

  private Session mailSession() {
    final String username = "indvited@codeczar.co.uk";// change accordingly
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