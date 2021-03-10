package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_SUBSCRIBER_ELIGIBLE_LIST_V1;
import static com.ef.eventservice.controller.EventControllerConstants.SUBMIT_DELIVERABLE;

import java.util.List;

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
import com.ef.dataaccess.Query;
import com.ef.eventservice.controller.util.PREventScheduleUtil;
import com.ef.model.event.PREvent;
import com.ef.model.event.SubscriberDeliverableSubmissionBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class SubscriberEventController {

  private static final Logger logger = LoggerFactory.getLogger(SubscriberEventController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<Integer, List<PREvent>> queryEligibleSchedulesByBloggerProfile;
  private final PREventScheduleUtil prEventScheduleUtil;

  @Autowired
  public SubscriberEventController(
      @Qualifier("queryEligibleSchedulesByBloggerProfile") Query<Integer, List<PREvent>> queryEligibleSchedulesByBloggerProfile,
      PREventScheduleUtil prEventScheduleUtil) {
    this.queryEligibleSchedulesByBloggerProfile = queryEligibleSchedulesByBloggerProfile;
    this.prEventScheduleUtil = prEventScheduleUtil;
  }

  @GetMapping(GET_SUBSCRIBER_ELIGIBLE_LIST_V1)
  @ResponseBody
  public ResponseEntity<?> getBloggerEligibleEventList(@RequestParam Integer memberId) {

    List<PREvent> events = queryEligibleSchedulesByBloggerProfile.data(memberId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", memberId);

    List<PREvent> enrichedEvents = prEventScheduleUtil.populateAvailableDates(events);

    return new ResponseEntity<List<PREvent>>(enrichedEvents, HttpStatus.OK);
  }

  @PostMapping(SUBMIT_DELIVERABLE)
  @ResponseBody
  public ResponseEntity<?> addEventScheduleSubscriptions(
      @RequestBody SubscriberDeliverableSubmissionBindingModel model) {

    logUtil.debug(logger, "Received deliverable submission data ", model);

    return new ResponseEntity<SubscriberDeliverableSubmissionBindingModel>(model, HttpStatus.OK);
  }

}
