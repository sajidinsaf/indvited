package com.ef.common;

import com.ef.common.message.Response;

public interface EmailSender<T, R> {

  public Response<R> send(T message);
}
