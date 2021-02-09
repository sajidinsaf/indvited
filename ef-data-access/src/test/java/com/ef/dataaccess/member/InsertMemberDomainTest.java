package com.ef.dataaccess.member;

//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Random;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;
//import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.MemberDomain;
import com.ef.model.member.MemberDomainBindingModel;

public class InsertMemberDomainTest {

  private InsertMemberDomain insertMemberDomain;
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberDomainBindingModel memberDomainBindingModel;

  private int domainId = new Random().nextInt(10000);
  private int memberId = new Random().nextInt(10000000);

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMemberDomainTest.class);
    insertMemberDomain = appContext.getBean(InsertMemberDomain.class, "insertMemberDomain");
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void test() {
    when(memberDomainBindingModel.getDomainId()).thenReturn(domainId);
    when(memberDomainBindingModel.getMemberId()).thenReturn(memberId);

    MemberDomain memberDomain = insertMemberDomain.data(memberDomainBindingModel);

    assertThat(memberDomain.getMemberId(), Matchers.is(memberId));
    assertThat(memberDomain.getDomainId(), Matchers.is(domainId));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.config", "com.ef.dataaccess.member", "com.ef.dataaccess.event" })
class HsqlDbConfigInsertMemberDomainTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder).build();

  }

}