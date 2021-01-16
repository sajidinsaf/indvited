package com.ef.dataaccess.member;

//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;
//import org.junit.Test;
//import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.MemberType;

public class QueryMemberTypeByUsernameEmailPhoneTest {

  private Query<String, MemberType> queryMemberTypeByUsername;
  private Query<String, MemberType> queryMemberTypeByEmail;
  private Query<String, MemberType> queryMemberTypeByPhone;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryMemberTypeByUsernameEmailPhoneTest.class);
    queryMemberTypeByUsername = appContext.getBean("queryMemberTypeByUsername", Query.class);
    queryMemberTypeByEmail = appContext.getBean("queryMemberTypeByEmail", Query.class);
    queryMemberTypeByPhone = appContext.getBean("queryMemberTypeByPhone", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
//    jdbcTemplate.execute("drop table member_type");
//    jdbcTemplate.execute("drop table member");
  }

  @Test
  public void testForUsername() {

    MemberType memberType = queryMemberTypeByUsername.data("dummy");
    assertThat(memberType, Matchers.is(new MemberType(2, "pr")));

    memberType = queryMemberTypeByUsername.data("dummy2");
    assertThat(memberType, Matchers.is(new MemberType(3, "blogger")));

  }

  @Test
  public void testForEmail() {

    MemberType memberType = queryMemberTypeByEmail.data("dummy@123.com");
    assertThat(memberType, Matchers.is(new MemberType(2, "pr")));

    memberType = queryMemberTypeByEmail.data("dummy2@456.com");
    assertThat(memberType, Matchers.is(new MemberType(3, "blogger")));

  }

  @Test
  public void testForPhone() {

    MemberType memberType = queryMemberTypeByPhone.data("1234567890");
    assertThat(memberType, Matchers.is(new MemberType(2, "pr")));

    memberType = queryMemberTypeByPhone.data("3456789012");
    assertThat(memberType, Matchers.is(new MemberType(3, "blogger")));

  }

  public void testDataDoesntExist() {
    assertThat(isUnique(queryMemberTypeByUsername, "invalidValue"), Matchers.is(true));
    assertThat(isUnique(queryMemberTypeByEmail, "invalidValue"), Matchers.is(true));
    assertThat(isUnique(queryMemberTypeByPhone, "9034834843"), Matchers.is(true));
  }

  private boolean isUnique(Query<String, MemberType> query, String value) {
    try {
      query.data(value);
      return false;
    } catch (EmptyResultDataAccessException e) {
      return true;
    }
  }
}

@Configuration
@ComponentScan("com.ef.dataaccess.member")
class HsqlDbConfigQueryMemberTypeByUsernameEmailPhoneTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberData.sql").build();

  }

}