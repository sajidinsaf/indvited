package com.ef.dataaccess.event.blogger.deliverable;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

public class CloseSubscriptionOnDeliverableApprovalTest {

  private Update<SubscriberDeliverableSubmissionBindingModel, String> closeSubscriptionOnDeliverableApproval;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigApproveDeliverableAndCloseTest.class);
    closeSubscriptionOnDeliverableApproval = appContext.getBean("closeSubscriptionOnDeliverableApproval",
        Update.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldCloseSubscriptionWhenStatusApproved() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String deliverableDetail = "http://zomato.com/review/" + r.nextInt(100000);
    int status = EventStatusMeta.KNOWN_STATUS_ID_APPROVED;

    setupTables(eventId, subscriberId, status);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableDetail);

    // test the insert
    String result = closeSubscriptionOnDeliverableApproval.data(model);
    assertThat(result, is(Update.METHOD_UPDATE));

    String query = String.format("select status_id from event_schedule_subscription where id=%d", 100);

    int actualStatus = jdbcTemplate.queryForObject(query, Integer.class);
    assertThat(actualStatus, is(EventStatusMeta.KNOWN_STATUS_ID_CLOSED));
  }

  @Test
  public void shouldCloseSubscriptionWhenStatusDeliverbaleUploaded() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String deliverableDetail = "http://zomato.com/review/" + r.nextInt(100000);
    int status = EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED;

    setupTables(eventId, subscriberId, status);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableDetail);

    // test the insert
    String result = closeSubscriptionOnDeliverableApproval.data(model);
    assertThat(result, is(Update.METHOD_UPDATE));

    String query = String.format("select status_id from event_schedule_subscription where id=%d", 100);

    int actualStatus = jdbcTemplate.queryForObject(query, Integer.class);
    assertThat(actualStatus, is(EventStatusMeta.KNOWN_STATUS_ID_CLOSED));
  }

  @Test
  public void shouldCloseSubscriptionWhenStatusDeliverbaleRejected() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String deliverableDetail = "http://zomato.com/review/" + r.nextInt(100000);
    int status = EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED;

    setupTables(eventId, subscriberId, status);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableDetail);

    // test the insert
    String result = closeSubscriptionOnDeliverableApproval.data(model);
    assertThat(result, is(Update.METHOD_UPDATE));

    String query = String.format("select status_id from event_schedule_subscription where id=%d", 100);

    int actualStatus = jdbcTemplate.queryForObject(query, Integer.class);
    assertThat(actualStatus, is(EventStatusMeta.KNOWN_STATUS_ID_CLOSED));
  }

  @Test
  public void shouldCloseSubscriptionWhenStatusNotApprovedDeliverbaleUploadedOrRejected() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String deliverableDetail = "http://zomato.com/review/" + r.nextInt(100000);
    int status = EventStatusMeta.KNOWN_STATUS_ID_APPLIED;

    setupTables(eventId, subscriberId, status);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableDetail);

    // test the insert
    String result = closeSubscriptionOnDeliverableApproval.data(model);
    assertThat(result, is(CloseSubscriptionOnDeliverableApproval.NO_MATCHING_SUBSRIPTION + model));

    String query = String.format("select status_id from event_schedule_subscription where id=%d", 100);

    int actualStatus = jdbcTemplate.queryForObject(query, Integer.class);
    assertThat(actualStatus, is(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));
  }

  private void setupTables(int eventId, int subscriberId, int status) {
//   private final String QUERY_SUBSCRIPTION_ID = "select ess.id from event_schedule_subscription ess, event e, event_schedule es where es.event_id=%d and ess.event_schedule_id=es.id and ess.status_id in (%d, %d) and ess.subscriber_id=%d";

    jdbcTemplate.update(String.format("insert into event_schedule (id, event_id) values (1, %d)", eventId));

    jdbcTemplate.update(String.format(
        "insert into event_schedule_subscription (id, event_schedule_id, status_id, subscriber_id) values (100, 1, %d, %d)",
        status, subscriberId));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigApproveDeliverableAndCloseTest {

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