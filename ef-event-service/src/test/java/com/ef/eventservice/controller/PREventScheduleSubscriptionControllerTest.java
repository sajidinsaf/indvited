package com.ef.eventservice.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.common.Context;
import com.ef.common.EmailSender;
import com.ef.common.message.Channel;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;
import com.ef.model.member.Member;

public class PREventScheduleSubscriptionControllerTest {

  private PREventScheduleSubscriptionController controller;

  private JdbcTemplate jdbcTemplate;

  @Mock
  private Response<Member> status;

  @Mock
  private Member member;

  private StatusCode statusCode;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleSubscriptionControllerTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription = appContext
        .getBean("insertPrEventScheduleSubscription", Insert.class);

    controller = new PREventScheduleSubscriptionController(insertPrEventScheduleSubscription);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldPersistAndGetScheduleSubscriptionList() throws Exception {

    long eventScheduleTimeslotId = new Random().nextInt(10000000);
    int subscriberId = new Random().nextInt(100000);
    int priority = new Random().nextInt(5);

    PREventScheduleSubscriptionBindingModel a = new PREventScheduleSubscriptionBindingModel(eventScheduleTimeslotId,
        subscriberId, priority);

    eventScheduleTimeslotId = new Random().nextInt(10000000);
    subscriberId = new Random().nextInt(100000);
    priority = new Random().nextInt(5);

    PREventScheduleSubscriptionBindingModel b = new PREventScheduleSubscriptionBindingModel(eventScheduleTimeslotId,
        subscriberId, priority);

    eventScheduleTimeslotId = new Random().nextInt(10000000);
    subscriberId = new Random().nextInt(100000);
    priority = new Random().nextInt(5);

    PREventScheduleSubscriptionBindingModel c = new PREventScheduleSubscriptionBindingModel(eventScheduleTimeslotId,
        subscriberId, priority);

    ResponseEntity<?> response = controller
        .addEventScheduleSubscriptions(new PREventScheduleSubscriptionBindingModel[] { a, b, c });

    List<EventScheduleSubscription> subscriptions = (List<EventScheduleSubscription>) response.getBody();
    assertThat(subscriptions.size(), is(3));
    assertThat(subscriptions.get(0).getEventSubscriptionTimeslotId(), is(a.getEventSubscriptionTimeslotId()));
    assertThat(subscriptions.get(1).getSubscriberId(), is(b.getSubscriberId()));
    assertThat(subscriptions.get(2).getPriority(), is(c.getPriority()));

    assertThat(subscriptions.get(0).getEventStatus().getId(), is(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));
    assertThat(subscriptions.get(1).getEventStatus().getId(), is(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));
    assertThat(subscriptions.get(2).getEventStatus().getId(), is(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));

  }
}

@Configuration
@ComponentScan("com.ef.dataaccess,com.ef.eventservice")
class HsqlDbConfigPREventScheduleSubscriptionControllerTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/insertEventStatusMeta.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
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

  @Bean
  public Channel eventSubscriptionChannel() {
    return new Channel() {

      @Override
      public Long publish(String message, Context context) {
        return (long) message.length();
      }

      @Override
      public String getName() {
        return Channel.class.getName();
      }
    };
  }

}
