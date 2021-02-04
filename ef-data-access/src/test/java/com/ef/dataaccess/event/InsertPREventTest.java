package com.ef.dataaccess.event;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

//import java.util.Arrays;
//import java.util.Random;
//import java.util.regex.Pattern;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
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
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventCriteriaBindingModel;
import com.ef.model.event.PREventDeliverableBindingModel;
import com.ef.model.event.PREventLocationBindingModel;
import com.ef.model.event.PREventTimeSlotBindingModel;
import com.ef.model.member.MemberType;

public class InsertPREventTest {

  private InsertPREvent insertPREvent;
  private JdbcTemplate jdbcTemplate;
  private MemberTypeCache memberTypeCache;

  @Mock
  private PREventBindingModel eventData;

  private int memberId = 0;
  private int eventType = 0;
  private String domainName = "Restaurant";
  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel = new PREventTimeSlotBindingModel[] {
      new PREventTimeSlotBindingModel("1400", "1800"), new PREventTimeSlotBindingModel("1200", "1700") };
  private String cap = "1 cocktail each / 2 starters / 2 mains";
  private String exclusions = "*no red meat , no fish* IN case u want to order a *dessert or any dish apart of the above cap it would be *PAYABLE*";
  private List<PREventCriteriaBindingModel> eventCriteria = Arrays.asList(
      new PREventCriteriaBindingModel("Mininum Zomato reviews", 175),
      new PREventCriteriaBindingModel("Minimum Instagram followers", 9000));
  private List<PREventDeliverableBindingModel> eventDeliverables = Arrays.asList(
      new PREventDeliverableBindingModel("Zomato Review"), new PREventDeliverableBindingModel("Instagram Post"));
  private PREventLocationBindingModel eventLocation = new PREventLocationBindingModel("Esora",
      "1st Floor, Commerz 2, International Business Park, Oberoi Garden City, Near Oberoi Mall, Goregaon East, Mumbai",
      "http://zoma.to/r/18789802", null);
  private String notes = "Find this restaurant on Zomato | Bhukkad, Shop 13, Mohid Heights, R. T. O. Road, Andheri Lokhandwala, Andheri West, Mumbai  http://zoma.to/r/19213346 (You have to place an order for 1 dish from menu lowest item need to be ordered  amount for which will not be refunded). *Timings 12pm till 3pm \u0026 7pm till 9pm Available From Monday Till Sunday Only on Friday \u0026 Saturday  Available From 12pm till 3pm*";

  @SuppressWarnings("resource")
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertPREventTest.class);
    insertPREvent = appContext.getBean(InsertPREvent.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
    memberTypeCache = appContext.getBean(MemberTypeCache.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertEventSuccessfully() {
    MemberType memberType = memberTypeCache.getMemberType(MemberType.KNOWN_MEMBER_TYPE_PR);

    eventData = new PREventBindingModel(memberId, memberType, eventType, domainName, cap, exclusions, eventCriteria,
        eventDeliverables, eventLocation, notes);

    PREvent event = insertPREvent.data(eventData);

    assertThat(event.getCap(), is(cap));

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    assertThat(dateFormat.format(event.getCreatedDate()), is(dateFormat.format(new java.util.Date())));

    assertThat(event.getMember().getId(), is(0));
    assertThat(event.getMember().getFirstName(), is("dummy"));
    assertThat(event.getMember().getEmail(), is("dummy@123.com"));

//    assertThat(event.getEventTimeSlots().length, is(2));
//
//    String eventDate0 = dateFormat.format(event.getEventTimeSlots()[0].getEventDate());
//    assertThat(eventDate0, is("15/01/2021"));
//    assertThat(event.getEventTimeSlots()[0].getTimeFrom(), is("1400"));
//
//    String eventDate1 = dateFormat.format(event.getEventTimeSlots()[1].getEventDate());
//    assertThat(eventDate1, is("16/01/2021"));
//    assertThat(event.getEventTimeSlots()[1].getTimeTo(), is("1700"));

    assertThat(event.getEventVenue().getName(), is("Esora"));
    assertThat(event.getEventVenue().getId(), is(0));

    assertThat(event.getEventCriteria()[0].getCriteriValue(), is(175));
    assertThat(event.getEventCriteria()[1].getName(), is("Minimum Instagram followers"));

    assertThat(event.getEventDeliverables()[0].getEventId(), is(event.getId()));
    assertThat(event.getEventDeliverables()[0].getDeliverableId(), is(0));
    assertThat(event.getEventDeliverables()[0].getDeliverableName(), is("Zomato Review"));

    assertThat(event.getEventDeliverables()[1].getEventId(), is(event.getId()));
    assertThat(event.getEventDeliverables()[1].getDeliverableId(), is(1));
    assertThat(event.getEventDeliverables()[1].getDeliverableName(), is("Instagram Post"));

  }

  @Test(expected = RuntimeException.class)
  public void shouldNotCreateEventWhenMemberTypeInDbIsNotPR() {
    MemberType memberType = memberTypeCache.getMemberType(MemberType.KNOWN_MEMBER_TYPE_PR);
    memberId = 1;
    eventData = new PREventBindingModel(memberId, memberType, eventType, domainName, cap, exclusions, eventCriteria,
        eventDeliverables, eventLocation, notes);

    insertPREvent.data(eventData);

  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigInsertPREventTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberData.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberLoginControlData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventDeliverableMeta.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertDomains.sql").build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}