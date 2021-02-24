package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_AWAITING_APPROVAL_SUBSCRIPTIONS_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIBE_SCHEDULE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;
import com.ef.model.event.PREventScheduleSubscriptionBindingModelWorkaround;

/**
 * Handles requests for the event service.
 */
@Controller
public class PREventScheduleSubscriptionController {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleSubscriptionController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription;
  private final Query<Integer, List<PREvent>> queryApprovalPendingSubscriptionsByPrId;

  @Autowired
  public PREventScheduleSubscriptionController(
      @Qualifier("insertPrEventScheduleSubscription") Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription,
      @Qualifier("queryApprovalPendingSubscriptionsByPrId") Query<Integer, List<PREvent>> queryApprovalPendingSubscriptionsByPrId) {
    this.insertPrEventScheduleSubscription = insertPrEventScheduleSubscription;
    this.queryApprovalPendingSubscriptionsByPrId = queryApprovalPendingSubscriptionsByPrId;
  }

  @PostMapping(SUBSCRIBE_SCHEDULE)
  @ResponseBody
  public ResponseEntity<?> addEventScheduleSubscriptions(
      @RequestBody PREventScheduleSubscriptionBindingModelWorkaround workaround, HttpServletRequest request) {

    logUtil.debug(logger, "Received subscription data ", workaround);
    PREventScheduleSubscriptionBindingModel[] scheduleSubscriptionChoices = workaround.getSubscriptions();

    logUtil.debug(logger, "Parsed array from workaround ", Arrays.toString(scheduleSubscriptionChoices));

    List<EventScheduleSubscription> persistedSubscriptions = new ArrayList<EventScheduleSubscription>();

    for (PREventScheduleSubscriptionBindingModel subscription : scheduleSubscriptionChoices) {
      if (subscription == null) {
        continue;
      }
      persistedSubscriptions.add(insertPrEventScheduleSubscription.data(subscription));
      logUtil.debug(logger, "Persisted subscription ", subscription);
    }
    return new ResponseEntity<List<EventScheduleSubscription>>(persistedSubscriptions, HttpStatus.OK);
  }

  @GetMapping(GET_AWAITING_APPROVAL_SUBSCRIPTIONS_V1)
  @ResponseBody
  public ResponseEntity<?> getBloggerEligibleEventList(@RequestParam Integer memberId) {

    List<PREvent> events = queryApprovalPendingSubscriptionsByPrId.data(memberId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", memberId);

    return new ResponseEntity<List<PREvent>>(events, HttpStatus.OK);
  }
}
