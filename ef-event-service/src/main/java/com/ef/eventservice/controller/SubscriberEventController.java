package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_SUBSCRIBER_ELIGIBLE_LIST_V1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.eventservice.controller.util.PREventScheduleUtil;
import com.ef.model.event.PREvent;

/**
 * Handles requests for the event service.
 */
@Controller
public class SubscriberEventController {

  private static final Logger logger = LoggerFactory.getLogger(SubscriberEventController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<Integer, List<PREvent>> queryEligibleSchedulesByBloggerProfile;

  @Autowired
  public SubscriberEventController(
      @Qualifier("queryEligibleSchedulesByBloggerProfile") Query<Integer, List<PREvent>> queryEligibleSchedulesByBloggerProfile,
      PREventScheduleUtil prEventScheduleUtil) {
    this.queryEligibleSchedulesByBloggerProfile = queryEligibleSchedulesByBloggerProfile;
  }

  @GetMapping(GET_SUBSCRIBER_ELIGIBLE_LIST_V1)
  @ResponseBody
  public ResponseEntity<?> getBloggerEligibleEventList(@RequestParam Integer memberId) {

    List<PREvent> events = queryEligibleSchedulesByBloggerProfile.data(memberId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", memberId);

    return new ResponseEntity<List<PREvent>>(events, HttpStatus.OK);
  }

}
