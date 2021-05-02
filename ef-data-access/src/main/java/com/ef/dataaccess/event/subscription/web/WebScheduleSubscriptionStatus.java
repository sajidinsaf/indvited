package com.ef.dataaccess.event.subscription.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ef.dataaccess.Update;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

public abstract class WebScheduleSubscriptionStatus
    implements Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> {

  private final String UPDATE_STATUS = "update event_schedule_subscription_web SET status_id=%d, approver_id=%d where id=%d";

  private final JdbcTemplate jdbcTemplate;

  public WebScheduleSubscriptionStatus(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Integer data(PREventScheduleSubscriptionStatusChangeBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int approverId = input.getApproverId();
    long subscriptionId = input.getSubscriptionId();

    int statusId = getStatus();

    String sql = String.format(UPDATE_STATUS, statusId, approverId, subscriberId, subscriptionId);
    int updateCount = jdbcTemplate.update(sql);

    return updateCount;
  }

  protected abstract int getStatus();
}
