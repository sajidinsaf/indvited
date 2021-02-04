package com.ef.common.message;

import com.ef.common.Context;

public interface Publisher<T> {

  public Response<?> publishEvent(T event, Context context);

}
