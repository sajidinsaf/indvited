package com.ef.dataaccess.event.subscription;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventScheduleSubscriptionApp;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;

@Component("insertPrEventScheduleSubscription")
public class InsertPREventScheduleSubscription
    implements Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventScheduleSubscription.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_SCHEDULE_SUBSCRIPTION_STATEMENT = "INSERT INTO event_schedule_subscription (event_schedule_id, subscriber_id, schedule_date, preferred_time, status_id) VALUES (?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  private final EventStatusMetaCache eventStatusMetaCache;

  @Autowired
  public InsertPREventScheduleSubscription(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventStatusMetaCache eventStatusMetaCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventStatusMetaCache = eventStatusMetaCache;

  }

  @Override
  public EventScheduleSubscription data(final PREventScheduleSubscriptionBindingModel input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert the PR Event schedule subscription and get the subscription id.
    jdbcTemplate.update(connection -> {
      return getInsertEventScheduleSubscriptionBindingModel(input, connection);
    }, keyHolder);

    long subscriptionId = keyHolder.getKey().longValue();
    logUtil.debug(logger, "created event schedule subscription with id", subscriptionId);

    EventScheduleSubscription eventScheduleSubscription = new EventScheduleSubscriptionApp(subscriptionId,
        input.getScheduleSubscriptionId(), input.getSubscriberId(), getDate(input.getScheduleDate()),
        input.getPreferredTime(), eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));

    return eventScheduleSubscription;
  }

  private final PreparedStatement getInsertEventScheduleSubscriptionBindingModel(
      PREventScheduleSubscriptionBindingModel input, Connection connection) throws SQLException {

    PreparedStatement ps = connection.prepareStatement(INSERT_SCHEDULE_SUBSCRIPTION_STATEMENT,
        Statement.RETURN_GENERATED_KEYS);
    ps.setLong(1, input.getScheduleSubscriptionId());
    ps.setInt(2, input.getSubscriberId());
    ps.setDate(3, getDate(input.getScheduleDate()));
    ps.setString(4, input.getPreferredTime());
    ps.setInt(5, EventStatusMeta.KNOWN_STATUS_ID_APPLIED);

    return ps;
  }

  private Date getDate(String dateString) {
    return new DateUtil().parseSqlDateFromEventDisplayString(dateString);
  }
}
