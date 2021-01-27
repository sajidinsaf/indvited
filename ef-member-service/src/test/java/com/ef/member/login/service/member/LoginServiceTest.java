package com.ef.member.login.service.member;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.AfterThrowing;
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

import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.member.login.service.LoginService;
import com.ef.member.login.service.validation.EmailNotNullOrEmptyValidator;
import com.ef.member.login.service.validation.PasswordNotNullOrEmptyValidator;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;

public class LoginServiceTest {
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberLoginBindingModel memberData;

  private LoginService loginService;

  private String email = "ashish@hotmail.com";
  private String email_sajid = "sajidinsaf@hotmail.com";
  private String password = "Bh1m@n1Street";

  @SuppressWarnings("resource")
  @Before
  public void setUp() throws Exception {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigLoginServiceTest.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    loginService = appContext.getBean("loginService", LoginService.class);
  }

  @After
  @AfterThrowing
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
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

    assertThat(errors.get(0), Matchers.is(LoginService.CREDENTIALS_INVALID_MESSAGE));

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

    assertThat(errors.get(0), Matchers.is(LoginService.CREDENTIALS_INVALID_MESSAGE));

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

  @Test
  public void isUnsuccessfulLoginWhenMemberNotEnaled() {

    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email_sajid);

    Response<Member> response = loginService.loginMember(memberData);

    StatusCode statusCode = response.getStatusCode();

    assertThat(statusCode, Matchers.is(StatusCode.PRECONDITION_FAILED));

    assertThat(response.getResponseResult(), Matchers.nullValue());

    List<String> errors = response.getFailureReasons();

    assertThat(errors.get(0), Matchers.is(LoginService.CREDENTIALS_INVALID_MESSAGE));

  }
}

@Configuration
@ComponentScan(basePackages = { "com.ef.member", "com.ef.dataaccess.member" })
class HsqlDbConfigLoginServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/login/service/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberData.sql")
        .addScript("classpath:com/ef/login/service/member/insertMemberLoginControlData.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public LoginService loginService(
      @Autowired @Qualifier("loginMember") Query<MemberLoginBindingModel, Member> loginMember) {
    return new LoginService(loginMember, loginDataValidators());
  }

  private List<Validator<MemberLoginBindingModel, String>> loginDataValidators() {
    List<Validator<MemberLoginBindingModel, String>> validators = new ArrayList<Validator<MemberLoginBindingModel, String>>();

    validators.add(new EmailNotNullOrEmptyValidator());
    validators.add(new PasswordNotNullOrEmptyValidator());
    return validators;
  }

}