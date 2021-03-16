package com.ef.dataaccess.event.blogger.deliverable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "insertOrUpdateSubscriberDeliverable")
public class InsertOrUpdateSubscriberDeliverable
    implements Update<SubscriberDeliverableSubmissionBindingModel, String> {

  private static final Logger logger = LoggerFactory.getLogger(InsertOrUpdateSubscriberDeliverable.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT = "insert into member_deliverable_data (member_id, deliverable_id, event_id, deliverable_detail) VALUES (%d,%d,%d,'%s')";
  private final String UPDATE = "update member_deliverable_data SET deliverable_detail='%s' where member_id = %d and event_id = %d and deliverable_id = %d";

  private final String QUERY_SUBSCRIPTION_ID = "select ess.id from event_schedule_subscription ess, event_schedule es where es.event_id=%d and ess.event_schedule_id=es.id and ess.status_id in (%d, %d) and ess.subscriber_id=%d";
  private final String UPDATE_SUBSCRIPTION_STATUS = "update event_schedule_subscription SET status_id=%d where id=%d";

  // private final String UPDATE_STATUS = "update event_schedule_subscription ess,
  // event e, event_schedule es set es.status_id=%d where es.event_id=%d and
  // ess.event_schedule_id=es.id and ess.status_id in (%d, %d) and
  // ess.subscriber_id=%d";

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

    String updateMethod = null;

    try {
      String sql = String.format(INSERT, subscriberId, deliverableId, eventId, deliverableDetail);
      jdbcTemplate.update(sql);
      updateMethod = Update.METHOD_INSERT;
      logUtil.debug(logger, "Created new entry for: ", input);
    } catch (org.springframework.dao.DuplicateKeyException e) {
      String sql = String.format(UPDATE, deliverableDetail, subscriberId, eventId, deliverableId);
      jdbcTemplate.update(sql);
      updateMethod = Update.METHOD_UPDATE;
      logUtil.debug(logger, "updated existing entry for: ", input);
    }

    String sql = String.format(QUERY_SUBSCRIPTION_ID, eventId, EventStatusMeta.KNOWN_STATUS_ID_APPROVED,
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED, subscriberId);
    long subscriptionId = jdbcTemplate.queryForObject(sql, Long.class);

    logUtil.debug(logger, "Retrieved subscrption for member deliverable: ", input, " Subscription id:", subscriptionId);
    sql = String.format(UPDATE_SUBSCRIPTION_STATUS, EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED,
        subscriptionId);
    jdbcTemplate.update(sql);
    logUtil.debug(logger, "Updated subscrption: ", subscriptionId, " to status id: ",
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED);

    return updateMethod;
  }

}
