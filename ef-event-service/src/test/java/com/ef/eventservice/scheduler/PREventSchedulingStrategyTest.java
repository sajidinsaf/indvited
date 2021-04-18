package com.ef.eventservice.scheduler;

import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_SCHEDULE_PERSIST_RESULT;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

public class PREventSchedulingStrategyTest {

  private Strategy<EventServiceContext, Response<PREvent>> strategy;

  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "unchecked", "resource" })
  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventSchedulingStrategyTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);

    strategy = appContext.getBean("prEventScheduleStrategy", Strategy.class);

  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldGetScheduleList() throws Exception {
    EventServiceContext context = new EventServiceContext();

    PREventSchedule schedule = new PREventSchedule();
    schedule.setEventId(37);

    EventScheduleResult prEventScheduleResult = new EventScheduleResult(schedule, null);

    context.put(PR_EVENT_SCHEDULE_PERSIST_RESULT, prEventScheduleResult);

    strategy.apply(context);

    // PREventSchedule schedule = schedules.get(0);
    assertThat(schedule.getId(), is(0L));

  }
}

@Configuration
@ComponentScan("com.ef.dataaccess,com.ef.eventservice")
class HsqlDbConfigPREventSchedulingStrategyTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/insertEventData.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/insertEventScheduleData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertVenueData.sql").build();
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
