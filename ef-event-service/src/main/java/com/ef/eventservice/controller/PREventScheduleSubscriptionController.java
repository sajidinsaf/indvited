package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_AWAITING_APPROVAL_SUBSCRIPTIONS_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIBE;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIPTIONS_APPROVE_DELIVERABLE_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIPTIONS_APPROVE_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIPTIONS_REJECT_DELIVERABLE_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBSCRIPTIONS_REJECT_V1;
import static com.ef.eventservice.controller.EventControllerConstants.UPDATE_SUBSCRIPTION_STATUS_ACTION;
import static com.ef.eventservice.controller.EventControllerConstants.UPDATE_SUBSCRIPTION_STATUS_BINDING_MODEL;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ef.common.MapBasedContext;
import com.ef.common.Strategy;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Response;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.eventservice.controller.util.PREventScheduleUtil;
import com.ef.eventservice.controller.util.PREventWebSubscriptionsUtil;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventScheduleSubscriptionBindingModel;
import com.ef.model.event.PREventScheduleSubscriptionBindingModelWorkaround;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class PREventScheduleSubscriptionController {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleSubscriptionController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription;
  private final Query<Integer, List<PREvent>> queryApprovalPendingSubscriptionsByPrId;
  private final PREventScheduleUtil prEventScheduleUtil;

  private final Strategy<MapBasedContext, ResponseEntity<?>> subscriptionStatusUpdateStrategy;

  private final Update<SubscriberDeliverableSubmissionBindingModel, String> closeSubscriptionOnDeliverableApproval;

  private final Update<SubscriberDeliverableSubmissionBindingModel, String> insertDeliverableRejectionAndUpdateSubscription;

  private final Query<Integer, PREvent> queryEnrichedEventWithAvailableSchedulesByEventId;

  private final Strategy<MapBasedContext, Response<String>> eventScheduleSubscriptionByWebStrategy;

  private final PREventWebSubscriptionsUtil prEventWebSubscriptionsUtil;

  @Autowired
  public PREventScheduleSubscriptionController(
      @Qualifier("insertPrEventScheduleSubscription") Insert<PREventScheduleSubscriptionBindingModel, EventScheduleSubscription> insertPrEventScheduleSubscription,
      @Qualifier("queryApprovalPendingSubscriptionsByPrId") Query<Integer, List<PREvent>> queryApprovalPendingSubscriptionsByPrId,
      PREventScheduleUtil prEventScheduleUtil, PREventWebSubscriptionsUtil prEventWebSubscriptionsUtil,
      @Qualifier("subscriptionStatusUpdateStrategy") Strategy<MapBasedContext, ResponseEntity<?>> subscriptionStatusUpdateStrategy,
      @Qualifier("closeSubscriptionOnDeliverableApproval") Update<SubscriberDeliverableSubmissionBindingModel, String> closeSubscriptionOnDeliverableApproval,
      @Qualifier("insertDeliverableRejectionAndUpdateSubscription") Update<SubscriberDeliverableSubmissionBindingModel, String> insertDeliverableRejectionAndUpdateSubscription,
      @Qualifier("queryEnrichedEventWithAvailableSchedulesByEventId") Query<Integer, PREvent> queryEventWithAvailableSchedulesByEventId,
      @Qualifier("eventScheduleSubscriptionByWebStrategy") Strategy<MapBasedContext, Response<String>> eventScheduleSubscriptionByWebStrategy) {
    this.insertPrEventScheduleSubscription = insertPrEventScheduleSubscription;
    this.queryApprovalPendingSubscriptionsByPrId = queryApprovalPendingSubscriptionsByPrId;
    this.prEventScheduleUtil = prEventScheduleUtil;
    this.prEventWebSubscriptionsUtil = prEventWebSubscriptionsUtil;
    this.subscriptionStatusUpdateStrategy = subscriptionStatusUpdateStrategy;
    this.closeSubscriptionOnDeliverableApproval = closeSubscriptionOnDeliverableApproval;
    this.insertDeliverableRejectionAndUpdateSubscription = insertDeliverableRejectionAndUpdateSubscription;
    this.queryEnrichedEventWithAvailableSchedulesByEventId = queryEventWithAvailableSchedulesByEventId;
    this.eventScheduleSubscriptionByWebStrategy = eventScheduleSubscriptionByWebStrategy;
  }

  @PostMapping(SUBSCRIBE + "Schedule")
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

    events = prEventWebSubscriptionsUtil.populateWebEvents(events, memberId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", memberId);

    List<PREvent> enrichedEvents = prEventScheduleUtil.populateAvailableDates(events);
    return new ResponseEntity<List<PREvent>>(enrichedEvents, HttpStatus.OK);
  }

  @PostMapping(SUBSCRIPTIONS_APPROVE_V1)
  @ResponseBody
  public ResponseEntity<?> approveSubscription(@RequestBody PREventScheduleSubscriptionStatusChangeBindingModel model,
      HttpServletRequest request) {

    MapBasedContext context = new MapBasedContext();
    context.put(UPDATE_SUBSCRIPTION_STATUS_ACTION, EventStatusMeta.KNOWN_STATUS_ID_APPROVED);
    context.put(UPDATE_SUBSCRIPTION_STATUS_BINDING_MODEL, model);
    return updatesubscriptionStatus(model, request, context);
  }

  @PostMapping(SUBSCRIPTIONS_REJECT_V1)
  @ResponseBody
  public ResponseEntity<?> rejectsubscription(@RequestBody PREventScheduleSubscriptionStatusChangeBindingModel model,
      HttpServletRequest request) {
    MapBasedContext context = new MapBasedContext();
    context.put(UPDATE_SUBSCRIPTION_STATUS_ACTION, EventStatusMeta.KNOWN_STATUS_ID_REJECTED);
    context.put(UPDATE_SUBSCRIPTION_STATUS_BINDING_MODEL, model);

    return updatesubscriptionStatus(model, request, context);
  }

  public ResponseEntity<?> updatesubscriptionStatus(PREventScheduleSubscriptionStatusChangeBindingModel model,
      HttpServletRequest request, MapBasedContext context) {

    logUtil.debug(logger, "Received subscription data ", model);

    return subscriptionStatusUpdateStrategy.apply(context);
  }

  @PostMapping(SUBSCRIPTIONS_APPROVE_DELIVERABLE_V1)
  @ResponseBody
  public ResponseEntity<?> approveDeliverableAndClose(@RequestBody SubscriberDeliverableSubmissionBindingModel input) {

    logUtil.debug(logger, "Received deliverable approval ", input);

    String result = closeSubscriptionOnDeliverableApproval.data(input);

    logUtil.debug(logger, "Status update result: ", result, input);
    return new ResponseEntity<SubscriberDeliverableSubmissionBindingModel>(input, HttpStatus.OK);

  }

  @PostMapping(SUBSCRIPTIONS_REJECT_DELIVERABLE_V1)
  @ResponseBody
  public ResponseEntity<?> rejectDeliverableAndClose(@RequestBody SubscriberDeliverableSubmissionBindingModel input) {

    logUtil.debug(logger, "Received deliverable rejection ", input);

    String result = insertDeliverableRejectionAndUpdateSubscription.data(input);

    logUtil.debug(logger, "Status update result: ", result, input);
    return new ResponseEntity<SubscriberDeliverableSubmissionBindingModel>(input, HttpStatus.OK);

  }

  @GetMapping(SUBSCRIBE + "/web/event/{eventId}")
  public ModelAndView confirmMember(@PathVariable Integer eventId) {
    logUtil.debug(logger, "Confirmation Code: " + eventId);

    PREvent event = queryEnrichedEventWithAvailableSchedulesByEventId.data(eventId);

    logUtil.info(logger, "Got event for ID: ", eventId, " [", event, "]");

    logUtil.info(logger, "Calculating available dates for event");

    List<PREvent> eventList = prEventScheduleUtil.populateAvailableDates(Arrays.asList(event));

    logUtil.info(logger, "Event with available scheduled date: ", (eventList.size() > 0 ? eventList.get(0) : null));
    event = eventList.get(0).getAllAvailableScheduledDatesForDisplay().size() > 0 ? eventList.get(0) : null;

    ModelAndView model = new ModelAndView();
    model.setViewName("applyForEvent");
    model.addObject("event", event);

    return model;

  }

  @PostMapping(SUBSCRIBE + "ScheduleWebForm")
  @ResponseBody
  public ResponseEntity<?> scheduleWebForm(@RequestBody PREventScheduleSubscriptionWebFormBindingModel formData,
      HttpServletRequest request) {

    formData.setStatusId(EventStatusMeta.KNOWN_STATUS_ID_APPLIED);
    logUtil.debug(logger, "Received subscription data ", formData);

    EventServiceContext context = new EventServiceContext();
    context.put(EventServiceContext.WEB_SCUBSCRIPTION_MODEL, formData);
    eventScheduleSubscriptionByWebStrategy.apply(context);

    return new ResponseEntity<String>("success", HttpStatus.OK);
  }
}
