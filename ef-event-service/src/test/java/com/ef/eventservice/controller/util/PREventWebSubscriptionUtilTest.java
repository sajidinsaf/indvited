package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventScheduleSubscriptionApp;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.PREventScheduleSubscriptionWeb;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;
import com.ef.model.member.Member;
import com.ef.model.member.MemberAddress;

public class PREventWebSubscriptionUtilTest {

  private Insert<PREventScheduleSubscriptionWebFormBindingModel, EventScheduleSubscription> insertWebSubscription;

  private PREventWebSubscriptionsUtil prWebEventScheduleUtil;
  private JdbcTemplate jdbcTemplate;

  private final String firstName = "Gregory";
  private final String lastName = "Peck";
  private final String email = "gp@gmail.com";
  private final String phone = "4525253222";
  private final String preferredDate = "Tue 10 Jul 2085";
  private final String preferredTime = "1230";
  private final String criteria = "criterionSepcriterionId1nameValSep3234criterionSepcriterionId2nameValSep13242criterionSepcriterionId3nameValSep12";
  private final String address = "3234 sdfsdf";
  private final String city = "London";
  private final String gender = "M";
  private final int statusId = 3;
  private int prId = 1000010034;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventWebSubscriptionUtilTest.class);
    prWebEventScheduleUtil = appContext.getBean(PREventWebSubscriptionsUtil.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    insertWebSubscription = appContext.getBean("insertPREventScheduleSubscriptionWeb", Insert.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldAddWebEventsToNewEvent() throws Exception {
    insertWebSubscription();
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
    prEvent.setId(343434);
    prEvent.setSchedules(new ArrayList<PREventSchedule>(Arrays.asList(schedule)));

    List<PREvent> enrichedEvents = prWebEventScheduleUtil.populateWebEvents(Arrays.asList(prEvent), prId);

    assertThat(enrichedEvents.size(), is(2));

    PREvent event1 = enrichedEvents.get(0);
    assertThat(event1.getId(), is(343434));
    assertThat(event1.getSchedules().size(), is(1));

    PREvent event2 = enrichedEvents.get(1);
    assertThat(event2.getId(), is(201));
    assertThat(event2.getSchedules().size(), is(1));
    assertThat(event2.getSchedules().get(0).getSubscriptions().size(), is(1));
    PREventScheduleSubscriptionWeb subscription = (PREventScheduleSubscriptionWeb) event2.getSchedules().get(0)
        .getSubscriptions().get(0);

    validate(subscription);
  }

  @Test
  public void shouldAddWebEventsExistingEventInList() throws Exception {
    insertWebSubscription();
    String startDateStr = "2061-02-15"; // Start date
    String endDateStr = "2061-03-07"; // End date

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = new Date(sdf.parse(startDateStr).getTime());
    Date endDate = new Date(sdf.parse(endDateStr).getTime());

    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");
    schedule.setSubscriptions(Arrays.asList(new EventScheduleSubscriptionApp(1L, 1L, 1,
        new java.sql.Date(System.currentTimeMillis()), "1300", new EventStatusMeta(1, "test", "test", "test"))));

    PREvent prEvent = new PREvent();
    prEvent.setId(201);
    prEvent.setSchedules(new ArrayList<PREventSchedule>(Arrays.asList(schedule)));

    List<PREvent> enrichedEvents = prWebEventScheduleUtil.populateWebEvents(Arrays.asList(prEvent), prId);

    assertThat(enrichedEvents.size(), is(1));

    PREvent event1 = enrichedEvents.get(0);
    assertThat(event1.getId(), is(201));
    assertThat(event1.getSchedules().size(), is(2));
    assertThat(event1.getSchedules().get(0).getSubscriptions().size(), is(1));
    assertThat(event1.getSchedules().get(1).getSubscriptions().size(), is(1));
    PREventScheduleSubscriptionWeb subscription = (PREventScheduleSubscriptionWeb) event1.getSchedules().get(1)
        .getSubscriptions().get(0);

    validate(subscription);
  }

  private void insertWebSubscription() {

    // System.out.println(new
    // DateUtil().parseDateFromEventDisplayString(preferredDate));
    PREventScheduleSubscriptionWebFormBindingModel w = new PREventScheduleSubscriptionWebFormBindingModel(201,
        firstName, lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender, statusId);

    insertWebSubscription.data(w);

  }

  private void validate(EventScheduleSubscription subscription) {
    assertThat(subscription.getId(), is(0L));
    Member subscriber = subscription.getSubscriber();
    assertThat(subscriber.getFirstName(), is(firstName));
    assertThat(subscriber.getLastName(), is(lastName));
    MemberAddress memberAddress = subscriber.getMemberAddress();
    assertThat(memberAddress.getAddressLine1(), is(address));
    assertThat(memberAddress.getCity(), is(city));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member", "com.ef.eventservice" })
class HsqlDbConfigPREventWebSubscriptionUtilTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/eventservice/controller/util/insertEventScheduleSubscriptionData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventStatusMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/web/insertEventScheduleData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberDataForQueryPREventListTest.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/insertEventData.sql")
        .addScript("com/ef/dataaccess/event/insertVenueData.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}