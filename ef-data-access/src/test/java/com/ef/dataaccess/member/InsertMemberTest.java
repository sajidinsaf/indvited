package com.ef.dataaccess.member;

//import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

public class InsertMemberTest {

  private InsertMember insertMember;
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberRegistrationBindingModel memberData;

  private String firstName = "fname" + new Random().nextInt(1000);
  private String lastName = "lname" + new Random().nextInt(1000);
  private String email = "em" + new Random().nextInt(1000) + "@abcd.com";
  private String username = "uname" + new Random().nextInt(1000) + "@absdcd.com";
  private String password = "passwd" + new Random().nextInt(1000) + "@asds.com";
  private String phone = new Random().nextInt(10) + "";
  private String memberTypeName = new String[] { "admin", "pr", "blogger" }[new Random().nextInt(2)];

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    initMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMemberTest.class);
    insertMember = appContext.getBean(InsertMember.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("drop table member_type");
    jdbcTemplate.execute("drop table member");
  }

  @Test
  public void test() {
    when(memberData.getFirstName()).thenReturn(firstName);
    when(memberData.getLastName()).thenReturn(lastName);
    when(memberData.getUsername()).thenReturn(username);
    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email);
    when(memberData.getPhone()).thenReturn(phone);
    when(memberData.getMemberType()).thenReturn(memberTypeName);

    Member member = insertMember.data(memberData);

    assertThat(member.getFirstName(), Matchers.is(firstName));
    assertThat(member.getLastName(), Matchers.is(lastName));
    assertThat(member.getUsername(), Matchers.is(username));
    assertThat(member.getPhone(), Matchers.is(phone));
    assertThat(member.getMemberType().getName(), Matchers.is(memberTypeName));

  }

}

@Configuration
@ComponentScan("com.ef.dataaccess.member")
class HsqlDbConfigInsertMemberTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:com/ef/dataaccess/registration/createMemberTypeTable.sql")
        .addScript("classpath:com/ef/dataaccess/registration/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/registration/createMemberTable.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}