package com.ef.dataaccess.event.subscription.web.util;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;
import com.ef.model.member.MemberCriteriaData;

public class InputDataUtilTest {

  private InputDataUtil inputDataUtil;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HSqlDbConfigInputDataUtilTest.class);
    inputDataUtil = appContext.getBean(InputDataUtil.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldUpdateSubscriptionAndMakeOthersRedundant() {
    String firstName = "Gregory";
    String lastName = "Peck";
    String email = "gp@gmail.com";
    String phone = "4525253222";
    String preferredDate = "Thu 15 Jan 2020-23";
    String preferredTime = "1230";
    String criteria = "criterionSepcriterionId1nameValSep3234criterionSepcriterionId2nameValSep13242criterionSepcriterionId3nameValSep12";
    String address = "3234 sdfsdf";
    String city = "London";
    String gender = "M";

    PREventScheduleSubscriptionWebFormBindingModel w = new PREventScheduleSubscriptionWebFormBindingModel(50, firstName,
        lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender);

    List<MemberCriteriaData> result = inputDataUtil.buildMemberCriteriaList(w);

    assertThat(result.get(0).getCriteriaMetadata().getId(), is(1));
    assertThat(result.get(0).getMemberCriteriaValue(), is(3234));
    assertThat(result.get(1).getCriteriaMetadata().getId(), is(2));
    assertThat(result.get(1).getMemberCriteriaValue(), is(13242));
    assertThat(result.get(2).getCriteriaMetadata().getId(), is(3));
    assertThat(result.get(2).getMemberCriteriaValue(), is(12));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HSqlDbConfigInputDataUtilTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}