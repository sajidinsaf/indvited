package com.ef.dataaccess.event.subscription;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

import com.ef.dataaccess.Update;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

public class ApprovePREventScheduleSubscriptionTest {

  private Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> approvePREventScheduleSubscriptionStatus;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigApprovePREventScheduleSubscriptionTest.class);
    approvePREventScheduleSubscriptionStatus = appContext.getBean("approvePREventScheduleSubscriptionStatus",
        Update.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldUpdateSubscriptionAndMakeOthersRedundant() {
    int eventId = 200;
    long scheduleId = 100;
    long subscriptionId = 3;
    int subscriberId = 242342;
    int approverId = 1000010016;

    PREventScheduleSubscriptionStatusChangeBindingModel model = new PREventScheduleSubscriptionStatusChangeBindingModel(
        eventId, scheduleId, subscriptionId, subscriberId, approverId, EventScheduleSubscription.SUBSCRIPTION_MODE_APP);

    int count = approvePREventScheduleSubscriptionStatus.data(model);

    assertThat(count, is(7));

    long expectedApprovedSubscriptionId = jdbcTemplate.queryForObject(
        "select id from event_schedule_subscription where status_id=" + EventStatusMeta.KNOWN_STATUS_ID_APPROVED,
        Long.class);

    assertThat(expectedApprovedSubscriptionId, is(subscriptionId));

    int expectedRedundantSubscriptionCount = jdbcTemplate
        .queryForObject("select count(id) from event_schedule_subscription where status_id="
            + EventStatusMeta.KNOWN_STATUS_ID_REDUNDANT, Integer.class);

    assertThat(expectedRedundantSubscriptionCount, is(6));

    int expectedApprovedId = jdbcTemplate
        .queryForObject("select approver_id from event_schedule_subscription where status_id="
            + EventStatusMeta.KNOWN_STATUS_ID_APPROVED, Integer.class);

    assertThat(expectedApprovedId, is(approverId));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigApprovePREventScheduleSubscriptionTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/insertEventData.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/insertEventScheduleData.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/insertEventScheduleSubscriptionData.sql")
        .build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}