package com.ef.dataaccess.event.subscription.web;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ef.common.LRPair;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.util.DateUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;

@Component("insertPREventScheduleSubscriptionWeb")
public class InsertPREventScheduleSubscriptionWeb
    implements Insert<PREventScheduleSubscriptionWebFormBindingModel, EventScheduleSubscription> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventScheduleSubscriptionWeb.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

//  event_schedule_subscription_web
//      event_id INTEGER,
//      event_schedule_id bigint,
//      first_name varchar(25),
//      last_name varchar(25),
//      email varchar(75),
//      phone varchar(10),
//      city varchar(30),
//      gender BOOLEAN,
//      preferred_date date,
//      preferred_time date,
//      criteria_string varchar(500),
//      status_id INTEGER
  private final String INSERT_SCHEDULE_SUBSCRIPTION_WEB_STATEMENT = "INSERT INTO event_schedule_subscription_web  (event_id, event_schedule_id, first_name, last_name, email, phone, address, city, gender, preferred_date, preferred_time, criteria_string, status_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  private final EventStatusMetaCache eventStatusMetaCache;

  private final Query<Pair<Integer, Date>, Long> queryEventScheduleIdByEventIdAndPreferredDate;

  @Autowired
  public InsertPREventScheduleSubscriptionWeb(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventStatusMetaCache eventStatusMetaCache,
      @Qualifier("queryEventScheduleIdByEventIdAndPreferredDate") Query<Pair<Integer, Date>, Long> queryEventScheduleIdByEventIdAndPreferredDate) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventStatusMetaCache = eventStatusMetaCache;
    this.queryEventScheduleIdByEventIdAndPreferredDate = queryEventScheduleIdByEventIdAndPreferredDate;

  }

  @Override
  public EventScheduleSubscription data(final PREventScheduleSubscriptionWebFormBindingModel input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    long scheduleId = getScheduleId(input);

    // Insert the PR Event schedule subscription and get the subscription id.
    jdbcTemplate.update(connection -> {
      return getInsertEventScheduleSubscriptionBindingModel(input, scheduleId, connection);
    }, keyHolder);

    long subscriptionId = keyHolder.getKey().longValue();
    logUtil.debug(logger, "created event schedule subscription with id", subscriptionId);

    EventScheduleSubscription eventScheduleSubscription = new EventScheduleSubscription(subscriptionId, scheduleId, -1,
        getDate(input.getPreferredDate()), input.getPreferredTime(),
        eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));

    return eventScheduleSubscription;
  }

  private final PreparedStatement getInsertEventScheduleSubscriptionBindingModel(
      PREventScheduleSubscriptionWebFormBindingModel input, long scheduleId, Connection connection)
      throws SQLException {

//  event_id INTEGER,
//  event_schedule_id INTEGER,
//  first_name varchar(25),
//  last_name varchar(25),
//  email varchar(75),
//  phone varchar(10),
//  city varchar(30),
//  gender BOOLEAN,
//  preferred_date date,
//  preferred_time date,
//  criteria_string varchar(500),
//  status_id INTEGER,
//  entry_timestamp timestamp

    PreparedStatement ps = connection.prepareStatement(INSERT_SCHEDULE_SUBSCRIPTION_WEB_STATEMENT,
        Statement.RETURN_GENERATED_KEYS);
    ps.setLong(1, input.getEventId());
    ps.setLong(2, scheduleId);
    ps.setString(3, input.getFirstName());
    ps.setString(4, input.getLastName());
    ps.setString(5, input.getEmail());
    ps.setString(6, input.getPhone());
    ps.setString(7, input.getAddress());
    ps.setString(8, input.getCity());
    ps.setString(9, input.getGender());
    ps.setDate(10, getDate(input.getPreferredDate()));
    ps.setString(11, input.getPreferredTime());
    ps.setString(12, input.getCriteria());
    ps.setInt(13, input.getStatusId());

    return ps;
  }

  private Date getDate(String dateString) {
    return new DateUtil().parseSqlDateFromEventDisplayString(dateString);
  }

  private long getScheduleId(PREventScheduleSubscriptionWebFormBindingModel input) {

    Date preferredDate = getDate(input.getPreferredDate());
    int eventId = input.getEventId();

    Pair<Integer, Date> eventIdAndPreferredDatePair = new LRPair<Integer, Date>(eventId, preferredDate);

    return queryEventScheduleIdByEventIdAndPreferredDate.data(eventIdAndPreferredDatePair);

  }
}
