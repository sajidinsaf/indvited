package com.ef.dataaccess.event.subscription.web;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;

public class InsertPREventScheduleSubscriptionWebTest {

  private Insert<PREventScheduleSubscriptionWebFormBindingModel, EventScheduleSubscription> insertWebSubscription;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertPREventScheduleSubscriptionWebTest.class);
    insertWebSubscription = appContext.getBean("insertPREventScheduleSubscriptionWeb", Insert.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertWebSubscriptionSuccessfully() {
    String firstName = "Gregory";
    String lastName = "Peck";
    String email = "gp@gmail.com";
    String phone = "4525253222";
    String preferredDate = "Tue 10 Jul 2085";
    String preferredTime = "1230";
    String criteria = "criterionSepcriterionId1nameValSep3234criterionSepcriterionId2nameValSep13242criterionSepcriterionId3nameValSep12";
    String address = "3234 sdfsdf";
    String city = "London";
    String gender = "M";

    // System.out.println(new
    // DateUtil().parseDateFromEventDisplayString(preferredDate));
    PREventScheduleSubscriptionWebFormBindingModel w = new PREventScheduleSubscriptionWebFormBindingModel(201,
        firstName, lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender);

    EventScheduleSubscription result = insertWebSubscription.data(w);

    assertThat(result.getId(), is(0L));
    assertThat(result.getPreferredTime(), is("1230"));
    assertThat(result.getEventScheduleId(), is(101L));
    assertThat(result.getSubscriberId(), is(-1));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigInsertPREventScheduleSubscriptionWebTest {

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