package com.ef.dataaccess.event.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "querySubscriptionIdByMemberIdEventIDAndStatusId")
public class QuerySubscriptionIdByMemberIdEventIDAndStatusId
    implements Query<SubscriberDeliverableSubmissionBindingModel, Long> {

  private final String QUERY_SUBSCRIPTION_ID = "select ess.id from event_schedule_subscription ess, event_schedule es where es.event_id=%d and ess.event_schedule_id=es.id and ess.status_id in (%d, %d, %d) and ess.subscriber_id=%d";
  private static final Logger logger = LoggerFactory.getLogger(QuerySubscriptionIdByMemberIdEventIDAndStatusId.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QuerySubscriptionIdByMemberIdEventIDAndStatusId(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Long data(SubscriberDeliverableSubmissionBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int eventId = input.getEventId();

    String sql = String.format(QUERY_SUBSCRIPTION_ID, eventId, EventStatusMeta.KNOWN_STATUS_ID_APPROVED,
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED, EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED,
        subscriberId);
    long subscriptionId = jdbcTemplate.queryForObject(sql, Long.class);
    logUtil.debug(logger, "Retrieved subscrption for member deliverable: ", input, " Subscription id:", subscriptionId);

    return subscriptionId;
  }

}
