package com.ef.dataaccess.event.blogger.deliverable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "closeSubscriptionOnDeliverableApproval")
public class CloseSubscriptionOnDeliverableApproval
    implements Update<SubscriberDeliverableSubmissionBindingModel, String> {

  public static final String NO_MATCHING_SUBSRIPTION = "NO_MATCHING_SUBSCRIPTION_ID for: ";
  private static final Logger logger = LoggerFactory.getLogger(CloseSubscriptionOnDeliverableApproval.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String QUERY_SUBSCRIPTION_ID = "select ess.id from event_schedule_subscription ess, event_schedule es where es.event_id=%d and ess.event_schedule_id=es.id and ess.status_id in (%d, %d, %d) and ess.subscriber_id=%d";
  private final String UPDATE_SUBSCRIPTION_STATUS = "update event_schedule_subscription SET status_id=%d where id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public CloseSubscriptionOnDeliverableApproval(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public String data(SubscriberDeliverableSubmissionBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int eventId = input.getEventId();

    String sql = String.format(QUERY_SUBSCRIPTION_ID, eventId, EventStatusMeta.KNOWN_STATUS_ID_APPROVED,
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED, EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED,
        subscriberId);
    try {
      long subscriptionId = jdbcTemplate.queryForObject(sql, Long.class);

      logUtil.debug(logger, "Retrieved subscrption for member deliverable: ", input, " Subscription id:",
          subscriptionId);
      sql = String.format(UPDATE_SUBSCRIPTION_STATUS, EventStatusMeta.KNOWN_STATUS_ID_CLOSED, subscriptionId);
      jdbcTemplate.update(sql);
      logUtil.debug(logger, "Closed subscrption: ", subscriptionId, " with status id: ",
          EventStatusMeta.KNOWN_STATUS_ID_CLOSED);
    } catch (EmptyResultDataAccessException e) {
      return NO_MATCHING_SUBSRIPTION + input;
    }

    return METHOD_UPDATE;
  }

}
