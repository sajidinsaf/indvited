package com.ef.dataaccess.event.subscription.web;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Update;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;

public abstract class WebScheduleSubscriptionStatusTest {

  private Insert<PREventScheduleSubscriptionWebFormBindingModel, Response<EventScheduleSubscription>> insertWebSubscription;
  private Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> updateStatusBean;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigWebScheduleSubscriptionStatusTest.class);
    insertWebSubscription = appContext.getBean("insertPREventScheduleSubscriptionWeb", Insert.class);
    updateStatusBean = getUpdateBean(appContext);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRejectSubscriptionSuccessfully() {
    String firstName = "Gregory";
    String lastName = "Peck";
    String email = "gp@gmail.com";
    String phone = "4525253222";
    String preferredDate = "Tue 10 Jul 2085";
    String preferredTime = "1230";
    String criteria = "criterionSepcriterionId1nameValSep3234criterionSepcriterionId2nameValSep13242criterionSepcriterionId3nameValSep12";
    String address = "3234 sdfsdf";
    String city = "London";
    String gender = "M";
    int statusId = 3;

    // System.out.println(new
    // DateUtil().parseDateFromEventDisplayString(preferredDate));
    PREventScheduleSubscriptionWebFormBindingModel w = new PREventScheduleSubscriptionWebFormBindingModel(201,
        firstName, lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender, statusId);

    Response<EventScheduleSubscription> response = insertWebSubscription.data(w);

    assertThat(response.getStatusCode(), is(StatusCode.OK));
    EventScheduleSubscription result = response.getResponseResult();
    PREventScheduleSubscriptionStatusChangeBindingModel input = new PREventScheduleSubscriptionStatusChangeBindingModel();
    input.setApproverId(new Random().nextInt());
    input.setEventId(w.getEventId());
    input.setSubscriptionId(result.getId());
    int numberOfRowsUpdated = updateStatusBean.data(input);

    assertThat(numberOfRowsUpdated, is(1));

    int updatedStatusId = jdbcTemplate.queryForObject(
        "select status_id from event_schedule_subscription_web where event_id=" + input.getEventId(), Integer.class);

    assertThat(updatedStatusId, is(getExpectedUpdateStatus()));
  }

  protected abstract Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> getUpdateBean(
      AnnotationConfigApplicationContext appContext);

  protected abstract int getExpectedUpdateStatus();

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigWebScheduleSubscriptionStatusTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/web/insertEventScheduleData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}