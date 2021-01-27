package com.ef.eventservice.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Publisher;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.dataaccess.Insert;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

public class PREventPublisher implements Publisher<PREventBindingModel> {

  private static final Logger logger = LoggerFactory.getLogger(PREventPublisher.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Jedis jedis;
  private final Insert<PREventBindingModel, PREvent> eventPersistor;

  public PREventPublisher(Jedis jedis,
      @Qualifier("insertPREvent") Insert<PREventBindingModel, PREvent> eventPersistor) {
    this.jedis = jedis;
    this.eventPersistor = eventPersistor;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Response<?> publishEvent(PREventBindingModel event, String channel) {

    List<String> validationResults = validate(event);

    if (validationResults != null && validationResults.size() > 0) {
      return new Response(validationResults, StatusCode.OK);
    }

    PREvent prEvent = eventPersistor.data(event);

    if (prEvent == null) {
      logUtil.warn(logger, event,
          " was not persisted and therefore will not be published. Please check log for further details");
      validationResults = new ArrayList<String>();
      validationResults.add("Event persistance failed");
      return new Response(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    jedis.publish(channel, new Gson().toJson(event));

    return new Response(prEvent, StatusCode.OK);
  }

  private List<String> validate(PREventBindingModel event) {
    return null;
  }

}