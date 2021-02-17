package com.ef.eventservice.scheduler;

import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_SCHEDULE_PERSIST_RESULT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.common.Strategy;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Query;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.EventScheduleResult;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

@Component("prEventScheduleStrategy")
public class PREventSchedulingStrategy implements Strategy<PREventPublisherContext, Response<PREvent>> {

  private final Query<Integer, PREvent> queryPREventById;
  private final Query<Integer, List<PREventSchedule>> queryEventScheduleList;

  @Autowired
  public PREventSchedulingStrategy(@Qualifier("queryEventById") Query<Integer, PREvent> queryPREventById,
      @Qualifier("queryPREventScheduleListByEventId") Query<Integer, List<PREventSchedule>> queryEventScheduleList) {
    this.queryPREventById = queryPREventById;
    this.queryEventScheduleList = queryEventScheduleList;
  }

  @Override
  public Response<PREvent> apply(PREventPublisherContext context) {

    EventScheduleResult prEventScheduleResult = context.get(PR_EVENT_SCHEDULE_PERSIST_RESULT);

    int eventId = prEventScheduleResult.getSchedule().getEventId();

    PREvent prEvent = queryPREventById.data(eventId);

    List<PREventSchedule> schedules = queryEventScheduleList.data(eventId);

    prEvent.setSchedules(schedules);
    return new Response<PREvent>(prEvent, StatusCode.OK);
  }

}
