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
import javax.servlet.http.HttpServletRequest;
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
import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;
import com.ef.model.event.PREventScheduleSubscriptionBindingModelWorkaround;
import com.ef.model.member.Member;

public class PREventScheduleSubscriptionControllerTest {

  private PREventScheduleSubscriptionController controller;

  private JdbcTemplate jdbcTemplate;

  @Mock
  private Response<Member> status;

  @Mock
  private HttpServletRequest httpServletRequest;

  @Mock
  private Member member;

  @SuppressWarnings({ "unchecked", "resource" })
  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleSubscriptionControllerTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription = appContext
        .getBean("insertPrEventScheduleSubscription", Insert.class);
    Query<Integer, List<PREvent>> queryApprovalPendingSubscriptionsByPrId = appContext
        .getBean("queryApprovalPendingSubscriptionsByPrId", Query.class);

    controller = new PREventScheduleSubscriptionController(insertPrEventScheduleSubscription,
        queryApprovalPendingSubscriptionsByPrId);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldPersistAndGetScheduleSubscriptionList() throws Exception {

    int subscriberId = new Random().nextInt(100000);
    String preferredDate1 = "Thu 15 Jan 2020-23";
    String preferredTime1 = "12:30";
    String preferredDate2 = "Fri 17 Jan 2020-23";
    String preferredTime2 = "2:30";
    String preferredDate3 = "Sat 18 Jan 2020-23";
    String preferredTime3 = "20:30";

    PREventScheduleSubscriptionBindingModelWorkaround w = new PREventScheduleSubscriptionBindingModelWorkaround(
        subscriberId, preferredDate1, preferredTime1, preferredDate2, preferredTime2, preferredDate3, preferredTime3);
    ResponseEntity<?> response = controller.addEventScheduleSubscriptions(w, httpServletRequest);

    List<EventScheduleSubscription> subscriptions = (List<EventScheduleSubscription>) response.getBody();
    assertThat(subscriptions.size(), is(3));
    assertThat(subscriptions.get(0).getScheduleSubscriptionId(), is(Long.parseLong(preferredDate1.split("-")[1])));
    assertThat(subscriptions.get(1).getSubscriberId(), is(w.getMemberId()));
    assertThat(subscriptions.get(2).getPreferredTime(), is(w.getPreferredTime3().replaceAll(":", "")));

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
