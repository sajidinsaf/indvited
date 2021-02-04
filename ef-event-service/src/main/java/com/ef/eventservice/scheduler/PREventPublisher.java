package com.ef.eventservice.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ef.common.Context;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Channel;
import com.ef.common.message.Publisher;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Insert;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.google.gson.Gson;

public class PREventPublisher implements Publisher<PREventBindingModel> {

  private static final Logger logger = LoggerFactory.getLogger(PREventPublisher.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Channel channel;
  // private final Jedis jedis;
  private final Insert<PREventBindingModel, PREvent> eventPersistor;
  private final List<Validator<PREventBindingModel, String>> validators;

  public PREventPublisher(Channel channel,
      @Qualifier("insertPREvent") Insert<PREventBindingModel, PREvent> eventPersistor,
      List<Validator<PREventBindingModel, String>> validators) {
    this.channel = channel;
    this.eventPersistor = eventPersistor;
    this.validators = validators;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Response<?> publishEvent(PREventBindingModel event, Context context) {

    List<String> validationResults = validate(event);

    if (validationResults != null && validationResults.size() > 0) {
      return new Response(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    PREvent prEvent = eventPersistor.data(event);

    if (prEvent == null) {
      logUtil.warn(logger, event,
          " was not persisted and therefore will not be published. Please check log for further details");
      validationResults = new ArrayList<String>();
      validationResults.add("Event persistance failed");
      return new Response(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    channel.publish(new Gson().toJson(event), context);

    return new Response(prEvent, StatusCode.OK);
  }

  private List<String> validate(PREventBindingModel event) {
    List<String> validationResults = new ArrayList<String>();
    for (Validator<PREventBindingModel, String> validator : validators) {
      String validationResult = validator.validate(event);
      if (validationResult != null) {
        validationResults.add(validationResult);
      }
    }
    return validationResults;
  }

}