package com.ef.dataaccess.member.group;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Random;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.group.MyGroup;
import com.ef.model.member.group.MyGroupBindingModel;

public class InsertMyGroup_And_QueryMyGroupsByCreatorId_Test {

  private Insert<MyGroupBindingModel, MyGroup> insertMyGroup;
  private MyGroupTypeCache myGroupTypeCache;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {

    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMyGroupTest.class);
    insertMyGroup = appContext.getBean("insertMyGroup", Insert.class);
    myGroupTypeCache = appContext.getBean(MyGroupTypeCache.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertMyGroup() {
    Random r = new Random();
    int creatorId = r.nextInt(100000);
    String name = "someRandomName" + r.nextInt(1000);
    int myGroupTypeId = myGroupTypeCache.getMemberType(1 + r.nextInt(2)).getId(); // get an id between 1 and 3
    String description = "someRandomDescription" + r.nextInt(100000);
    MyGroupBindingModel input = new MyGroupBindingModel(creatorId, myGroupTypeId, name, description);
    MyGroup myGroup = insertMyGroup.data(input);

    int expectedId = 0;
    assertThat(myGroup.getId(), is(expectedId));
    assertThat(myGroup.getCreatorId(), is(creatorId));
    assertThat(myGroup.getMyGroupType(), is(myGroupTypeCache.getMemberType(myGroupTypeId)));
    assertThat(myGroup.getName(), is(name.toUpperCase()));
    assertThat(myGroup.getDescription(), is(description));
    assertThat(myGroup.getCreationTimestamp(), notNullValue());
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.config", "com.ef.dataaccess.member", "com.ef.dataaccess.event" })
class HsqlDbConfigInsertMyGroupTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/group/insertMyGroupTypeData.sql").build();

  }

}