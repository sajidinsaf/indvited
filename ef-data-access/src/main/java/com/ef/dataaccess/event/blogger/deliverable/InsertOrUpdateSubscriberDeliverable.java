package com.ef.dataaccess.event.blogger.deliverable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Update;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "insertOrUpdateSubscriberDeliverable")
public class InsertOrUpdateSubscriberDeliverable
    implements Update<SubscriberDeliverableSubmissionBindingModel, String> {

  private final String INSERT = "insert into member_deliverable_data (member_id, deliverable_id, event_id, deliverable_detail) VALUES (%d,%d,%d,'%s')";
  private final String UPDATE = "update member_deliverable_data SET deliverable_detail='%s' where member_id = %d and event_id = %d and deliverable_id = %d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertOrUpdateSubscriberDeliverable(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public String data(SubscriberDeliverableSubmissionBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int eventId = input.getEventId();
    int deliverableId = input.getDeliverableId();
    String deliverableDetail = input.getDeliverableUrl();

    try {
      String sql = String.format(INSERT, subscriberId, deliverableId, eventId, deliverableDetail);
      jdbcTemplate.update(sql);
      return Update.METHOD_INSERT;
    } catch (org.springframework.dao.DuplicateKeyException e) {
      e.printStackTrace();
      String sql = String.format(UPDATE, deliverableDetail, subscriberId, eventId, deliverableId);
      jdbcTemplate.update(sql);
      return Update.METHOD_UPDATE;
    }

  }

}
