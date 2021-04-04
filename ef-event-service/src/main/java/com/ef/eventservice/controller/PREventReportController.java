package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.GET_REVIEW_SCHEDULE_FOR_DATES;

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
import org.springframework.web.bind.annotation.RequestBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.model.event.report.EventScheduleSubscribers;
import com.ef.model.event.report.EventScheduleSubscribersQueryParameters;

/**
 * Handles requests for the event service.
 */
@Controller
public class PREventReportController {

  private static final Logger logger = LoggerFactory.getLogger(PREventReportController.class);

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<EventScheduleSubscribersQueryParameters, List<EventScheduleSubscribers>> queryEventScheduleSubsribersListByDate;

  @Autowired
  public PREventReportController(
      @Qualifier("queryEventScheduleSubsribersListByDate") Query<EventScheduleSubscribersQueryParameters, List<EventScheduleSubscribers>> queryEventScheduleSubsribersListByDate) {
    this.queryEventScheduleSubsribersListByDate = queryEventScheduleSubsribersListByDate;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"eventCreatorId":"23324345","domainName":"Restaurant","eventType":"Restaurant Review Event","prEventTimeSlotBindingModel":[{"eventDate":"15/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"15/01/2021","timeFrom":"1800","timeTo":"2000"},{"eventDate":"16/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"16/01/2021","timeFrom":"1800","timeTo":"2000"},{"eventDate":"17/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"17/01/2021","timeFrom":"1800","timeTo":"2000"}],"cap":"1 cocktail each / 2 starters / 2 mains","exclusions":"*no red meat , no fish* IN case u want to order a *dessert or any dish apart of the above cap it would be *PAYABLE*","eventCriteria":[{"criterionName":"Mininum Zomato reviews","minValue":175},{"criterionName":"Minimum Instagram followers","minValue":9000}],"eventLocation":{"venueName":"Esora","venueAddress":"1st Floor, Commerz 2, International Business Park, Oberoi Garden City, Near Oberoi Mall, Goregaon East, Mumbai","zomatoUrl":"http://zoma.to/r/18789802"},"notes":"Find this restaurant on Zomato | Bhukkad, Shop 13, Mohid Heights, R. T. O. Road, Andheri Lokhandwala, Andheri West, Mumbai  http://zoma.to/r/19213346 (You have to place an order for 1 dish from menu lowest item need to be ordered  amount for which will not be refunded). *Timings 12pm till 3pm \u0026 7pm till 9pm Available From Monday Till Sunday Only on Friday \u0026 Saturday  Available From 12pm till 3pm*"}' "http://secure.codeczar.co.uk/ef-event-service/api/v1/event/publishPREvent"
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @GetMapping(GET_REVIEW_SCHEDULE_FOR_DATES)
  public ResponseEntity<?> publishPREvent(@RequestBody EventScheduleSubscribersQueryParameters params,
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

      logUtil.debug(logger, "Got request params: " + params);

      List<EventScheduleSubscribers> result = queryEventScheduleSubsribersListByDate.data(params);

      QueryResult<List<EventScheduleSubscribers>> queryResult = new QueryResult<List<EventScheduleSubscribers>>(result);

      return new ResponseEntity(queryResult, HttpStatus.OK);
    } catch (RuntimeException e) {
      logUtil.exception(logger, e, "Input Data: ", params);
      throw e;
    }
  }

  class QueryResult<R> {
    private R data;

    QueryResult(R data) {
      this.data = data;
    }

    public R getData() {
      return data;
    }
  }

}
