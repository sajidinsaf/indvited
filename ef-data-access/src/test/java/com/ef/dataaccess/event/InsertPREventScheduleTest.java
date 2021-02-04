package com.ef.dataaccess.event;

import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ef.dataaccess.config.DbTestUtils;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREventScheduleBindingModel;
import com.ef.model.event.PREventTimeSlotBindingModel;

public class InsertPREventScheduleTest {

  private InsertPREventSchedule insertPREventSchedule;
  private JdbcTemplate jdbcTemplate;

  @Mock
  private PREventScheduleBindingModel scheduleData;

  private int prEventId = 12345;
  private String startDate = "01.02.2021";
  private String endDate = "08.02.2021";
  private boolean monday = true;
  private boolean tuesday = true;
  private boolean wedenesday = false;
  private boolean thursday = true;
  private boolean friday = false;
  private boolean saturday = true;
  private boolean sunday = false;
  private boolean innerCircle = true;
  private boolean myBloggers = false;
  private boolean allEligible = false;

  @SuppressWarnings({ "resource" })
  @Before
  public void setUp() {
    openMocks(this);
    AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
        HsqlDbConfigInsertPREventScheduleTest.class);
    insertPREventSchedule = appContext.getBean(InsertPREventSchedule.class);
    jdbcTemplate = appContext.getBean(JdbcTemplate.class);
  }

  @After
  public void tearDown() {
    jdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE");
  }

  @Test
  public void shouldInsertScheduleSuccessfully() {

    scheduleData = new PREventScheduleBindingModel(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday,
        friday, saturday, sunday, innerCircle, myBloggers, allEligible);

    PREventTimeSlotBindingModel[] timeSlots = new PREventTimeSlotBindingModel[2];

    timeSlots[0] = new PREventTimeSlotBindingModel("1500", "1700");
    timeSlots[1] = new PREventTimeSlotBindingModel("1700", "2100");

    scheduleData.setTimeSlots(timeSlots);

    EventScheduleResult result = insertPREventSchedule.data(scheduleData);

    assertThat(result.getScheduleId(), is(0L));
    assertThat(result.getTimeSlotIds().length, is(2));
    assertThat(result.getTimeSlotIds()[0], is(0L));
    assertThat(result.getTimeSlotIds()[1], is(1L));
  }

}

@Configuration
@ComponentScan(basePackages = { "com.ef.dataaccess.event", "com.ef.dataaccess.member" })
class HsqlDbConfigInsertPREventScheduleTest {

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() {

    return new JdbcTemplate(dataSource());
  }

  private DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL);
    return new DbTestUtils().addCreateScripts(embeddedDatabaseBuilder).build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}