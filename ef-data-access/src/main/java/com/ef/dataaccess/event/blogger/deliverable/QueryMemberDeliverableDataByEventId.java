package com.ef.dataaccess.event.blogger.deliverable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventDeliverableMetadataCache;
import com.ef.model.event.EventDeliverable;
import com.ef.model.event.SubscriberDeliverableSubmission;
import com.ef.model.event.wrapper.MemberEventDeliverableDataWrapper;
import com.ef.model.member.Member;

@Component(value = "queryMemberDeliverableDataByEventId")
public class QueryMemberDeliverableDataByEventId implements Query<Integer, List<MemberEventDeliverableDataWrapper>> {

  private final String SELECT = "select * from member_deliverable_data where event_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventDeliverableMetadataCache eventDeliverableMetadataCache;
  private final Query<Integer, Member> queryMemberById;
  private final Query<Integer, List<EventDeliverable>> queryEventDeliverableListByEventId;

  @Autowired
  public QueryMemberDeliverableDataByEventId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventDeliverableMetadataCache eventDeliverableMetadataCache,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("queryEventDeliverableListByEventId") Query<Integer, List<EventDeliverable>> queryEventDeliverableListByEventId) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventDeliverableMetadataCache = eventDeliverableMetadataCache;
    this.queryMemberById = queryMemberById;
    this.queryEventDeliverableListByEventId = queryEventDeliverableListByEventId;
  }

  @Override
  public List<MemberEventDeliverableDataWrapper> data(Integer eventId) {

    List<SubscriberDeliverableSubmission> deliverables = jdbcTemplate.query(String.format(SELECT, eventId),
        (rs, rowNum) -> new SubscriberDeliverableSubmission(rs.getInt("EVENT_ID"), rs.getInt("MEMBER_ID"),
            rs.getString("DELIVERABLE_DETAIL"),
            eventDeliverableMetadataCache.getEventDeliverable(rs.getInt("DELIVERABLE_ID"))));

    Map<Integer, List<SubscriberDeliverableSubmission>> memberDataMap = new HashMap<Integer, List<SubscriberDeliverableSubmission>>();

    for (SubscriberDeliverableSubmission s : deliverables) {
      List<SubscriberDeliverableSubmission> subscriberDeliverables = memberDataMap.get(s.getSubscriberId());
      subscriberDeliverables = subscriberDeliverables == null ? new ArrayList<SubscriberDeliverableSubmission>()
          : subscriberDeliverables;
      subscriberDeliverables.add(s);
      memberDataMap.put(s.getSubscriberId(), subscriberDeliverables);
    }

    List<MemberEventDeliverableDataWrapper> memberDataDeliverableWrappers = new ArrayList<MemberEventDeliverableDataWrapper>();

    List<EventDeliverable> eventDeliverables = queryEventDeliverableListByEventId.data(eventId);

    for (int memberId : memberDataMap.keySet()) {
      Member member = queryMemberById.data(memberId);
      memberDataDeliverableWrappers.add(new MemberEventDeliverableDataWrapper(member, memberDataMap.get(memberId)));
    }

    // Add entries for missing deliverables as requried by the UI
    if (memberDataDeliverableWrappers.size() < eventDeliverables.size()) {
      addPlaceHoldersForMissingDeliverables(eventId, memberDataDeliverableWrappers, eventDeliverables);
    }
    return memberDataDeliverableWrappers;
  }

  private void addPlaceHoldersForMissingDeliverables(int eventId,
      List<MemberEventDeliverableDataWrapper> memberDataDeliverableWrappers, List<EventDeliverable> eventDeliverables) {

    Map<Integer, EventDeliverable> eventDeliverablesMap = new HashMap<Integer, EventDeliverable>();
    for (EventDeliverable ed : eventDeliverables) {
      eventDeliverablesMap.put(ed.getDeliverableId(), ed);
    }

    for (MemberEventDeliverableDataWrapper md : memberDataDeliverableWrappers) {
      Set<Integer> sdsDeliverableIds = new HashSet<Integer>();

      for (SubscriberDeliverableSubmission sds : md.getDeliverables()) {
        sdsDeliverableIds.add(sds.getEventDeliverableMeta().getId());
      }

      for (int edId : eventDeliverablesMap.keySet()) {
        if (!sdsDeliverableIds.contains(edId)) {
          EventDeliverable ed = eventDeliverablesMap.get(edId);

          SubscriberDeliverableSubmission missingSubmission = new SubscriberDeliverableSubmission(eventId,
              md.getSubscriber().getId(), "Not yet submitted",
              eventDeliverableMetadataCache.getEventDeliverable(ed.getDeliverableId()));
          md.getDeliverables().add(missingSubmission);
        }
      }

    }

  }

}
