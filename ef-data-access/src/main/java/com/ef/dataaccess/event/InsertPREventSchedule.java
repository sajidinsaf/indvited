package com.ef.dataaccess.event;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.util.DateUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.EventTimeslot;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.PREventScheduleBindingModel;
import com.ef.model.event.PREventTimeSlotBindingModel;

@Component("insertPrEventSchedule")
public class InsertPREventSchedule implements Insert<PREventScheduleBindingModel, EventScheduleResult> {

  public static final String DATE_FORMAT = System.getProperty("event.schedule.date.format") != null
      ? System.getProperty("event.schedule.date.format")
      : "dd/MM/yyyy";

  public static final String TIME_FORMAT = System.getProperty("event.schedule.time.format") != null
      ? System.getProperty("event.schedule.time.format")
      : "HH.mm";

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventSchedule.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_SCHEDULE_STATEMENT = "INSERT INTO event_schedule(event_id, start_date, end_date, schedule_time, bloggers_per_day, days_of_the_week, publish_to_inner_circle, publish_to_my_bloggers, publish_to_all_eligible, scheduled_for_timestamp) VALUES (?,?,?,?,?,?,?,?,?,?)";

  private final String INSERT_TIMESLOT_STATEMENT = "INSERT INTO event_schedule_timeslot(event_schedule_id, start_time, end_time) VALUES (?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  private final Query<Long, PREventSchedule> queryEventScheduleById;

  @Autowired
  public InsertPREventSchedule(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventScheduleById") Query<Long, PREventSchedule> queryEventScheduleById) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventScheduleById = queryEventScheduleById;

  }

//  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;

//  private PREventCriteriaBindingModel[] eventCriteria;

//  private String notes;  
//  
  @Override
  public EventScheduleResult data(final PREventScheduleBindingModel input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert the PR Event schedule and get the schedule id.
    jdbcTemplate.update(connection -> {
      return getInsertSchedulePreparedStatement(input, connection);
    }, keyHolder);

    long scheduleId = keyHolder.getKey().longValue();

    logUtil.debug(logger, "created event schedule with id", scheduleId);

    // long[] eventTimeSlotIds = insertEventTimeSlots(input, scheduleId);

    PREventSchedule schedule = queryEventScheduleById.data(scheduleId);

    EventScheduleResult scheduleResult = new EventScheduleResult(schedule, new long[] {});

    return scheduleResult;
  }

  private long[] insertEventTimeSlots(PREventScheduleBindingModel input, long scheduleId) {

    PREventTimeSlotBindingModel[] timeSlots = input.getTimeSlots();
    EventTimeslot[] eventTimeSlots = new EventTimeslot[timeSlots.length];
    long[] eventTimeSlotIds = new long[timeSlots.length];

    for (int i = 0; i < timeSlots.length; i++) {
      final PREventTimeSlotBindingModel timeSlotModel = timeSlots[i];
      // This null check has been introduced due to changes on front end form
      // whereby we may get empty cells in the array
      if (timeSlotModel == null) {
        continue;
      }
      KeyHolder keyHolder = new GeneratedKeyHolder();

      // Insert the PR Event schedule and get the schedule id.
      jdbcTemplate.update(connection -> {
        return getInsertTimeslotPreparedStatement(timeSlotModel, scheduleId, connection);
      }, keyHolder);

      long timeSlotId = keyHolder.getKey().longValue();

      EventTimeslot eventTimeSlot = new EventTimeslot(timeSlotId, scheduleId, timeSlotModel.getTimeFrom(),
          timeSlotModel.getTimeTo());
      eventTimeSlots[i] = eventTimeSlot;
      eventTimeSlotIds[i] = timeSlotId;
    }

    return eventTimeSlotIds;

  }

  private final PreparedStatement getInsertSchedulePreparedStatement(PREventScheduleBindingModel input,
      Connection connection) throws SQLException {

    int eventId = input.getEventId();
    Date startDate = getDate(input.getStartDate(), DATE_FORMAT);
    Date endDate = getDate(input.getEndDate(), DATE_FORMAT);
    String daysOfTheWeek = input.getScheduledDaysOfTheWeekString();

    PreparedStatement ps = connection.prepareStatement(INSERT_SCHEDULE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, eventId);
    ps.setDate(2, startDate);
    ps.setDate(3, endDate);
    ps.setString(4, input.getScheduleTime());
    ps.setInt(5, input.getBloggersPerDay());
    ps.setString(6, daysOfTheWeek);
    ps.setBoolean(7, input.isInnerCircle());
    ps.setBoolean(8, input.isMyBloggers());
    ps.setBoolean(9, input.isAllEligible());
    Timestamp scheduledForTimeStamp = getScheduledForTimestamp(input);

    ps.setTimestamp(10, scheduledForTimeStamp);

    return ps;
  }

  private final PreparedStatement getInsertTimeslotPreparedStatement(PREventTimeSlotBindingModel timeSlot,
      long scheduleId, Connection connection) throws SQLException {

    String timeFrom = timeSlot.getTimeFrom();
    String timeTo = timeSlot.getTimeTo();

    PreparedStatement ps = connection.prepareStatement(INSERT_TIMESLOT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setLong(1, scheduleId);
    ps.setString(2, timeFrom);
    ps.setString(3, timeTo);

    return ps;
  }

  private Timestamp getScheduledForTimestamp(PREventScheduleBindingModel input) {
    if (input.getScheduleOnDate() == null) {
      return new Timestamp(System.currentTimeMillis());
    }

    String dateTimeString = input.getScheduleOnDate() + " " + input.getScheduleOnTime();
    Date date = getDate(dateTimeString, DATE_FORMAT + " " + TIME_FORMAT);

    return new Timestamp(date.getTime());
  }

  public Date getDate(String date, String dateFormat) {
    return new DateUtil().parseSqlDateFromEventDisplayString(date, dateFormat);
  }
}
