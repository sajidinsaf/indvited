package com.ef.dataaccess.event.schedule;

import static org.hamcrest.Matchers.is;
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
import com.ef.model.event.PREventSchedule;

public class QueryPREventScheduleIdListEventByEventIdTest {

  private Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryPREventScheduleListEventByIdTest.class);
    queryPREventScheduleListByEventId = appContext.getBean("queryPREventScheduleListByEventId", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRetrieveScheduleSuccessfully() throws ParseException {
//    INSERT INTO event_schedule
//    (id, event_id, start_date, end_date, days_of_the_week, publish_to_inner_circle, publish_to_my_bloggers, publish_to_all_eligible, scheduled_for_timestamp) 
//    VALUES (100,200,'2080-01-15','2080-01-30','2,3,5,6',true,true,false,'2080-01-15 00:00:00');
    List<PREventSchedule> schedules = queryPREventScheduleListByEventId.data(200);
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

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryPREventScheduleListEventByIdTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/schedule/insertEventScheduleData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}