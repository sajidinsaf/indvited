package com.ef.dataaccess.event.blogger.query;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRetrieveTwoScheduleSuccessfully() throws ParseException {
    List<PREvent> prEvents = queryEligibleSchedulesByBloggerProfile.data(1000010016);

    assertThat(prEvents.size(), is(1));

    PREvent prEvent = prEvents.get(0);

    List<PREventSchedule> schedules = prEvent.getSchedules();

    assertThat(schedules.size(), is(2));
    PREventSchedule schedule = schedules.get(0);
    assertThat(schedule.getId(), is(100L));
    assertThat(schedule.getEventId(), is(200));
    assertThat(schedule.getStartDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-15 00:00:00").getTime()));
    assertThat(schedule.getEndDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-30 00:00:00").getTime()));
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

    schedule = schedules.get(1);
    assertThat(schedule.getId(), is(101L));
    assertThat(schedule.getEventId(), is(200));
  }

  @Test
  public void shouldRetrieveFourSchedulesSuccessfully() throws ParseException {

    List<PREvent> prEvents = queryEligibleSchedulesByBloggerProfile.data(1000010037);
    assertThat(prEvents.size(), is(2));

    List<PREventSchedule> schedules1 = prEvents.get(0).getSchedules();
    assertThat(schedules1.size(), is(2));

    List<PREventSchedule> schedules2 = prEvents.get(1).getSchedules();
    assertThat(schedules2.size(), is(2));

    PREventSchedule schedule = schedules1.get(0);
    assertThat(schedule.getId(), is(100L));
    assertThat(schedule.getEventId(), is(200));
    assertThat(schedule.getStartDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-15 00:00:00").getTime()));
    assertThat(schedule.getEndDate().getTime(),
        is(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2080-01-30 00:00:00").getTime()));
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

    schedule = schedules1.get(1);
    assertThat(schedule.getId(), is(101L));
    assertThat(schedule.getEventId(), is(200));

    assertThat(prEvents.get(0).getMember(), notNullValue());
    assertThat(prEvents.get(0).getEventCriteria(), notNullValue());
    assertThat(prEvents.get(0).getEventCriteria().length, is(2));
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
        .addScript("classpath:com/ef/dataaccess/event/blogger/query/insertEventScheduleData.sql")
        .addScript("classpath:com/ef/dataaccess/event/blogger/query/insertEventData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertVenueData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberDataForQueryPREventListTest.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}