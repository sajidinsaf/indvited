package com.ef.dataaccess.member;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;
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

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.member.Member;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberDomain;
import com.ef.model.member.MemberForumCriterionBindingModel;

public class InsertBloggerProfileDataTest {

  private InsertBloggerProfileData insertBloggerProfileData;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertBloggerProfileDataTest.class);
    insertBloggerProfileData = appContext.getBean(InsertBloggerProfileData.class, "insertBloggerProfileData");
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void test() {

    int memberId = 0;
    String instagramUrl = "http://insta.com/mypage";
    String zomatoUrl = "http://zomato.com/mypage";
    String youtubeUrl = "http://youtube.com/mypage";

    int instagramFollowerCount = new Random().nextInt(99000999);
    int youtubeFollowerCount = new Random().nextInt(3553523);
    int zomatoLevel = new Random().nextInt(15);
    int zomatoFollowerCount = new Random().nextInt(99000999);
    int zomatoReviewCount = new Random().nextInt(224242);

    boolean catDiet = false;
    boolean catFashion = true;
    boolean catHealth = false;
    boolean catHotel = true;
    boolean catLifestyle = false;
    boolean catRestaurant = true;
    boolean catBabyProducts = false;
    boolean catTravel = true;
    boolean catTechnology = false;

    MemberForumCriterionBindingModel memberForumCriterionBindingModel = new MemberForumCriterionBindingModel(memberId,
        instagramUrl, zomatoUrl, youtubeUrl, instagramFollowerCount, youtubeFollowerCount, zomatoLevel,
        zomatoFollowerCount, zomatoReviewCount, catDiet, catFashion, catHealth, catHotel, catLifestyle, catRestaurant,
        catBabyProducts, catTravel, catTechnology);

    Member member = insertBloggerProfileData.data(memberForumCriterionBindingModel);

    List<MemberCriteriaData> memberCriteriaData = member.getMemberCriteriaDataList();
    assertThat(memberCriteriaData.size(), is(5));

    List<MemberDomain> memberDomainList = member.getMemberDomainMappings();
    assertThat(memberDomainList.size(), is(9));
    System.out.println(memberDomainList);
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.config", "com.ef.dataaccess.member", "com.ef.dataaccess.event" })
class HsqlDbConfigInsertBloggerProfileDataTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/core/insertDomains.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertForums.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertDomainForum.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql").build();

  }

}