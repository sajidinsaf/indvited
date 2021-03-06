package com.ef.dataaccess.event.blogger.query;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

public class QueryEligibleSchedulesByBloggerProfileTest {

  private Query<Integer, List<PREvent>> queryEligibleSchedulesByBloggerProfile;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryEligibleSchedulesByBloggerProfileTest.class);
    queryEligibleSchedulesByBloggerProfile = appContext.getBean("queryEligibleSchedulesByBloggerProfile", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    insertEventScheduleData();
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRetrieveTwoScheduleSuccessfully() throws ParseException {
    List<PREvent> prEvents = queryEligibleSchedulesByBloggerProfile.data(1000010037);

    assertThat(prEvents.size(), is(2));

    PREvent prEvent = prEvents.get(0);

    List<PREventSchedule> schedules = prEvent.getSchedules();

    assertThat(schedules.size(), is(2));
    PREventSchedule schedule = schedules.get(0);
    assertThat(schedule.getId(), is(100L));
    assertThat(schedule.getEventId(), is(200));
    assertThat(schedule.getStartDate().getTime(), is(Date.valueOf((LocalDate.now().plusDays(3L))).getTime()));
    assertThat(schedule.getEndDate().getTime(), is(Date.valueOf((LocalDate.now().plusDays(7L))).getTime()));
    assertThat(schedule.isSunday(), is(false));
    assertThat(schedule.isMonday(), is(false));
    assertThat(schedule.isTuesday(), is(true));
    assertThat(schedule.isWednesday(), is(true));
    assertThat(schedule.isThursday(), is(false));
    assertThat(schedule.isFriday(), is(true));
    assertThat(schedule.isSaturday(), is(true));
    assertThat(schedule.isInnerCircle(), is(true));
    assertThat(schedule.isMyBloggers(), is(true));
    assertThat(schedule.isAllEligible(), is(false));
    assertThat(schedule.getSubscriptions().size(), is(0));

    schedule = schedules.get(1);
    assertThat(schedule.getId(), is(101L));
    assertThat(schedule.getEventId(), is(200));
    assertThat(schedule.getSubscriptions().size(), is(3));

    assertThat(prEvents.get(0).getMember(), notNullValue());
    assertThat(prEvents.get(0).getEventCriteria(), notNullValue());
    assertThat(prEvents.get(0).getEventCriteria().length, is(2));

    assertThat(prEvents.get(0).getEventCriteria()[0].getForum(), notNullValue());
    assertThat(schedule.getSubscriptions().size(), is(3));
  }

  private void insertEventScheduleData() {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String startDate1 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(3L))));
    String endDate1 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(7L))));

    String startDate2 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(5L))));
    String endDate2 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(8L))));

    String startDate3 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(3L))));
    String endDate3 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(11L))));

    String startDate4 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(10L))));
    String endDate4 = dateFormat.format(Date.valueOf((LocalDate.now().plusDays(13L))));

    String sql = "INSERT INTO event_schedule "
        + "(id, event_id, start_date, end_date, schedule_time, bloggers_per_day, days_of_the_week, publish_to_inner_circle, publish_to_my_bloggers, publish_to_all_eligible, scheduled_for_timestamp) "
        + "VALUES " + "(100,200,'" + startDate1 + "','" + endDate1
        + "','lunch only',10,'2,3,5,6',true,true,false,'2080-01-15 00:00:00')," + "(101,200,'" + startDate2 + "','"
        + endDate2 + "','1200-1600 1800-2000',7,'1,3,5,6',true,true,false,'2080-01-15 00:00:00')," + "(102,201,'"
        + startDate3 + "','" + endDate3 + "','lunch only',10,'2,3,5,6',true,true,false,'2080-01-15 00:00:00'),"
        + "(103,201,'" + startDate4 + "','" + endDate4
        + "','1200-1600 1800-2000',7,'1,3,5,6',true,true,false,'2080-01-15 00:00:00');";

    jdbcTemplate.update(sql);
  }
}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryEligibleSchedulesByBloggerProfileTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/blogger/query/insertEventCriteriaData.sql")
        .addScript("classpath:com/ef/dataaccess/event/blogger/query/insertMemberCriteriaData.sql")
        .addScript("classpath:com/ef/dataaccess/event/blogger/query/insertEventData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertVenueData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberDataForQueryPREventListTest.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaData.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertForums.sql")
        .addScript("classpath:com/ef/dataaccess/event/schedule/subscription/insertEventScheduleSubscriptionData.sql")
        .build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}