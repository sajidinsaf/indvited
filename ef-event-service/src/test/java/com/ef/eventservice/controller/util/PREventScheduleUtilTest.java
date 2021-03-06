package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.wrapper.PREventScheduleForPrApprovalWrapper;
import com.ef.model.event.wrapper.PREventWrapper;

public class PREventScheduleUtilTest {

  private PREventScheduleUtil prEventScheduleUtil;
  private JdbcTemplate jdbcTemplate;
  private EventStatusMetaCache eventStatusMetaCache;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventScheduleUtilTest.class);
    prEventScheduleUtil = appContext.getBean(PREventScheduleUtil.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    eventStatusMetaCache = appContext.getBean(EventStatusMetaCache.class);
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

    List<PREvent> enrichedEvents = prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));

    List<java.util.Date> dates = enrichedEvents.get(0).getSchedules().get(0).getAvailableDates();

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

    assertThat(enrichedEvents.get(0).getAllAvailableScheduledDatesForDisplay().toString(), is(
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

    List<PREvent> enrichedEvents = prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));

    schedule = enrichedEvents.get(0).getSchedules().get(0);
    List<java.util.Date> dates = schedule.getAvailableDates();

    assertThat(dates.size(), greaterThan(3));

    assertThat(dates.contains(new java.util.Date(Date.valueOf(yesterday).getTime())), is(false));

  }

  @Test
  public void shouldSetAvailableSubscritionCount() throws Exception {

    shouldSetOnWrapper(0L, 60);
  }

  @Test
  public void shouldMinusApprovedSubscriptionCount() throws Exception {

    shouldSetOnWrapper(102L, 59);
  }

  public void shouldSetOnWrapper(long scheduleId, int expectedAvailableCount) throws Exception {

    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate plusTenDays = LocalDate.now().plusDays(10);

    Date startDate = Date.valueOf(yesterday);
    Date endDate = Date.valueOf(plusTenDays);
    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    int dayNumber = LocalDate.now().getDayOfWeek().getValue();

    // select the next three days of the week after today. So we should encounter
    // each day twice in the next 10 days.
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      ++dayNumber;
      if (dayNumber > 7) {
        dayNumber = 1;
      }
      sb.append(dayNumber).append(",");
    }

    String daysOfTheWeek = sb.toString();

    schedule.setDaysOfTheWeek(daysOfTheWeek.substring(0, daysOfTheWeek.lastIndexOf(",")));

    schedule.setBloggersPerDay(10);
    schedule.setId(scheduleId);

    EventStatusMeta created = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_CREATED);

    List<EventScheduleSubscription> subs = new ArrayList<EventScheduleSubscription>();

    int randomListSize = new Random().nextInt(15) + 5;
    for (int i = 0; i < randomListSize; i++) {
      subs.add(new EventScheduleSubscription(0, 0, 0, null, null, created));
    }

    EventStatusMeta approved = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPROVED);

    subs.add(new EventScheduleSubscription(0, 0, 0, null, null, approved));

    // shuffle this list to set the statusToTest at any random location in the list
    Collections.shuffle(subs);

    schedule.setSubscriptions(subs);

    PREvent prEvent = new PREvent();

    PREventScheduleForPrApprovalWrapper wrapper = new PREventScheduleForPrApprovalWrapper(schedule);
    prEvent.setSchedules(Arrays.asList(wrapper));

    List<PREvent> enrichedEvents = prEventScheduleUtil.populateAvailableDates(Arrays.asList(prEvent));
    prEvent = enrichedEvents.get(0);
    schedule = prEvent.getSchedules().get(0);
    List<java.util.Date> dates = schedule.getAvailableDates();

    assertThat(prEvent.getClass().getName(), is(PREventWrapper.class.getName()));

    assertThat(((PREventWrapper) prEvent).getEventStatus(), is(approved));

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
        .addScript("classpath:com/ef/eventservice/controller/util/insertEventScheduleSubscriptionData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventStatusMeta.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}