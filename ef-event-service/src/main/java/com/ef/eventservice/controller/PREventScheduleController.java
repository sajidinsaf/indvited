package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.CREATE_SCHEDULE;
import static com.ef.eventservice.controller.EventControllerConstants.GET_PR_EVENT_SCHEDULE_LIST;
import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_SCHEDULE_BINDING_MODEL;
import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_SCHEDULE_PERSIST_RESULT;

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

import com.ef.common.Strategy;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Response;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.PREventScheduleBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class PREventScheduleController {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Insert<PREventScheduleBindingModel, EventScheduleResult> insertPrEventSchedule;

  private final Strategy<EventServiceContext, Response<?>> prEventScheduleStrategy;

  private final Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId;

  @Autowired
  public PREventScheduleController(
      @Qualifier("insertPrEventSchedule") Insert<PREventScheduleBindingModel, EventScheduleResult> insertPrEventSchedule,
      @Qualifier("prEventScheduleStrategy") Strategy<EventServiceContext, Response<?>> prEventScheduleStrategy,
      @Qualifier("queryPREventScheduleListByEventId") Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId) {
    this.insertPrEventSchedule = insertPrEventSchedule;
    this.prEventScheduleStrategy = prEventScheduleStrategy;
    this.queryPREventScheduleListByEventId = queryPREventScheduleListByEventId;
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

      EventServiceContext context = new EventServiceContext();
      context.put(PR_EVENT_SCHEDULE_BINDING_MODEL, eventSchedule);
      context.put(PR_EVENT_SCHEDULE_PERSIST_RESULT, prEventScheduleResult);

      if (eventSchedule.getScheduleOnDate() == null) {
        Response<?> publishResponse = prEventScheduleStrategy.apply(context);

        if (publishResponse.getFailureReasons() != null && publishResponse.getFailureReasons().size() > 0) {
          return new ResponseEntity<List<String>>(publishResponse.getFailureReasons(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity(publishResponse.getResponseResult(), HttpStatus.OK);
      } else {
        return new ResponseEntity("Even scheduled: " + prEventScheduleResult.getSchedule(), HttpStatus.OK);
      }
    } catch (RuntimeException e) {
      logUtil.exception(logger, e, "Input Data: ", eventSchedule);
      throw e;
    }

  }

  @GetMapping(GET_PR_EVENT_SCHEDULE_LIST)
  @ResponseBody
  public ResponseEntity<?> getPrEventScheduleList(@RequestParam Integer eventId) {

    List<PREventSchedule> events = queryPREventScheduleListByEventId.data(eventId);
    logUtil.debug(logger, "Returning ", events.size(), " events for member id ", eventId);

    return new ResponseEntity<List<PREventSchedule>>(events, HttpStatus.OK);
  }
}
