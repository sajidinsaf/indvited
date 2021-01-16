package com.ef.dataaccess.member;

//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

import org.aspectj.lang.annotation.AfterThrowing;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.dataaccess.config.LoginCredentials;

public class MemberLoginServiceTest {

  private Query<LoginCredentials, Boolean> memberLoginService;
  private JdbcTemplate jdbcTemplate;
  private PasswordEncoder encoder;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigMemberLoginServiceTest.class);
    memberLoginService = appContext.getBean("memberLoginService", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    encoder = appContext.getBean(PasswordEncoder.class);
  }

  @After
  @AfterThrowing
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
//    jdbcTemplate.execute("drop table member_type");
//    jdbcTemplate.execute("drop table member");
  }

  // @Test
  public void testPasswordMatches() {

    System.out.println(encoder.encode("dummy"));
    LoginCredentials loginCredentials = new LoginCredentials("dummy", "dummy");
    boolean result = memberLoginService.data(loginCredentials);

    assertThat(result, Matchers.is(true));
  }

  // @Test
  public void testPasswordDoesntMatch() {

    LoginCredentials loginCredentials = new LoginCredentials("dummy", "incorrectPassword");
    boolean result = memberLoginService.data(loginCredentials);

    assertThat(result, Matchers.is(false));
  }

  // @Test
  public void testUsernameNotFound() {

    LoginCredentials loginCredentials = new LoginCredentials("usernameDoesntExist", "dummy");
    boolean result = memberLoginService.data(loginCredentials);

    assertThat(result, Matchers.is(false));
  }
}

@Configuration
@ComponentScan("com.ef.dataaccess.member")
class HsqlDbConfigMemberLoginServiceTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberDataWithEncryptedPassword.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}