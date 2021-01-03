package com.ef.member.registration.model;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.dataaccess.member.InsertMember;
import com.ef.member.registration.service.RegistrationService;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;

public class RegistrationServiceTest {
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberRegistrationBindingModel memberData;

  private RegistrationService registrationService;

  private String firstName = "fname" + new Random().nextInt(1000);
  private String lastName = "lname" + new Random().nextInt(1000);
  private String email = "em" + new Random().nextInt(1000) + "@abcd.com";
  private String username = "uname" + new Random().nextInt(1000) + "@absdcd.com";
  private String password = "passwd" + new Random().nextInt(1000) + "@asds.com";
  private String phone = new Random().nextInt(10) + "";
  private String memberTypeName = new String[] { "admin", "pr", "blogger" }[new Random().nextInt(2)];

  @SuppressWarnings("resource")
  @Before
  public void setUp() throws Exception {
    initMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigRegistrationServiceTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    registrationService = appContext.getBean(RegistrationService.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("drop table member");
    jdbcTemplate.execute("drop table member_type");
    jdbcTemplate.execute("drop table domain");
    jdbcTemplate.execute("drop table event_type");
    jdbcTemplate.execute("drop table event_criteria_meta");
  }

  @Test
  public void testScuccessfulInsert() {
    when(memberData.getFirstName()).thenReturn(firstName);
    when(memberData.getLastName()).thenReturn(lastName);
    when(memberData.getUsername()).thenReturn(username);
    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email);
    when(memberData.getPhone()).thenReturn(phone);
    when(memberData.getMemberType()).thenReturn(memberTypeName);

    Response<Member> response = registrationService.registerMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.OK));

    Member member = response.getResponseResult();

    assertThat(member.getFirstName(), Matchers.is(firstName));
    assertThat(member.getLastName(), Matchers.is(lastName));
    assertThat(member.getUsername(), Matchers.is(username));
    assertThat(member.getPhone(), Matchers.is(phone));
    assertThat(member.getMemberType().getName(), Matchers.is(memberTypeName));
  }

}

@Configuration
@ComponentScan("com.ef.member")
class HsqlDbConfigRegistrationServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:com/ef/dataaccess/registration/createDomainTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/createEventCriteriaMetaTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/createEventTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/createMemberTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/createMemberTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/insertMemberTypeData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RegistrationService registrationService(@Autowired InsertMember insertMember) {
    return new RegistrationService(insertMember);
  }
}