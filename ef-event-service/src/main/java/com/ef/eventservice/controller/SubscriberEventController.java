package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_PR_SUBSCRIBER_ELIGIBLE_LIST;

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

  private final Query<Integer, List<PREvent>> prEventListQuery;

  @Autowired
  public SubscriberEventController(@Qualifier("queryPREventList") Query<Integer, List<PREvent>> prEventListQuery,
      PREventScheduleUtil prEventScheduleUtil) {
    this.prEventListQuery = prEventListQuery;
  }

  @GetMapping(GET_PR_SUBSCRIBER_ELIGIBLE_LIST)
  @ResponseBody
  public ResponseEntity<?> getBloggerEligibleEventList(@RequestParam Integer bloggerId) {

    List<PREvent> events = prEventListQuery.data(bloggerId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", bloggerId);

    return new ResponseEntity<List<PREvent>>(events, HttpStatus.OK);
  }

}
