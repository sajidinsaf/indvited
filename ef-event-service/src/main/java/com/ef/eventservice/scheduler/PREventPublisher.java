package com.ef.eventservice.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ef.dataaccess.Insert;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

public class PREventPublisher implements Publisher<PREventBindingModel> {

  private final Jedis jedis;
  private final Insert<PREventBindingModel, PREvent> eventPersistor;

  public PREventPublisher(Jedis jedis,
      @Qualifier("insertPREvent") Insert<PREventBindingModel, PREvent> eventPersistor) {
    this.jedis = jedis;
    this.eventPersistor = eventPersistor;
  }

  public Response<?> publishEvent(PREventBindingModel event, String channel) {

    List<String> validationResults = validate(event);

    if (validationResults != null && validationResults.size() > 0) {
      return new Response(validationResults, StatusCode.OK);
    }
    PREvent prEvent = eventPersistor.data(event);

    jedis.publish(channel, new Gson().toJson(event));

    return new Response(prEvent, StatusCode.OK);
  }

  private List<String> validate(PREventBindingModel event) {
    // TODO Auto-generated method stub
    return null;
  }

}