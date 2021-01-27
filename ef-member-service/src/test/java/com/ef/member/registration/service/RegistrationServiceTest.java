package com.ef.member.registration.service;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.common.EmailSender;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.work.Worker;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.dataaccess.member.InsertMember;
import com.ef.member.registration.model.RegistrationPreconfirmationMessageModel;
import com.ef.member.registration.service.RegistrationService;
import com.ef.member.registration.service.worker.ConfirmEmailSenderWorker;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

public class RegistrationServiceTest {
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberRegistrationBindingModel memberData;

  private RegistrationService registrationService;

  private String firstName = "fname" + new Random().nextInt(1000);
  private String lastName = "lname" + new Random().nextInt(1000);
  private String email = "em" + new Random().nextInt(1000) + "@abcd.com";
  private String username = "uname" + new Random().nextInt(1000) + "@absdcd.com";
  private String password = "passwd" + new Random().nextInt(1000) + "@asds.com";
  private String phone = new Random().nextInt(10) + "";
  private String memberTypeName = new String[] { "admin", "pr", "blogger" }[new Random().nextInt(2)];

  @SuppressWarnings("resource")
  @Before
  public void setUp() throws Exception {
    initMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigRegistrationServiceTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    registrationService = appContext.getBean(RegistrationService.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void testScuccessfulInsert() {
    when(memberData.getFirstName()).thenReturn(firstName);
    when(memberData.getLastName()).thenReturn(lastName);
    when(memberData.getUsername()).thenReturn(username);
    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email);
    when(memberData.getPhone()).thenReturn(phone);
    when(memberData.getMemberType()).thenReturn(memberTypeName);

    Response<Member> response = registrationService.registerMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.OK));

    Member member = response.getResponseResult();

    assertThat(member.getFirstName(), Matchers.is(firstName));
    assertThat(member.getLastName(), Matchers.is(lastName));
    assertThat(member.getUsername(), Matchers.is(username));
    assertThat(member.getPhone(), Matchers.is(phone));
    assertThat(member.getMemberType().getName(), Matchers.is(memberTypeName));
  }

}

@Configuration
@ComponentScan("com.ef.member")
class HsqlDbConfigRegistrationServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/registration/insertMemberTypeData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RegistrationService registrationService(@Autowired InsertMember insertMember) {
    return new RegistrationService(insertMember, confirmEmailWorker());
  }

  public Worker<MessagePacket<RegistrationPreconfirmationMessageModel>, Response<String>> confirmEmailWorker() {
    return new ConfirmEmailSenderWorker(mailSender(), mailSession(), "indvited@codeczar.co.uk");
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