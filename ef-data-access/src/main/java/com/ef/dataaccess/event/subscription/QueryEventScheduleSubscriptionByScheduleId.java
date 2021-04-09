package com.ef.dataaccess.event.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.member.Member;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "queryEventScheduleSubscriptionByScheduleId")
public class QueryEventScheduleSubscriptionByScheduleId implements Query<Long, List<EventScheduleSubscription>> {

  private final String SELECT_EVENT = "select * from event_schedule_subscription where event_schedule_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventStatusMetaCache eventStatusMetaCache;
  private final Query<Integer, Member> queryMemberById;
  private final Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId;

  @Autowired
  public QueryEventScheduleSubscriptionByScheduleId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventStatusMetaCache eventStatusMetaCache, @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("queryMemberCriteriaDataByMemberId") Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventStatusMetaCache = eventStatusMetaCache;
    this.queryMemberById = queryMemberById;
    this.queryMemberCriteriaDataByMemberId = queryMemberCriteriaDataByMemberId;
  }

  @Override
  public List<EventScheduleSubscription> data(final Long scheduleId) {

    List<EventScheduleSubscription> eventScheduleSubscriptions = jdbcTemplate.query(
        String.format(SELECT_EVENT, scheduleId),
        (rs, rowNum) -> new EventScheduleSubscription(rs.getLong("ID"), rs.getLong("EVENT_SCHEDULE_ID"),
            rs.getInt("SUBSCRIBER_ID"), rs.getDate("SCHEDULE_DATE"), rs.getString("PREFERRED_TIME"),
            eventStatusMetaCache.getEventStatusMeta(rs.getInt("STATUS_ID"))));

    for (EventScheduleSubscription subscription : eventScheduleSubscriptions) {
      int subscriberId = subscription.getSubscriberId();
      Member member = queryMemberById.data(subscriberId);
      subscription.setSubscriber(member);

      List<MemberCriteriaData> memberCriteriaDataList = queryMemberCriteriaDataByMemberId.data(subscriberId);
      member.setMemberCriteriaDataList(memberCriteriaDataList);

    }
    return eventScheduleSubscriptions;
  }

}
