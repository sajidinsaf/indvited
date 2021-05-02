package com.ef.dataaccess.event.subscription.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.model.event.EventStatusMeta;

@Component(value = "rejectWebScheduleSubscriptionStatus")
public class RejectWebScheduleSubscriptionStatus extends WebScheduleSubscriptionStatus {

  @Autowired
  public RejectWebScheduleSubscriptionStatus(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected int getStatus() {
    return EventStatusMeta.KNOWN_STATUS_ID_REJECTED;
  }

}
