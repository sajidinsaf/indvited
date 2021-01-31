
package com.ef.dataaccess.event;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventTimeSlotBindingModel;

@Component("insertPREventTimeSlot")
public class InsertPREventTimeSlot implements Insert<Pair<PREventBindingModel, PREvent>, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventTimeSlot.class);

  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO event_time_slot(event_id, scheduled_date, time_from, time_to) VALUES (?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertPREventTimeSlot(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @SuppressWarnings("deprecation")
  @Override
  public PREvent data(Pair<PREventBindingModel, PREvent> input) {

//    PREventTimeSlotBindingModel[] eventTimeSlotBindingModelList = input.getLeft().getPrEventTimeSlotBindingModel();
//    PREvent prEvent = input.getRight();
//    if (eventTimeSlotBindingModelList == null || eventTimeSlotBindingModelList.length == 0) {
//      loggingUtil.debug(logger, "No event time slots specified for event: ", input.getRight().getId());
//      return input.getRight();
//    }
//    EventTimeSlot[] eventTimeSlotList = new EventTimeSlot[eventTimeSlotBindingModelList.length];
//    int count = 0;
//    for (PREventTimeSlotBindingModel eventTimeSlotBindingModel : eventTimeSlotBindingModelList) {
//      int eventId = input.getRight().getId();
//      String timeFrom = eventTimeSlotBindingModel.getTimeFrom();
//      String timeTo = eventTimeSlotBindingModel.getTimeTo();
//
//      Date date = getDate(eventTimeSlotBindingModel);
//
//      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { eventId, date, timeFrom, timeTo });
//
//      EventTimeSlot eventTimeSlot = new EventTimeSlot(date, timeFrom, timeTo);
//      eventTimeSlotList[count] = eventTimeSlot;
//      ++count;
//    }
//
//    prEvent.setEventTimeSlots(eventTimeSlotList);
//    return prEvent;
    return null;
  }

  private Date getDate(PREventTimeSlotBindingModel timeSlot) {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date parsed = new java.util.Date();
//    try {
//      // parsed = format.parse(timeSlot.getEventDate());
//    } catch (ParseException e) {
//      throw new RuntimeException(
//          "Exception while parsing event time slot date: " + timeSlot.getEventDate() + " with format dd/MM/yyyy");
//    }
    return new java.sql.Date(parsed.getTime());

  }

}
