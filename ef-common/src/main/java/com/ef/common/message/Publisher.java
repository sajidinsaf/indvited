package com.ef.common.message;

public interface Publisher<T> {

  public Response<?> publishEvent(T event, String channel);

}
