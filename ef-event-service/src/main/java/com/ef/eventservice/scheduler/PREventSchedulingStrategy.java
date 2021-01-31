package com.ef.eventservice.scheduler;

import com.ef.common.Strategy;
import com.ef.common.message.Response;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleResult;

public class PREventSchedulingStrategy implements Strategy<PREventPublisherContext, Response<EventScheduleResult>> {

  public PREventSchedulingStrategy() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Response<EventScheduleResult> apply(PREventPublisherContext context) {
    // TODO Auto-generated method stub
    return null;
  }

}
