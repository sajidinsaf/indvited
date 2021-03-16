package com.ef.dataaccess.event.blogger.deliverable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

@Component(value = "insertDeliverableRejectionAndUpdateSubscription")
public class InsertDeliverableRejectionAndUpdateSubscription
    implements Update<SubscriberDeliverableSubmissionBindingModel, String> {

  private static final Logger logger = LoggerFactory.getLogger(InsertDeliverableRejectionAndUpdateSubscription.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT = "insert into member_deliverable_data_rejection (member_id, event_id, rejection_comment) VALUES (%d,%d,'%s')";
  private final String UPDATE_SUBSCRIPTION_STATUS = "update event_schedule_subscription SET status_id=%d where id=%d";

  private final Query<SubscriberDeliverableSubmissionBindingModel, Long> querySubscriptionIdByMemberIdEventIDAndStatusId;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertDeliverableRejectionAndUpdateSubscription(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("querySubscriptionIdByMemberIdEventIDAndStatusId") Query<SubscriberDeliverableSubmissionBindingModel, Long> querySubscriptionIdByMemberIdEventIDAndStatusId) {
    this.jdbcTemplate = jdbcTemplate;
    this.querySubscriptionIdByMemberIdEventIDAndStatusId = querySubscriptionIdByMemberIdEventIDAndStatusId;
  }

  @Override
  public String data(SubscriberDeliverableSubmissionBindingModel input) {

    int subscriberId = input.getSubscriberId();
    int eventId = input.getEventId();
    String rejectionComment = input.getComments();

    String sql = String.format(INSERT, subscriberId, eventId, rejectionComment);
    jdbcTemplate.update(sql);
    logUtil.debug(logger, "Created new entry for: ", input);

    long subscriptionId = querySubscriptionIdByMemberIdEventIDAndStatusId.data(input);

    sql = String.format(UPDATE_SUBSCRIPTION_STATUS, EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED,
        subscriptionId);
    jdbcTemplate.update(sql);

    logUtil.debug(logger, "Updated subscrption: ", subscriptionId, " to status id: ",
        EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_REJECTED);

    return Update.METHOD_INSERT;
  }

}
