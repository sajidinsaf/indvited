package com.ef.eventservice.controller;

import static com.ef.eventservice.controller.EventControllerConstants.PUBLISH_EVENT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.eventservice.scheduler.Publisher;
import com.ef.model.event.PREventBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class EventController {

  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  private final Publisher<PREventBindingModel> prEventPublisher;

  private final String channel;

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  @Autowired
  public EventController(@Qualifier("prEventPublisher") Publisher<PREventBindingModel> prEventPublisher,
      @Qualifier("prEventChannelName") String eventChannelName) {
    this.channel = eventChannelName;
    this.prEventPublisher = prEventPublisher;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"eventCreatorId":"23324345","domainName":"Restaurant","eventType":"Restaurant Review Event","prEventTimeSlotBindingModel":[{"eventDate":"15/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"15/01/2021","timeFrom":"1800","timeTo":"2000"},{"eventDate":"16/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"16/01/2021","timeFrom":"1800","timeTo":"2000"},{"eventDate":"17/01/2021","timeFrom":"1200","timeTo":"1600"},{"eventDate":"17/01/2021","timeFrom":"1800","timeTo":"2000"}],"cap":"1 cocktail each / 2 starters / 2 mains","exclusions":"*no red meat , no fish* IN case u want to order a *dessert or any dish apart of the above cap it would be *PAYABLE*","eventCriteria":[{"criterionName":"Mininum Zomato reviews","minValue":175},{"criterionName":"Minimum Instagram followers","minValue":9000}],"eventLocation":{"venueName":"Esora","venueAddress":"1st Floor, Commerz 2, International Business Park, Oberoi Garden City, Near Oberoi Mall, Goregaon East, Mumbai","zomatoUrl":"http://zoma.to/r/18789802"},"notes":"Find this restaurant on Zomato | Bhukkad, Shop 13, Mohid Heights, R. T. O. Road, Andheri Lokhandwala, Andheri West, Mumbai  http://zoma.to/r/19213346 (You have to place an order for 1 dish from menu lowest item need to be ordered  amount for which will not be refunded). *Timings 12pm till 3pm \u0026 7pm till 9pm Available From Monday Till Sunday Only on Friday \u0026 Saturday  Available From 12pm till 3pm*"}' "http://secure.codeczar.co.uk/ef-event-service/rest/event/publishPREvent"
  @PostMapping(PUBLISH_EVENT)
  public ResponseEntity<?> publishPREvent(@RequestBody PREventBindingModel event, HttpServletRequest request) {
    String xAuthToken = request.getHeader("X-Auth-Token");

    if (xAuthToken == null) {
      return ResponseEntity.badRequest().body("Unauthorised request. Valid login needed");
    }

    HttpSession session = request.getSession();
    if (session == null || !session.getId().equals(xAuthToken)) {
      return ResponseEntity.badRequest().body("Unauthorised request. Valid login needed");
    }

    logUtil.debug(logger, "Publishing publish event: " + event);

    prEventPublisher.publishEvent(event, channel);
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
