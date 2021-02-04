package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_PR_EVENT_LIST;

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
import com.ef.model.event.PREvent;

/**
 * Handles requests for the event service.
 */
@Controller
public class EventController {

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<Integer, List<PREvent>> prEventListQuery;

  @Autowired
  public EventController(@Qualifier("queryPREventList") Query<Integer, List<PREvent>> prEventListQuery) {
    this.prEventListQuery = prEventListQuery;
  }

  @GetMapping(GET_PR_EVENT_LIST)
  @ResponseBody
  public ResponseEntity<?> getPrEvenntList(@RequestParam Integer id) {

    List<PREvent> events = prEventListQuery.data(id);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", id);

    return new ResponseEntity<List<PREvent>>(events, HttpStatus.OK);
  }
}
