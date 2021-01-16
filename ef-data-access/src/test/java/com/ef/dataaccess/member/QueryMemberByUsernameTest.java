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

//import static org.junit.Assert.*;

import org.junit.Test;
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

import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

public class QueryMemberByUsernameTest {

  private Query<String, Member> queryMemberByUsername;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryMemberByUsernameTest.class);
    queryMemberByUsername = appContext.getBean("queryMemberByUsername", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  @AfterThrowing
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
//    jdbcTemplate.execute("drop table member_type");
//    jdbcTemplate.execute("drop table member");
  }

  @Test
  public void test() {

    Member member = queryMemberByUsername.data("dummy");
    assertThat(member.getMemberType(), Matchers.is(new MemberType(2, "pr")));
    assertThat(member.getEmail(), Matchers.is("dummy@123.com"));

    member = queryMemberByUsername.data("dummy2");
    assertThat(member.getMemberType(), Matchers.is(new MemberType(3, "blogger")));
    assertThat(member.getEmail(), Matchers.is("dummy2@456.com"));

  }

}

@Configuration
@ComponentScan("com.ef.dataaccess")
class HsqlDbConfigQueryMemberByUsernameTest {

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