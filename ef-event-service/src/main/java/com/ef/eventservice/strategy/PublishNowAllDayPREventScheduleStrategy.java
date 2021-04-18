package com.ef.eventservice.strategy;

import static com.ef.eventservice.controller.EventControllerConstants.PR_EVENT_SCHEDULE_BINDING_MODEL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ef.common.Strategy;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Publisher;
import com.ef.common.message.Response;
import com.ef.dataaccess.Insert;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventScheduleBindingModel;

public class PublishNowAllDayPREventScheduleStrategy implements Strategy<EventServiceContext, Response<?>> {

  private static final Logger logger = LoggerFactory.getLogger(PublishNowAllDayPREventScheduleStrategy.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Publisher<PREvent> prEventPublisher;

  private final String channel;

  private final Insert<PREventScheduleBindingModel, PREvent> insertPREventSchedule;

  public PublishNowAllDayPREventScheduleStrategy(Publisher<PREvent> prEventPublisher,
      @Qualifier("prEventChannelName") String eventChannelName,
      @Qualifier("insertPREventSchedule") Insert<PREventScheduleBindingModel, PREvent> insertPREventSchedule) {
    this.prEventPublisher = prEventPublisher;
    this.channel = eventChannelName;
    this.insertPREventSchedule = insertPREventSchedule;
  }

  @Override
  public Response<?> apply(EventServiceContext context) {
    try {

      PREventScheduleBindingModel prEventScheduleBindingModel = context.get(PR_EVENT_SCHEDULE_BINDING_MODEL);
      logUtil.debug(logger, "Publishing publish event: " + prEventScheduleBindingModel);

      PREvent prEvent = insertPREventSchedule.data(prEventScheduleBindingModel);

      Response<?> publishResponse = prEventPublisher.publishEvent(prEvent, context);

      return publishResponse;

    } catch (RuntimeException e) {
      logUtil.exception(logger, e, "Input Data: ", context);
      throw e;
    }

  }

}
