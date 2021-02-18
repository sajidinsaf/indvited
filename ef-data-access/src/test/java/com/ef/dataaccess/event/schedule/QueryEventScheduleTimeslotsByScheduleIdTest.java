package com.ef.dataaccess.event.schedule;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
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
import com.ef.model.event.EventTimeslot;

public class QueryEventScheduleTimeslotsByScheduleIdTest {

  private Query<Long, List<EventTimeslot>> queryEventScheduleTimeslotsByScheduleId;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked", "rawtypes" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryTimeslotsTest.class);
    queryEventScheduleTimeslotsByScheduleId = (Query) appContext.getBean("queryEventScheduleTimeslotsByScheduleId",
        Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  // @Test
  public void shouldRetrieveScheduleTimeslotsSuccessfully() throws ParseException {
    long scheduleId = 1001L;
    List<EventTimeslot> timeSlots = queryEventScheduleTimeslotsByScheduleId.data(scheduleId);

    assertThat(timeSlots.size(), is(3));
    long timeSlotId = 20000L;
    for (EventTimeslot timeSlot : timeSlots) {
      assertThat(timeSlot.getEventScheduleId(), is(scheduleId));
      assertThat(timeSlot.getId(), is(timeSlotId));
      ++timeSlotId;
    }

    scheduleId = 2001L;
    timeSlots = queryEventScheduleTimeslotsByScheduleId.data(scheduleId);

    assertThat(timeSlots.size(), is(2));
    timeSlotId = 30001L;
    for (EventTimeslot timeSlot : timeSlots) {
      assertThat(timeSlot.getEventScheduleId(), is(scheduleId));
      assertThat(timeSlot.getId(), is(timeSlotId));
      ++timeSlotId;
    }

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryTimeslotsTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/schedule/insertEventScheduleTimeslotData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}