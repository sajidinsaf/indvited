package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

import com.ef.common.Context;
import com.ef.common.MapBasedContext;
import com.ef.common.Strategy;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.wrapper.PREventWrapper;

public class PREventStatusStrategyTest {

  private Strategy<Context, Void> statusStrategy;
  private JdbcTemplate jdbcTemplate;
  private EventStatusMetaCache eventStatusMetaCache;

  private EventStatusMeta created;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigPREventStatusTest.class);
    statusStrategy = appContext.getBean("prEventStatusStrategy", Strategy.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    eventStatusMetaCache = appContext.getBean(EventStatusMetaCache.class);

  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldSetStatusToTest() throws Exception {
    EventStatusMeta closed = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_CLOSED);
    EventStatusMeta readyForReview = eventStatusMetaCache
        .getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_READYFORREVIEW);
    EventStatusMeta approved = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPROVED);
    EventStatusMeta applied = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPLIED);
    created = eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_CREATED);

    test(closed);
    test(applied);
    test(readyForReview);
    test(approved);
    test(created);

  }

  private void test(EventStatusMeta statusToTest) {
    PREventSchedule schedule = new PREventSchedule();

    PREvent prEvent = new PREvent();
    prEvent.setSchedules(Arrays.asList(schedule));

    List<EventScheduleSubscription> subs = new ArrayList<EventScheduleSubscription>();
    int randomListSize = new Random().nextInt(15) + 5;
    for (int i = 0; i < randomListSize; i++) {
      subs.add(new EventScheduleSubscription(0, 0, 0, null, null, created));
    }

    subs.add(new EventScheduleSubscription(0, 0, 0, null, null, statusToTest));

    // shuffle this list to set the statusToTest at any random location in the list
    Collections.shuffle(subs);

    schedule.setSubscriptions(subs);

    PREventWrapper prEventWrapper = new PREventWrapper(prEvent);

    MapBasedContext context = new MapBasedContext();
    context.put(PREventPublisherContext.CURRENT_EVENT_WRAPPER, prEventWrapper);

    statusStrategy.apply(context);

    assertThat(prEventWrapper.getEventStatus(), is(statusToTest));
  }
}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member", "com.ef.eventservice" })
class HsqlDbConfigPREventStatusTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/insertEventStatusMeta.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}