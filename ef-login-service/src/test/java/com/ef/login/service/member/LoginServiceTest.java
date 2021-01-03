package com.ef.login.service.member;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;

public class LoginServiceTest {
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberLoginBindingModel memberData;

  private LoginService loginService;

  private String email = "ashish@hotmail.com";
  private String password = "Bh1m@n1Street";

  @SuppressWarnings("resource")
  @Before
  public void setUp() throws Exception {
    initMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigLoginServiceTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    loginService = appContext.getBean("loginService", LoginService.class);
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
  public void isSuccessfulLogin() {

    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.OK));

    Member member = response.getResponseResult();

    assertThat(member.getFirstName(), Matchers.is("Ashish"));
    assertThat(member.getLastName(), Matchers.is("Kamdar"));

  }

  @Test
  public void isUnsuccessfulLoginWhenUsernameDoesntMatch() {

    when(memberData.getPassword()).thenReturn("someotherpwd" + new Random(100000));
    when(memberData.getEmail()).thenReturn(email);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("Username not found or password not matched"));

  }

  @Test
  public void isUnsuccessfulLoginWhenEmailNotFound() {

    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn("someotheremail" + new Random(500) + "a3b4.com");

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("Username not found or password not matched"));

  }

  @Test
  public void isUnsuccessfulLoginWhenEmailIsNull() {

    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(null);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("email has null or empty value"));

  }

  @Test
  public void isUnsuccessfulLoginWhenEmailIsEmpty() {

    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn("   ");

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("email has null or empty value"));

  }

  @Test
  public void isUnsuccessfulLoginWhenPasswordIsNull() {

    when(memberData.getPassword()).thenReturn(null);
    when(memberData.getEmail()).thenReturn(email);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("password has null or empty value"));

  }

  @Test
  public void isUnsuccessfulLoginWhenPasswordIsEmpty() {

    when(memberData.getPassword()).thenReturn(" ");
    when(memberData.getEmail()).thenReturn(email);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is("password has null or empty value"));

  }
}

@Configuration
@ComponentScan(basePackages = { "com.ef.login", "com.ef.dataaccess.member" })
class HsqlDbConfigLoginServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:com/ef/login/service/member/createMemberTypeTable.sql")
        .addScript("classpath:com/ef/login/service/member/createMemberTable.sql")
        .addScript("classpath:com/ef/login/service/member/createEventTypeTable.sql")
        .addScript("classpath:com/ef/login/service/member/createDomainTable.sql")
        .addScript("classpath:com/ef/login/service/member/createEventCriteriaMetaTable.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberData.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberTypeData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public LoginService registrationService(
      @Autowired @Qualifier("loginMember") Query<MemberLoginBindingModel, Member> loginMember) {
    return new LoginService(loginMember);
  }

}