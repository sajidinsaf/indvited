package com.ef.eventservice.scheduler;

import com.ef.model.response.Response;

public interface Publisher<T> {

  public Response<?> publishEvent(T event, String channel);

}
