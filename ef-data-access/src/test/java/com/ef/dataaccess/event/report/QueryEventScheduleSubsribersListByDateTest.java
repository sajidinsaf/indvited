package com.ef.dataaccess.event.report;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.text.ParseException;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.report.EventScheduleSubscribers;
import com.ef.model.event.report.EventScheduleSubscribersQueryParameters;
import com.ef.model.event.report.MemberSubscription;

public class QueryEventScheduleSubsribersListByDateTest {

  private Query<EventScheduleSubscribersQueryParameters, List<EventScheduleSubscribers>> queryEventScheduleSubsribersListByDate;
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings({ "resource", "unchecked" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigQueryEventScheduleSubsribersListByDateTest.class);
    queryEventScheduleSubsribersListByDate = appContext.getBean("queryEventScheduleSubsribersListByDate", Query.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);

  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldRetrieveTwoScheduleSuccessfully() throws ParseException {

    int prId = new Random().nextInt(99999999);
    insertEventScheduleData(prId);

    EventScheduleSubscribersQueryParameters params = new EventScheduleSubscribersQueryParameters(prId, "2021-04-03",
        "2021-04-06");

    List<EventScheduleSubscribers> result = queryEventScheduleSubsribersListByDate.data(params);
    assertThat("", notNullValue());
    assertThat(result.size(), is(1));

    EventScheduleSubscribers ess = result.get(0);
    assertThat(ess.getMemberSubscriptionList().size(), is(1));

    MemberSubscription ms = ess.getMemberSubscriptionList().get(0);

    assertThat(ms.getFirstName().startsWith("first"), is(true));
    assertThat(ms.getLastName().startsWith("last"), is(true));
    assertThat(ms.getScheduleDate(), is("Sun 4 Apr 2021"));

    // System.out.println(ess.getReportForVenue());
  }

  private void insertEventScheduleData(int prId) {

    String scheduleDate = "2021-04-04";
    String preferredTime = "1400";

    int status = 4;
    long scheduleId = new Random().nextInt(1000000);
    int eventId = new Random().nextInt(100000);

    int subscriberId = new Random().nextInt(100000);

    int venueId = new Random().nextInt(100000);

    int phone = new Random().nextInt(100000);

    String venueName = "venue" + new Random().nextInt(9999);

    String venueAddress = "address" + new Random().nextInt(9999999);

    String firstName = "first" + new Random().nextInt(9999999);

    String lastName = "last" + new Random().nextInt(9999999);

    // private final String SELECT = "SELECT e.id as event_id, v.name as venue_name,
    // v.address as venue_address, m.firstname as first_name, m.lastname last_name,
    // m.phone as phone, ess.schedule_date as date, ess.preferred_time as time FROM
    // event e, event_schedule es, event_schedule_subscription ess, venue v, member
    // m WHERE ess.status_id=4 and ess.event_schedule_id = es.id and es.event_id =
    // e.id and e.event_venue_id = v.id and m.id = ess.subscriber_id and
    // ess.schedule_date BETWEEN '2021-04-10' AND '2021-04-17'";

    String sql = "INSERT INTO event " + "(id, event_venue_id, member_id) " + "VALUES " + "(" + eventId + "," + venueId
        + "," + prId + ");";

    jdbcTemplate.update(sql);

    sql = "INSERT INTO event_schedule " + "(id, event_id) " + "VALUES " + "(" + scheduleId + "," + eventId + ");";

    jdbcTemplate.update(sql);

    sql = "INSERT INTO venue " + "(id, name, address) " + "VALUES " + "(" + venueId + ",'" + venueName + "','"
        + venueAddress + "');";

    jdbcTemplate.update(sql);

    sql = "INSERT INTO event_schedule_subscription "
        + "(event_schedule_id, subscriber_id, schedule_date, preferred_time, status_id) " + "VALUES " + "(" + scheduleId
        + "," + subscriberId + ",'" + scheduleDate + "','" + preferredTime + "'," + status + ");";
    jdbcTemplate.update(sql);

    sql = "INSERT INTO member " + "(id, firstname, lastname, phone) " + "VALUES " + "(" + subscriberId + ",'"
        + firstName + "','" + lastName + "','" + phone + "');";
    jdbcTemplate.update(sql);

  }
}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigQueryEventScheduleSubsribersListByDateTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder)

        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaMeta.sql")
        .addScript("classpath:com/ef/dataaccess/member/insertMemberTypeData.sql")
        .addScript("classpath:com/ef/dataaccess/event/insertEventCriteriaData.sql")
        .addScript("classpath:com/ef/dataaccess/core/insertForums.sql").build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}