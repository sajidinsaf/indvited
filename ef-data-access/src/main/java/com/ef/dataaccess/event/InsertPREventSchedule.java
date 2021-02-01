package com.ef.dataaccess.event;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.model.event.AbstractPREventScheduleBindingModel;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.EventTimeSlot;
import com.ef.model.event.PREventTimeSlotBindingModel;

@Component("insertPREventSchedule")
public class InsertPREventSchedule<T extends AbstractPREventScheduleBindingModel>
    implements Insert<T, EventScheduleResult> {

  private static final String DATE_FORMAT = System.getProperty("event.schedule.date.format") != null
      ? System.getProperty("event.schedule.date.format")
      : "dd.MM.yyyy";

  private static final String TIME_FORMAT = System.getProperty("event.schedule.time.format") != null
      ? System.getProperty("event.schedule.time.format")
      : "HH.mm";

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventSchedule.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_SCHEDULE_STATEMENT = "INSERT INTO event_schedule(event_id, start_date, end_date, days_of_the_week, scheduled_for_timestamp) VALUES (?,?,?,?,?)";

  private final String INSERT_TIMESLOT_STATEMENT = "INSERT INTO event_schedule_timeslot(event_schedule_id, start_time, end_time) VALUES (?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertPREventSchedule(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

//  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;

//  private PREventCriteriaBindingModel[] eventCriteria;

//  private String notes;  
//  
  @Override
  public EventScheduleResult data(final T input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert the PR Event schedule and get the schedule id.
    jdbcTemplate.update(connection -> {
      return getInsertSchedulePreparedStatement(input, connection);
    }, keyHolder);

    long scheduleId = (long) keyHolder.getKey();

    insertEventTimeSlots(input, scheduleId);

    EventScheduleResult scheduleResult = new EventScheduleResult(scheduleId);

    return scheduleResult;
  }

  private EventTimeSlot[] insertEventTimeSlots(T input, long scheduleId) {

    PREventTimeSlotBindingModel[] timeSlots = input.getTimeSlots();
    EventTimeSlot[] eventTimeSlots = new EventTimeSlot[timeSlots.length];

    for (int i = 0; i < timeSlots.length; i++) {
      final PREventTimeSlotBindingModel timeSlotModel = timeSlots[i];
      KeyHolder keyHolder = new GeneratedKeyHolder();

      // Insert the PR Event schedule and get the schedule id.
      jdbcTemplate.update(connection -> {
        return getInsertTimeslotPreparedStatement(timeSlotModel, scheduleId, connection);
      }, keyHolder);

      long timeSlotId = (long) keyHolder.getKey();

      EventTimeSlot eventTimeSlot = new EventTimeSlot(timeSlotId, timeSlotModel.getTimeFrom(),
          timeSlotModel.getTimeTo());
      eventTimeSlots[i] = eventTimeSlot;
    }

    return eventTimeSlots;

  }

  private final PreparedStatement getInsertSchedulePreparedStatement(T input, Connection connection)
      throws SQLException {

    int eventId = input.getEventId();
    Date startDate = getDate(input.getStartDate(), DATE_FORMAT);
    Date endDate = getDate(input.getEndDate(), DATE_FORMAT);
    String daysOfTheWeek = input.getScheduledDaysOfTheWeekString();

    PreparedStatement ps = connection.prepareStatement(INSERT_SCHEDULE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, eventId);
    ps.setDate(2, startDate);
    ps.setDate(3, endDate);
    ps.setString(4, daysOfTheWeek);

    Timestamp scheduledForTimeStamp = getScheduledForTimestamp(input);

    ps.setTimestamp(5, scheduledForTimeStamp);

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

  private Timestamp getScheduledForTimestamp(T input) {
    if (input.getScheduleDate() == null) {
      return new Timestamp(System.currentTimeMillis());
    }

    String dateTimeString = input.getScheduleDate() + " " + input.getScheduleTime();
    Date date = getDate(dateTimeString, DATE_FORMAT + " " + TIME_FORMAT);

    return new Timestamp(date.getTime());
  }

  private Date getDate(String date, String dateFormat) {

    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    java.util.Date parsed = null;
    try {
      parsed = format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException("Exception while parsing event time slot date: " + date
          + " with format dd.MM.yyyy. The date format can be specified with the system property 'event.schedule.date.format'");
    }
    return new Date(parsed.getTime());

  }
}
