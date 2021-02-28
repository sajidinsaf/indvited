package com.ef.dataaccess.event.subscription;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;
import java.util.Random;

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
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

public class RejectPREventScheduleSubscriptionTest {

  private Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> rejectPREventScheduleSubscriptionStatus;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigRejectPREventScheduleSubscriptionTest.class);
    rejectPREventScheduleSubscriptionStatus = appContext.getBean("rejectPREventScheduleSubscriptionStatus",
        Update.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldUpdateSubscriptionAndMakeOthersRedundant() {
    int doesntMatterEventId = new Random().nextInt();
    long scheduleId = 100;
    long doesntMatterSubscriptionId = new Random().nextInt();
    int subscriberId = 242342;
    int approverId = 1000010016;

    PREventScheduleSubscriptionStatusChangeBindingModel model = new PREventScheduleSubscriptionStatusChangeBindingModel(
        doesntMatterEventId, scheduleId, doesntMatterSubscriptionId, subscriberId, approverId);

    int count = rejectPREventScheduleSubscriptionStatus.data(model);

    assertThat(count, is(4));

    int expectedUntouchedSubscriptionId = jdbcTemplate.queryForObject(
        "select count(id) from event_schedule_subscription where event_schedule_id = 102 and status_id !="
            + EventStatusMeta.KNOWN_STATUS_ID_REJECTED,
        int.class);

    assertThat(expectedUntouchedSubscriptionId, is(3));

    List<Integer> approverIds = jdbcTemplate
        .query("select approver_id from event_schedule_subscription where status_id ="
            + EventStatusMeta.KNOWN_STATUS_ID_REJECTED, (rs, rowNum) -> rs.getInt("APPROVER_ID"));

    assertThat(approverIds.size(), is(4));

    for (int expectedApproverId : approverIds) {
      assertThat(expectedApproverId, is(approverId));
    }
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigRejectPREventScheduleSubscriptionTest {

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