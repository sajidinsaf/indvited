package com.ef.eventservice.controller.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ef.common.MapBasedContext;
import com.ef.common.Strategy;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.MessagePacket;
import com.ef.dataaccess.Update;
import com.ef.eventservice.controller.EventControllerConstants;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

@Component("subscriptionStatusUpdateStrategy")
public class SubscriptionStatusUpdateStrategy implements Strategy<MapBasedContext, ResponseEntity<?>> {
  private static final Logger logger = LoggerFactory.getLogger(SubscriptionStatusUpdateStrategy.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Map<String, Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer>> statusUpdateBeanMap;

  public SubscriptionStatusUpdateStrategy(
      @Qualifier("approvePREventScheduleSubscriptionStatus") Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> approvePREventScheduleSubscriptionStatus,
      @Qualifier("rejectPREventScheduleSubscriptionStatus") Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> rejectPREventScheduleSubscriptionStatus,
      @Qualifier("approveWebScheduleSubscriptionStatus") Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> approveWebScheduleSubscriptionStatus,
      @Qualifier("rejectWebScheduleSubscriptionStatus") Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> rejectWebScheduleSubscriptionStatus) {

    statusUpdateBeanMap = new HashMap<String, Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer>>();
    statusUpdateBeanMap.put(
        key(EventScheduleSubscription.SUBSCRIPTION_MODE_APP, EventStatusMeta.KNOWN_STATUS_ID_APPROVED),
        approvePREventScheduleSubscriptionStatus);
    statusUpdateBeanMap.put(
        key(EventScheduleSubscription.SUBSCRIPTION_MODE_APP, EventStatusMeta.KNOWN_STATUS_ID_REJECTED),
        rejectPREventScheduleSubscriptionStatus);
    statusUpdateBeanMap.put(
        key(EventScheduleSubscription.SUBSCRIPTION_MODE_WEB, EventStatusMeta.KNOWN_STATUS_ID_APPROVED),
        approveWebScheduleSubscriptionStatus);
    statusUpdateBeanMap.put(
        key(EventScheduleSubscription.SUBSCRIPTION_MODE_WEB, EventStatusMeta.KNOWN_STATUS_ID_REJECTED),
        rejectWebScheduleSubscriptionStatus);
  }

  @Override
  public ResponseEntity<?> apply(MapBasedContext context) {

    int updateStatusAction = context.get(EventControllerConstants.UPDATE_SUBSCRIPTION_STATUS_ACTION);
    PREventScheduleSubscriptionStatusChangeBindingModel model = context
        .get(EventControllerConstants.UPDATE_SUBSCRIPTION_STATUS_BINDING_MODEL);
    int subscriptionMode = model.getSubscriptionMode();

    HttpServletRequest httpRequest = context.get(EventControllerConstants.HTTP_REQUEST);

    String key = key(updateStatusAction, subscriptionMode);

    Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> updateSubscription = statusUpdateBeanMap
        .get(key);

    return updatesubscriptionStatus(model, httpRequest, updateSubscription);
  }

  public ResponseEntity<?> updatesubscriptionStatus(PREventScheduleSubscriptionStatusChangeBindingModel model,
      HttpServletRequest request,
      Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> updateSubscriptionStatus) {

    logUtil.debug(logger, "Received subscription data ", model);

    int result = updateSubscriptionStatus.data(model);
    final String returnString = result > 0 ? "success" : "failed";

    logUtil.debug(logger, "Subscription status update result ", returnString, " No of rows updated: ", result);
    MessagePacket<String> p = new MessagePacket<String>() {

    };
    p.setPayload(returnString);

    return new ResponseEntity<MessagePacket<String>>(p, HttpStatus.OK);
  }

  private String key(int subscriptionMode, int status) {
    return subscriptionMode + "_" + status;
  }
}
