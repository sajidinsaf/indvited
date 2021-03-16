package com.ef.dataaccess.event.blogger.deliverable;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.sql.Timestamp;
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

import com.ef.dataaccess.Update;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

public class InsertDeliverableRejectionAndUpdateSubscriptionTest {

  private Update<SubscriberDeliverableSubmissionBindingModel, String> insertDeliverableRejectionAndUpdateSubscription;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertDeliverableRejectionAndUpdateSubscriptionTest.class);
    insertDeliverableRejectionAndUpdateSubscription = appContext
        .getBean("insertDeliverableRejectionAndUpdateSubscription", Update.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertRejectionReasonAndUpdateSubscriptionStatus() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String comment = " URL not found: http://zomato.com/review/" + r.nextInt(100000);

    setupTables(eventId, subscriberId);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, null, comment);

    // test the insert
    String result = insertDeliverableRejectionAndUpdateSubscription.data(model);
    assertThat(result, is(Update.METHOD_INSERT));

    String query = String.format(
        "select rejection_comment from member_deliverable_data_rejection where event_id=%d and member_id=%d", eventId,
        subscriberId);
    String expectedComment = jdbcTemplate.queryForObject(query, String.class);
    assertThat(expectedComment, is(comment));

    query = String.format(
        "select created_timestamp from member_deliverable_data_rejection where event_id=%d and member_id=%d", eventId,
        subscriberId);

    Timestamp timeStamp = jdbcTemplate.queryForObject(query, Timestamp.class);
    assertThat(timeStamp, notNullValue());

    query = String.format("select status_id from event_schedule_subscription where id=%d", 100);

    int actualStatus = jdbcTemplate.queryForObject(query, Integer.class);
    assertThat(actualStatus, is(EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED));
  }

  private void setupTables(int eventId, int subscriberId) {
    jdbcTemplate.update(String.format("insert into event_schedule (id, event_id) values (1, %d)", eventId));

    jdbcTemplate.update(String.format(
        "insert into event_schedule_subscription (id, event_schedule_id, status_id, subscriber_id) values (100, 1, %d, %d)",
        EventStatusMeta.KNOWN_STATUS_ID_APPROVED, subscriberId));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigInsertDeliverableRejectionAndUpdateSubscriptionTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder).build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}