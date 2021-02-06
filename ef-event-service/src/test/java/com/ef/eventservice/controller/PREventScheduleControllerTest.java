package com.ef.eventservice.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

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
import com.ef.common.Strategy;
import com.ef.common.message.Channel;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.PREventScheduleBindingModel;
import com.ef.model.member.Member;

public class PREventScheduleControllerTest {

  private PREventScheduleController controller;

  private JdbcTemplate jdbcTemplate;

  @Mock
  private Response<Member> status;

  @Mock
  private Member member;

  private StatusCode statusCode;

  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleControllerTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    Insert<PREventScheduleBindingModel, EventScheduleResult> insertPrEventSchedule = appContext
        .getBean("insertPrEventSchedule", Insert.class);

    Strategy<PREventPublisherContext, Response<?>> prEventScheduleNowStrategy = appContext
        .getBean("prEventScheduleNowStrategy", Strategy.class);

    Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId = appContext
        .getBean("queryPREventScheduleListByEventId", Query.class);

    controller = new PREventScheduleController(insertPrEventSchedule, prEventScheduleNowStrategy,
        queryPREventScheduleListByEventId);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldPersistSchedule() {

//    when(registrationService.registerMember(memberRegistationData)).thenReturn(registrationStatus);
//    when(registrationStatus.getResponseResult()).thenReturn(member);
//    statusCode = StatusCode.OK;
//    when(registrationStatus.getStatusCode()).thenReturn(statusCode);
//
//    ResponseEntity<?> registrationResult = registrationController.registerMember(memberRegistationData);
//    assertThat((Member) registrationResult.getBody(), Matchers.is(member));
//    assertThat(registrationResult.getStatusCode(), Matchers.is(HttpStatus.OK));
//    verify(registrationService).registerMember(memberRegistationData);
  }

  @Test
  public void shouldGetScheduleList() throws Exception {
    ResponseEntity<?> response = controller.getPrEvenntScheduleList(200);

    List<PREventSchedule> schedules = (List<PREventSchedule>) response.getBody();
    assertThat(schedules.size(), is(2));
    PREventSchedule schedule = schedules.get(0);
    assertThat(schedule.getId(), is(100L));
    assertThat(schedule.getEventId(), is(200));
    assertThat(schedule.getStartDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-15 00:00:00").getTime()));
    assertThat(schedule.getEndDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-30 00:00:00").getTime()));
    assertThat(schedule.isSunday(), is(false));
    assertThat(schedule.isMonday(), is(true));
    assertThat(schedule.isTuesday(), is(true));
    assertThat(schedule.isWednesday(), is(false));
    assertThat(schedule.isThursday(), is(true));
    assertThat(schedule.isFriday(), is(true));
    assertThat(schedule.isSaturday(), is(false));
    assertThat(schedule.isInnerCircle(), is(true));
    assertThat(schedule.isMyBloggers(), is(true));
    assertThat(schedule.isAllEligible(), is(false));

    schedule = schedules.get(1);
    assertThat(schedule.getId(), is(101L));
    assertThat(schedule.getEventId(), is(200));
  }
}

@Configuration
@ComponentScan("com.ef.dataaccess,com.ef.eventservice")
class HsqlDbConfigPREventScheduleControllerTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/schedule/insertEventScheduleData.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/insertEventScheduleTimeslotData.sql").build();
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
