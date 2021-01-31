package com.ef.dataaccess.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.event.AbstractPREventScheduleBindingModel;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.member.MemberLoginBindingModel;

@Component("insertPREventSchedule")
public class InsertPREventSchedule<T extends AbstractPREventScheduleBindingModel> implements Insert<T, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventSchedule.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_EVENT_STATEMENT = "INSERT INTO event(uuid, event_type_id, domain_id, cap, exclusions, member_id, event_venue_id, notes) VALUES (?,?,?,?,?,?,?,?)";
  private final String INSERT_SCHEDULE_STATEMENT = "INSERT INTO event_schedule(event_id, start_date, end_date,days_of_the_week) VALUES (?,?,?,?)";
  private final String INSERT_TIMESLOT_STATEMENT = "INSERT INTO event_schedule_timeslot(event_schedule_id, start_time, end_time) VALUES (?,?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public InsertPREventSchedule(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("memberTypeCache") MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;

  }

//  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;

//  private PREventCriteriaBindingModel[] eventCriteria;

//  private String notes;  
//  
  @Override
  public PREvent data(final T input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

//    insertEventTimeSlots(input);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    int eventId = jdbcTemplate.update(connection -> {
      return getInsertEventPreparedStatement(input, connection);
    }, keyHolder);

    return null;
  }

  private Integer getLocationId(PREventBindingModel input) {
    return input.getEventLocation().getId();
  }

  private final PreparedStatement getInsertEventPreparedStatement(T input, Connection connection) throws SQLException {
    PreparedStatement ps = connection.prepareStatement(INSERT_EVENT_STATEMENT);
    ps.setString(1, "");
    return ps;
  }

  private int getMemberId(PREventBindingModel input) {
    try {
      String emailId = input.getEventCreatorEmailId();
      MemberLoginBindingModel emailAndType = new MemberLoginBindingModel();
      emailAndType.setEmail(emailId);
      emailAndType.setMemberType(memberTypeCache.getMemberType(1));

      return -1;

      // return queryMemberIdByEmailAndMemberType.data(emailAndType);
    } catch (EmptyResultDataAccessException e) {
      logUtil.warn(logger, "No member information found for Event input: ", input);
      return -1;
    }
  }
}
