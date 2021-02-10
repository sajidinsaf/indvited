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
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberCriteriaDataBindingModel;

public class InsertMemberCriteriaDataTest {

  private InsertMemberCriteriaData insertMemberCriteriaData;
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberCriteriaDataBindingModel memberCriteriaDataBindingModel;

  private int memberCriteriaValue = new Random().nextInt(10000);
  private int memberId = new Random().nextInt(10000000);
  private String criteriaMetadataName = "Minimum Zomato reviews";

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMemberCriteriaDataTest.class);
    insertMemberCriteriaData = appContext.getBean(InsertMemberCriteriaData.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void test() {
    when(memberCriteriaDataBindingModel.getCriteriaMetadataName()).thenReturn(criteriaMetadataName);
    when(memberCriteriaDataBindingModel.getMemberCriteriaValue()).thenReturn(memberCriteriaValue);
    when(memberCriteriaDataBindingModel.getMemberId()).thenReturn(memberId);

    MemberCriteriaData memberCriteriaData = insertMemberCriteriaData.data(memberCriteriaDataBindingModel);

    String expectedId = memberId + "_" + 0;
    assertThat(memberCriteriaData.getId(), Matchers.is(expectedId));
    assertThat(memberCriteriaData.getCriteriaMetadata().getId(), Matchers.is(0));
    assertThat(memberCriteriaData.getMemberCriteriaValue(), Matchers.is(memberCriteriaValue));

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.config", "com.ef.dataaccess.member", "com.ef.dataaccess.event" })
class HsqlDbConfigInsertMemberCriteriaDataTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql").build();

  }

}