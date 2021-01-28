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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.MemberType;

public class MemberTypeCacheTest {

  private MemberTypeCache queryMemberType;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryMemberTest.class);
    queryMemberType = appContext.getBean(MemberTypeCache.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  @AfterThrowing
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
//    jdbcTemplate.execute("drop table member_type");
  }

  @Test
  public void test() {

    assertThat(queryMemberType.getMemberType("admin"), Matchers.is(new MemberType(1, "admin")));
    assertThat(queryMemberType.getMemberType("pr"), Matchers.is(new MemberType(2, "pr")));
    assertThat(queryMemberType.getMemberType("blogger"), Matchers.is(new MemberType(3, "blogger")));

    assertThat(queryMemberType.getMemberType(1), Matchers.is(new MemberType(1, "admin")));
    assertThat(queryMemberType.getMemberType(2), Matchers.is(new MemberType(2, "pr")));
    assertThat(queryMemberType.getMemberType(3), Matchers.is(new MemberType(3, "blogger")));

  }

}

@Configuration
@ComponentScan("com.ef.dataaccess.config,com.ef.dataaccess.member")
class HsqlDbConfigQueryMemberTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql").build();

  }

}