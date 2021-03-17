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
import com.ef.model.member.group.MyGroupMember;
import com.ef.model.member.group.MyGroupMemberBindingModel;

public class InsertMyGroupMemberTest {

  private Insert<MyGroupMemberBindingModel, MyGroupMember> insertMyGroupMember;
  private MyGroupTypeCache myGroupTypeCache;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {

    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMyGroupMemberTest.class);
    insertMyGroupMember = appContext.getBean("insertMyGroupMember", Insert.class);
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
    int myGroupId = r.nextInt(100000);
    int memberId = r.nextInt(100000000);

    setUpTables(myGroupId, memberId);

    MyGroupMemberBindingModel input = new MyGroupMemberBindingModel(myGroupId, memberId);
    MyGroupMember myGroupMember = insertMyGroupMember.data(input);

    assertThat(myGroupMember.getMember().getId(), is(memberId));
    assertThat(myGroupMember.getMyGroup().getId(), is(myGroupId));

    assertThat(myGroupMember.getAddedTimestamp(), notNullValue());
  }

  private void setUpTables(int groupId, int memberId) {
    jdbcTemplate.update("insert into member(id, member_type_id) values (?,?)", new Object[] { memberId, 3 });
    jdbcTemplate.update("insert into my_group(id, my_group_type_id) values (?, ?)", new Object[] { groupId, 1 });

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.config", "com.ef.dataaccess.member", "com.ef.dataaccess.event" })
class HsqlDbConfigInsertMyGroupMemberTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/group/insertMyGroupTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql").build();

  }

}