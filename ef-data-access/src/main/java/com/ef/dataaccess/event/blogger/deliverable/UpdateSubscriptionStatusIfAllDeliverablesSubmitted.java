package com.ef.dataaccess.event.blogger.deliverable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "updateSubscriptionStatusIfAllDeliverablesSubmitted")
public class UpdateSubscriptionStatusIfAllDeliverablesSubmitted
    implements Update<SubscriberDeliverableSubmissionBindingModel, String> {

  private final String INSERT = "insert into member_deliverable_data (member_id, deliverable_id, event_id, deliverable_detail) VALUES (%d,%d,%d,'%s')";
  private final String UPDATE = "update member_deliverable_data SET deliverable_detail='%s' where member_id = %d and event_id = %d and deliverable_id = %d";

  private final String QUERY_SUBSCRIPTION_ID = "select ess.id from event_schedule_subscription ess, event e, event_schedule es where es.event_id=%d and ess.event_schedule_id=es.id and ess.status_id in (%d, %d) and ess.subscriber_id=%d";
  // private final String UPDATE_STATUS = "update event_schedule_subscription ess,
  // event e, event_schedule es set es.status_id=%d where es.event_id=%d and
  // ess.event_schedule_id=es.id and ess.status_id in (%d, %d) and
  // ess.subscriber_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UpdateSubscriptionStatusIfAllDeliverablesSubmitted(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public String data(SubscriberDeliverableSubmissionBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int eventId = input.getEventId();
    int deliverableId = input.getDeliverableId();
    String deliverableDetail = input.getDeliverableUrl();

    String updateMethod = null;

    try {
      String sql = String.format(INSERT, subscriberId, deliverableId, eventId, deliverableDetail);
      jdbcTemplate.update(sql);
      updateMethod = Update.METHOD_INSERT;
    } catch (org.springframework.dao.DuplicateKeyException e) {
      String sql = String.format(UPDATE, deliverableDetail, subscriberId, eventId, deliverableId);
      jdbcTemplate.update(sql);
      updateMethod = Update.METHOD_UPDATE;
    }

    String sql = String.format(QUERY_SUBSCRIPTION_ID, eventId, EventStatusMeta.KNOWN_STATUS_ID_APPROVED,
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED, subscriberId);
    int subscriptionId = jdbcTemplate.queryForObject(sql, Integer.class);
    System.out.println(subscriptionId);
    return updateMethod;
  }

}
