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
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.dataaccess.event.subscription.web.util.InputDataUtil;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREventScheduleSubscriptionWeb;

@Component(value = "queryApprovalPendingSubscriptionsFromWeb")
public class QueryApprovalPendingSubscriptionsFromWeb implements Query<Integer, List<PREventScheduleSubscriptionWeb>> {

  private static final Logger logger = LoggerFactory.getLogger(QueryApprovalPendingSubscriptionsFromWeb.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private static final String SELECT = "select * from event_schedule_subscription_web essw, event e where preferred_date > '%s' and e.id = essw.event_id and e.member_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final InputDataUtil inputDataUtil;
  private final EventStatusMetaCache eventStatusMetaCache;

  @Autowired
  public QueryApprovalPendingSubscriptionsFromWeb(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      InputDataUtil inputDataUtil, EventStatusMetaCache eventStatusMetaCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.inputDataUtil = inputDataUtil;
    this.eventStatusMetaCache = eventStatusMetaCache;
  }

  @Override
  public List<PREventScheduleSubscriptionWeb> data(Integer prId) {
    String today = new DateUtil().formatDate(new java.util.Date(), DateUtil.yyyy_dash_MM_dash_dd_format);

//    PREventScheduleSubscriptionWeb(int id, int eventId, int eventScheduleId, String firstName, String lastName,
//        String email, String phone, Date preferredDate, String preferredTime, String criteria, String address,
//        String city, String gender, int statusId)

    List<PREventScheduleSubscriptionWeb> schedules = jdbcTemplate.query(String.format(SELECT, today, prId),
        (rs, rowNum) -> new PREventScheduleSubscriptionWeb(rs.getInt("ID"), rs.getInt("EVENT_ID"),
            rs.getLong("EVENT_SCHEDULE_ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
            rs.getString("EMAIL"), rs.getString("PHONE"), rs.getDate("PREFERRED_DATE"), rs.getString("PREFERRED_TIME"),
            inputDataUtil.buildMemberCriteriaList(rs.getString("CRITERIA_STRING")), rs.getString("ADDRESS"),
            rs.getString("CITY"), rs.getString("GENDER"),
            eventStatusMetaCache.getEventStatusMeta(rs.getInt("STATUS_ID"))));

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
