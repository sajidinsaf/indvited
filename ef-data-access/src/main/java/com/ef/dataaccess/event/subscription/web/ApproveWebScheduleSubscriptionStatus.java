package com.ef.dataaccess.event.subscription.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.model.event.EventStatusMeta;

@Component(value = "approveWebScheduleSubscriptionStatus")
public class ApproveWebScheduleSubscriptionStatus extends WebScheduleSubscriptionStatus {

  @Autowired
  public ApproveWebScheduleSubscriptionStatus(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected int getStatus() {
    return EventStatusMeta.KNOWN_STATUS_ID_APPROVED;
  }

}
