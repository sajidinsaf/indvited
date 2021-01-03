package com.ef.model.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

  private final T responseResult;
  private final StatusCode statusCode;
  private final List<String> failureReasons;

  public Response(T registrationObject, StatusCode statusCode) {
    this.statusCode = statusCode;
    this.responseResult = registrationObject;
    failureReasons = new ArrayList<String>();
  }

  public Response(List<String> failureReasons, StatusCode statusCode) {
    this.statusCode = statusCode;
    this.responseResult = null;
    this.failureReasons = failureReasons;
  }

  public StatusCode getStatusCode() {
    return statusCode;
  }

  public T getResponseResult() {
    return responseResult;
  }

  public List<String> getFailureReasons() {
    return failureReasons;
  }

}
