package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.CREATE_SCHEDULE;
import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_BINDING_MODEL;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ef.common.Strategy;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Response;
import com.ef.dataaccess.Insert;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREventScheduleBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class PREventScheduleController {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Insert<PREventScheduleBindingModel, EventScheduleResult> insertPrEventSchedule;

  private final Strategy<PREventPublisherContext, Response<?>> prEventScheduleNowStrategy;

  @Autowired
  public PREventScheduleController(
      @Qualifier("insertPrEventSchedule") Insert<PREventScheduleBindingModel, EventScheduleResult> insertPrEventSchedule,
      @Qualifier("prEventScheduleNowStrategy") Strategy<PREventPublisherContext, Response<?>> prEventScheduleNowStrategy) {
    this.insertPrEventSchedule = insertPrEventSchedule;
    this.prEventScheduleNowStrategy = prEventScheduleNowStrategy;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @PostMapping(CREATE_SCHEDULE)
  public ResponseEntity<?> createAllDayEventNow(@RequestBody PREventScheduleBindingModel eventSchedule,
      HttpServletRequest request) {
    try {
//      String xAuthToken = request.getHeader("X-Auth-Token");
//
//      if (xAuthToken == null) {
//        return ResponseEntity.badRequest().body("Unauthorised request. Valid login needed");
//      }
//
//      HttpSession session = request.getSession();
//      if (session == null || !session.getId().equals(xAuthToken)) {
//        return ResponseEntity.badRequest().body("Unauthorised request. Valid login needed");
//      }

      logUtil.debug(logger, "Creating event schedule: " + eventSchedule);

      EventScheduleResult prEventScheduleResult = insertPrEventSchedule.data(eventSchedule);

      PREventPublisherContext context = new PREventPublisherContext();
      context.put(PR_EVENT_BINDING_MODEL, prEventScheduleResult.getScheduleId());

      if (eventSchedule.getScheduleDate() == null) {
        Response<?> publishResponse = prEventScheduleNowStrategy.apply(context);

        if (publishResponse.getFailureReasons() != null && publishResponse.getFailureReasons().size() > 0) {
          return new ResponseEntity<List<String>>(publishResponse.getFailureReasons(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity(publishResponse.getResponseResult(), HttpStatus.OK);
      } else {
        return new ResponseEntity("Even scheduled with id: " + prEventScheduleResult.getScheduleId(), HttpStatus.OK);
      }
    } catch (RuntimeException e) {
      logUtil.exception(logger, e, "Input Data: ", eventSchedule);
      throw e;
    }

  }

}
