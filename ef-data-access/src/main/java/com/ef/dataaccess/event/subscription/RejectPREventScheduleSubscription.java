package com.ef.dataaccess.event.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

@Component(value = "rejectPREventScheduleSubscriptionStatus")
public class RejectPREventScheduleSubscription
    implements Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> {

  private final String UPDATE_REJECTED = "update event_schedule_subscription SET status_id=%d, approver_id=%d where subscriber_id=%d and event_schedule_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public RejectPREventScheduleSubscription(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventScheduleSubscriptionByScheduleId") Query<Long, List<EventScheduleSubscription>> queryEventScheduleSubscriptionByScheduleId) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Integer data(PREventScheduleSubscriptionStatusChangeBindingModel input) {

    int subscriberId = input.getSubscriberId();
    long scheduleId = input.getScheduleId();
    int approverId = input.getApproverId();

    int redundantStatusId = EventStatusMeta.KNOWN_STATUS_ID_REJECTED;

    String sql = String.format(UPDATE_REJECTED, redundantStatusId, approverId, subscriberId, scheduleId);

    int updateCount = jdbcTemplate.update(sql);

    return updateCount;
  }

}
