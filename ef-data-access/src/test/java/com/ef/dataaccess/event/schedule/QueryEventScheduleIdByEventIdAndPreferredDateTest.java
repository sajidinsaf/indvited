package com.ef.dataaccess.event.schedule;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Date;
import java.text.ParseException;

import javax.sql.DataSource;

import org.apache.commons.lang3.tuple.Pair;
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

import com.ef.common.LRPair;
import com.ef.common.util.DateUtil;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;

public class QueryEventScheduleIdByEventIdAndPreferredDateTest {

  private Query<Pair<Integer, Date>, Long> queryEventScheduleIdByEventIdAndPreferredDate;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryEventScheduleIdByEventIdAndPreferredDateTest.class);
    queryEventScheduleIdByEventIdAndPreferredDate = appContext.getBean("queryEventScheduleIdByEventIdAndPreferredDate",
        Query.class);
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

    Date date = new java.sql.Date(
        new DateUtil().parseDate("2085-07-07", DateUtil.yyyy_dash_MM_dash_dd_format).getTime());

    Long id = queryEventScheduleIdByEventIdAndPreferredDate.data(new LRPair<Integer, Date>(201, date));
    assertThat(id, is(101L));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryEventScheduleIdByEventIdAndPreferredDateTest {

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