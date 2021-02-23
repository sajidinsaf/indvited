package com.ef.dataaccess.event.subscription;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Random;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;

public class PREventScheduleSubscriptionTest {

  private InsertPREventScheduleSubscription insertPREventScheduleSubscription;
  private JdbcTemplate jdbcTemplate;

  @Mock
  private PREventScheduleSubscriptionBindingModel prEventScheduleSubscriptionBindingModel;

  private long eventSubscriptionTimeslotId = new Random(10000000).nextLong();
  private int subscriberId = new Random(100000).nextInt();
  private String scheduleDate = "Wed 15 Jan 2020";
  private String preferredTime = "14:00";

  @SuppressWarnings({ "resource" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleSubscriptionTest.class);
    insertPREventScheduleSubscription = appContext.getBean(InsertPREventScheduleSubscription.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertScheduleSuccessfullyWithOneTimeSlotWhenIsAllDayTrue() {

    prEventScheduleSubscriptionBindingModel = new PREventScheduleSubscriptionBindingModel(eventSubscriptionTimeslotId,
        subscriberId, scheduleDate, preferredTime);

    EventScheduleSubscription result = insertPREventScheduleSubscription.data(prEventScheduleSubscriptionBindingModel);

    assertThat(result.getScheduleSubscriptionId(), is(eventSubscriptionTimeslotId));
    assertThat(result.getSubscriberId(), is(subscriberId));
    assertThat(result.getPreferredTime(), is(prEventScheduleSubscriptionBindingModel.getPreferredTime()));
    assertThat(result.getEventStatus().getId(), is(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigPREventScheduleSubscriptionTest {

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

}