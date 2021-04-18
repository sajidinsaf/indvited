package com.ef.eventservice.web.subscription.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.common.MapBasedContext;
import com.ef.common.Strategy;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Insert;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREventScheduleSubscriptionWebFormBindingModel;

@Component("eventScheduleSubscriptionByWebStrategy")
public class EventScheduleSubscriptionByWebStrategy implements Strategy<MapBasedContext, Response<String>> {

  private final Insert<PREventScheduleSubscriptionWebFormBindingModel, EventScheduleSubscription> insertPREventScheduleSubscriptionWeb;

  public EventScheduleSubscriptionByWebStrategy(
      @Qualifier("insertPREventScheduleSubscriptionWeb") Insert<PREventScheduleSubscriptionWebFormBindingModel, EventScheduleSubscription> insertPREventScheduleSubscriptionWeb) {
    this.insertPREventScheduleSubscriptionWeb = insertPREventScheduleSubscriptionWeb;
  }

  @Override
  public Response<String> apply(MapBasedContext context) {

    try {

      PREventScheduleSubscriptionWebFormBindingModel input = context.get(EventServiceContext.WEB_SCUBSCRIPTION_MODEL);

      // @TODO check if event criteria match. If not return an appropriate response
      // message

      // persist the request
      EventScheduleSubscription result = insertPREventScheduleSubscriptionWeb.data(input);
      String response = "Your request has been successfully registered. Your request number is: " + result.getId()
          + ". Please quote this number in your communications. You will receive confirmation when your request has been approved.";
      return new Response<String>(response, StatusCode.OK);
    } catch (RuntimeException e) {
      return new Response<String>(
          "REquest submission failed. Please try after some time or contact support for more information.",
          StatusCode.OK);
    }

  }

}
