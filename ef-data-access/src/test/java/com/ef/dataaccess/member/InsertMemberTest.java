package com.ef.dataaccess.member;

import static org.hamcrest.Matchers.is;
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

import org.aspectj.lang.annotation.AfterThrowing;
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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.PreconfirmationMemberRegistrationModel;

public class InsertMemberTest {

  private InsertMember insertMember;
  private JdbcTemplate jdbcTemplate;
  @Mock
  private MemberRegistrationBindingModel memberData;

  private String firstName = "fname" + new Random().nextInt(1000);
  private String lastName = "lname" + new Random().nextInt(1000);
  private String email = "em" + new Random().nextInt(1000) + "@abcd.com";
  private String password = "passwd" + new Random().nextInt(1000) + "@asds.com";
  private String phone = new Random().nextInt(10) + "";
  private String memberTypeName = new String[] { "admin", "pr", "blogger" }[new Random().nextInt(2)];
  private String gender = "F";
  private String addressLine1 = "8493 Golf Drive";
  private String addressLine2 = "Marbella St 9";
  private String city = "Springfield";
  private String country = "United States of America";
  private String pincode = "876543";

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertMemberTest.class);
    insertMember = appContext.getBean(InsertMember.class);
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
    when(memberData.getFirstName()).thenReturn(firstName);
    when(memberData.getLastName()).thenReturn(lastName);
    when(memberData.getPassword()).thenReturn(password);
    when(memberData.getEmail()).thenReturn(email);
    when(memberData.getGender()).thenReturn(gender);
    when(memberData.getPhone()).thenReturn(phone);
    when(memberData.getMemberType()).thenReturn(memberTypeName);
    when(memberData.getAddressLine1()).thenReturn(addressLine1);
    when(memberData.getAddressLine2()).thenReturn(addressLine2);
    when(memberData.getCity()).thenReturn(country);
    when(memberData.getCountry()).thenReturn(country);
    when(memberData.getPincode()).thenReturn(pincode);

    PreconfirmationMemberRegistrationModel pmrMember = insertMember.data(memberData);

    Member member = pmrMember.getMember();

    assertThat(member.getFirstName(), is(firstName));
    assertThat(member.getLastName(), is(lastName));
    assertThat(member.getPhone(), is(phone));
    assertThat(member.getGender(), is(gender));
    assertThat(member.getMemberType().getName(), is(memberTypeName));
    assertThat(member.getMemberAddress().getMemberId(), is(member.getId()));
    assertThat(member.getMemberAddress().getAddressLine1(), is(addressLine1));
    assertThat(member.getMemberAddress().getAddressLine2(), is(addressLine2));
    assertThat(member.getMemberAddress().getCity(), is(country));
    assertThat(member.getMemberAddress().getCountry(), is(country));
    assertThat(member.getMemberAddress().getPincode(), is(pincode));
    assertThat(member.getMemberAddress().isCurrent(), is(true));
  }

}

@Configuration
@ComponentScan("com.ef.dataaccess.config, com.ef.dataaccess.member")
class HsqlDbConfigInsertMemberTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);

    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}