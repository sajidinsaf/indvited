package com.ef.eventservice.scheduler;

import org.springframework.stereotype.Component;

import com.ef.common.Strategy;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleResult;

@Component("prEventScheduleNowStrategy")
public class PREventSchedulingStrategy implements Strategy<PREventPublisherContext, Response<EventScheduleResult>> {

  public PREventSchedulingStrategy() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Response<EventScheduleResult> apply(PREventPublisherContext context) {

    return new Response<EventScheduleResult>(new EventScheduleResult(-1L, new long[] {}), StatusCode.OK);
  }

}
