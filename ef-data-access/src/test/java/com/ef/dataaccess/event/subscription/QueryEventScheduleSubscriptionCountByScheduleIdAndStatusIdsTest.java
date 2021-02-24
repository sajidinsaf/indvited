package com.ef.dataaccess.event.subscription;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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
import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;

public class QueryEventScheduleSubscriptionCountByScheduleIdAndStatusIdsTest {

  private Query<Pair<Long, int[]>, Integer> queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryEventScheduleSubscriptionCountByScheduleIdAndStatusIdsTest.class);
    queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds = appContext
        .getBean("queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldReturnCountSuccessfully() {

    int count = queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds
        .data(new LRPair<Long, int[]>(100L, new int[] { 3, 4, 5 }));

    assertThat(count, is(4));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryEventScheduleSubscriptionCountByScheduleIdAndStatusIdsTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder).addScript(
        "classpath:com/ef/dataaccess/event/schedule/subscription/insertEventScheduleSubscriptionDataForQueryEventScheduleCount.sql")
        .build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}