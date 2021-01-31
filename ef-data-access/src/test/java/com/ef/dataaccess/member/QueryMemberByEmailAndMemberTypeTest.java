package com.ef.dataaccess.member;

//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Random;

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
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberType;

public class QueryMemberByEmailAndMemberTypeTest {

  private Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryMemberByEmailAndMemberTypeTest.class);
    queryMemberByEmailAndMemberType = appContext.getBean("queryMemberByEmailAndMemberType", Query.class);

    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
//    jdbcTemplate.execute("drop table member_type");
//    jdbcTemplate.execute("drop table member");
  }

  @Test
  public void shouldFindMemberId() {
    String doesntMatterPassword = "sgsdf9rskf" + new Random().nextInt(999999);
    MemberLoginBindingModel loginModel = new MemberLoginBindingModel("dummy@123.com", doesntMatterPassword);
    MemberType memberType = MemberType.PR;
    loginModel.setMemberType(memberType);
    Member member = queryMemberByEmailAndMemberType.data(loginModel);
    assertThat(member.getId(), Matchers.is(0));

  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void shouldThrowEmptyResultsetExceptionWhenMemberTypeDoesntMatch() {
    String doesntMatterPassword = "slkgytrskf" + new Random().nextInt(999999);
    MemberLoginBindingModel loginModel = new MemberLoginBindingModel("dummy@123.com", doesntMatterPassword);
    MemberType memberType = MemberType.BLOGGER;
    loginModel.setMemberType(memberType);
    Member member = queryMemberByEmailAndMemberType.data(loginModel);
    assertThat(member.getId(), Matchers.is(0));

  }

}

@Configuration
@ComponentScan("com.ef.dataaccess.config,com.ef.dataaccess.member")
class HsqlDbConfigQueryMemberByEmailAndMemberTypeTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberData.sql").build();

  }

}