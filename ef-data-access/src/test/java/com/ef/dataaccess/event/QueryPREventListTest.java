package com.ef.dataaccess.event;

import static org.hamcrest.Matchers.is;
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
    System.out.println(events.get(0).getSchedules().get(0));
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
        .addScript("classpath:com/ef/dataaccess/event/insertEventScheduleDataForQueryPREventListTest.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}