package com.ef.dataaccess.core;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

import org.aspectj.lang.annotation.AfterThrowing;
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

public class DomainForumCacheTest {

  private DomainForumCache domainForumCache;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigDomainForumCacheTest.class);
    domainForumCache = appContext.getBean(DomainForumCache.class);
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
    assertThat(domainForumCache.getDomainsByForumId(2).size(), is(2));
    assertThat(domainForumCache.getDomainsByForumId(1).size(), is(9));
    assertThat(domainForumCache.getForumsByDomainId(3).size(), is(2));
    assertThat(domainForumCache.getForumsByDomainId(6).size(), is(2));
  }

}

@Configuration
@ComponentScan("com.ef.dataaccess.config,com.ef.dataaccess.member")
class HsqlDbConfigDomainForumCacheTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/core/insertDomains.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertForums.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertDomainForum.sql").build();

  }

}