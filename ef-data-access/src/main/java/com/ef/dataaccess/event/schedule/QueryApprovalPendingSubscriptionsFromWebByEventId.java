package com.ef.dataaccess.event.schedule;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.util.DateUtil;
import com.ef.dataaccess.Query;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREventScheduleSubscriptionWeb;

@Component(value = "queryApprovalPendingSubscriptionsFromWebByEventId")
public class QueryApprovalPendingSubscriptionsFromWebByEventId
    implements Query<Integer, List<PREventScheduleSubscriptionWeb>> {

  private static final Logger logger = LoggerFactory.getLogger(QueryApprovalPendingSubscriptionsFromWebByEventId.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private static final String SELECT = "select * from event_schedule_subscription_web where event_id=%d and preferred_date > '%s'";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryApprovalPendingSubscriptionsFromWebByEventId(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<PREventScheduleSubscriptionWeb> data(Integer eventId) {
    String today = new DateUtil().formatDate(new java.util.Date(), DateUtil.yyyy_dash_MM_dash_dd_format);

//    PREventScheduleSubscriptionWeb(int id, int eventId, int eventScheduleId, String firstName, String lastName,
//        String email, String phone, Date preferredDate, String preferredTime, String criteria, String address,
//        String city, String gender, int statusId)

    List<PREventScheduleSubscriptionWeb> schedules = jdbcTemplate.query(String.format(SELECT, eventId, today),
        (rs, rowNum) -> new PREventScheduleSubscriptionWeb(rs.getInt("ID"), rs.getInt("EVENT_ID"),
            rs.getLong("EVENT_SCHEDULE_ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
            rs.getString("EMAIL"), rs.getString("PHONE"), rs.getDate("PREFERRED_DATE"), rs.getString("PREFERRED_TIME"),
            rs.getString("CRTIERIA_STRING"), rs.getString("ADDRESS"), rs.getString("CITY"), rs.getString("GENDER"),
            rs.getInt("STATUS_ID")));

    return schedules;
  }

  private boolean isAfterToday(Date scheduleDate) {
    return scheduleDate.toLocalDate().isAfter(LocalDate.now());
  }

  private Comparator<EventScheduleSubscription> comparator() {
    // Compare the subscriptions by name and id
    Comparator<EventScheduleSubscription> subscriptionComparator = Comparator
        .comparing(EventScheduleSubscription::getId).thenComparing(EventScheduleSubscription::getSubscriberId);
    return subscriptionComparator;
  }

}
