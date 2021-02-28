package com.ef.dataaccess.event.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

@Component(value = "approvePREventScheduleSubscriptionStatus")
public class ApprovePREventScheduleSubscription
    implements Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> {

  private final String UPDATE_APPROVE = "update event_schedule_subscription SET status_id=%d, approver_id=%d where subscriber_id=%d and id=%d";
  private final String UPDATE_REDUNDANT = "update event_schedule_subscription SET status_id=%d where subscriber_id=%d and event_schedule_id=%d and id != %d";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, List<Long>> queryPREventScheduleIdListByEventId;

  @Autowired
  public ApprovePREventScheduleSubscription(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryPREventScheduleIdListByEventId") Query<Integer, List<Long>> queryPREventScheduleIdListByEventId) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryPREventScheduleIdListByEventId = queryPREventScheduleIdListByEventId;
  }

  @Override
  public Integer data(PREventScheduleSubscriptionStatusChangeBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int approverId = input.getApproverId();
    long subscriptionId = input.getSubscriptionId();

    int approvedStatusId = EventStatusMeta.KNOWN_STATUS_ID_APPROVED;
    int redundantStatusId = EventStatusMeta.KNOWN_STATUS_ID_REDUNDANT;

    String sql = String.format(UPDATE_APPROVE, approvedStatusId, approverId, subscriberId, subscriptionId);
    int updateCount = jdbcTemplate.update(sql);

    List<Long> allScheduleIdsForEvent = queryPREventScheduleIdListByEventId.data(input.getEventId());

    for (long scheduleId : allScheduleIdsForEvent) {
      sql = String.format(UPDATE_REDUNDANT, redundantStatusId, subscriberId, scheduleId, subscriptionId);
      updateCount += jdbcTemplate.update(sql);
    }
    return updateCount;
  }

}
