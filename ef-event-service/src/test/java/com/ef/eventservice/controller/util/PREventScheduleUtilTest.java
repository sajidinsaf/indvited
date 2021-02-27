package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.wrapper.PREventScheduleForPrApprovalWrapper;

public class PREventScheduleUtilTest {

  private PREventScheduleUtil prEventScheduleUtil;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleUtilTest.class);
    prEventScheduleUtil = appContext.getBean(PREventScheduleUtil.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldPopulateAllScheduledDatesForThreeWeeks() throws Exception {

    String startDateStr = "2061-02-15"; // Start date
    String endDateStr = "2061-03-07"; // End date

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = new Date(sdf.parse(startDateStr).getTime());
    Date endDate = new Date(sdf.parse(endDateStr).getTime());

    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");

    PREvent prEvent = new PREvent();
    prEvent.setSchedules(Arrays.asList(schedule));

    prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));

    List<java.util.Date> dates = prEvent.getSchedules().get(0).getAvailableDates();

    assertThat(dates.size(), is(9));
    assertThat(sdf.format(dates.get(0)), is("2061-02-15"));
    assertThat(sdf.format(dates.get(1)), is("2061-02-17"));
    assertThat(sdf.format(dates.get(2)), is("2061-02-20"));
    assertThat(sdf.format(dates.get(3)), is("2061-02-22"));
    assertThat(sdf.format(dates.get(4)), is("2061-02-24"));
    assertThat(sdf.format(dates.get(5)), is("2061-02-27"));
    assertThat(sdf.format(dates.get(6)), is("2061-03-01"));
    assertThat(sdf.format(dates.get(7)), is("2061-03-03"));
    assertThat(sdf.format(dates.get(8)), is("2061-03-06"));

    assertThat(prEvent.getAllAvailableScheduledDatesForDisplay().toString(), is(
        "[AvailableScheduledDate [date=Tue 15 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Thu 17 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Sun 20 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Tue 22 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Thu 24 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Sun 27 Feb 2061, scheduleId=0], AvailableScheduledDate [date=Tue 1 Mar 2061, scheduleId=0], AvailableScheduledDate [date=Thu 3 Mar 2061, scheduleId=0], AvailableScheduledDate [date=Sun 6 Mar 2061, scheduleId=0]]"));

  }

  @Test
  public void shouldPopulateAllScheduledDatesAndIgnoreOlderDate() throws Exception {

    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate plusTenDays = LocalDate.now().plusDays(10);

    Date startDate = Date.valueOf(yesterday);
    Date endDate = Date.valueOf(plusTenDays);
    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");

    PREvent prEvent = new PREvent();
    prEvent.setSchedules(Arrays.asList(schedule));

    prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));

    schedule = prEvent.getSchedules().get(0);
    List<java.util.Date> dates = schedule.getAvailableDates();

    assertThat(dates.size(), greaterThan(3));

    assertThat(dates.contains(new java.util.Date(Date.valueOf(yesterday).getTime())), is(false));

  }

  @Test
  public void shouldSetAvailableSubscritionCount() throws Exception {

    shouldSetOnWrapper(0L, 50);
  }

  @Test
  public void shouldMinusApprovedSubscriptionCount() throws Exception {

    shouldSetOnWrapper(102L, 49);
  }

  public void shouldSetOnWrapper(long scheduleId, int expectedAvailableCount) throws Exception {

    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate plusTenDays = LocalDate.now().plusDays(10);

    Date startDate = Date.valueOf(yesterday);
    Date endDate = Date.valueOf(plusTenDays);
    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");
    schedule.setBloggersPerDay(10);
    schedule.setId(scheduleId);

    PREvent prEvent = new PREvent();

    PREventScheduleForPrApprovalWrapper wrapper = new PREventScheduleForPrApprovalWrapper(schedule);
    prEvent.setSchedules(Arrays.asList(wrapper));

    prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));

    schedule = prEvent.getSchedules().get(0);
    List<java.util.Date> dates = schedule.getAvailableDates();

    assertThat(dates.size(), greaterThan(3));

    assertThat(dates.contains(new java.util.Date(Date.valueOf(yesterday).getTime())), is(false));
    assertThat(wrapper.getAvailableSubscriptions(), is(expectedAvailableCount));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member", "com.ef.eventservice" })
class HsqlDbConfigPREventScheduleUtilTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/eventservice/controller/util/insertEventScheduleSubscriptionData.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}