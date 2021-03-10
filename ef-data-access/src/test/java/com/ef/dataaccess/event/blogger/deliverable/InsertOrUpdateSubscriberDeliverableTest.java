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
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

public class InsertOrUpdateSubscriberDeliverableTest {

  private Update<SubscriberDeliverableSubmissionBindingModel, String> insertOrUpdateSubscriberDeliverable;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertOrUpdateSubscriberDeliverableTest.class);
    insertOrUpdateSubscriberDeliverable = appContext.getBean("insertOrUpdateSubscriberDeliverable", Update.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertAndUpdateDeliverable() {
    Random r = new Random();
    int eventId = r.nextInt(200) + 5;
    int deliverableId = r.nextInt(5);
    int subscriberId = r.nextInt(5000) + 100000;
    String deliverableDetail = "http://zomato.com/review/" + r.nextInt(100000);

    SubscriberDeliverableSubmissionBindingModel model = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableDetail);

    // test the insert
    String result = insertOrUpdateSubscriberDeliverable.data(model);
    assertThat(result, is(Update.METHOD_INSERT));

    String query = String.format(
        "select deliverable_detail from member_deliverable_data where event_id=%d and member_id=%d and deliverable_id=%d",
        eventId, subscriberId, deliverableId);
    String expectedDeliverableResult = jdbcTemplate.queryForObject(query, String.class);
    assertThat(expectedDeliverableResult, is(deliverableDetail));

    // test the update
    deliverableDetail = deliverableDetail + "/changeIt/" + r.nextInt(500);

    model.setDeliverableUrl(deliverableDetail);

    result = insertOrUpdateSubscriberDeliverable.data(model);
    assertThat(result, is(Update.METHOD_UPDATE));

    query = String.format(
        "select deliverable_detail from member_deliverable_data where event_id=%d and member_id=%d and deliverable_id=%d",
        eventId, subscriberId, deliverableId);

    expectedDeliverableResult = jdbcTemplate.queryForObject(query, String.class);
    assertThat(expectedDeliverableResult, is(deliverableDetail));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigInsertOrUpdateSubscriberDeliverableTest {

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