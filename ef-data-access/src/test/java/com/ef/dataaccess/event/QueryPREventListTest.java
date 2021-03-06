package com.ef.dataaccess.event;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
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

public class QueryPREventListTest {

  private Query<Integer, List<PREvent>> queryPREventList;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryPREventListTest.class);
    queryPREventList = appContext.getBean("queryPREventList", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRetrieveScheduleTimeslotsSuccessfully() throws ParseException {
    int prId = 1000010034;
    List<PREvent> events = queryPREventList.data(prId);
    assertThat(events.size(), is(2));
    assertThat(events.get(0).getEventVenue().getName(), is("Esora"));
    assertThat(events.get(0).getSchedules().size(), is(2));
    assertThat(events.get(1).getSchedules().size(), is(0));
    assertThat(events.get(0).getEventType(), notNullValue());
    assertThat(events.get(0).getEventType().getId(), is(events.get(0).getEventTypeId()));
    assertThat(events.get(0).getEventDeliverables(), notNullValue());
    assertThat(events.get(0).getEventDeliverables().size(), is(2));
    assertThat(events.get(0).getEventDeliverables().get(0).getEventId(), is(events.get(0).getId()));
    assertThat(events.get(0).getEventDeliverables().get(1).getEventId(), is(events.get(0).getId()));
    assertThat(events.get(0).getEventDeliverables().get(0).getDeliverableName(), is("Zomato Review"));
    assertThat(events.get(0).getEventDeliverables().get(1).getDeliverableName(), is("Live Instagram Stories"));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryPREventListTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/insertEventData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertVenueData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventScheduleDataForQueryPREventListTest.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventDeliverableMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventDeliverableData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberDataForQueryPREventListTest.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}