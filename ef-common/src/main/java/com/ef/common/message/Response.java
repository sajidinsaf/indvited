package com.ef.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response<T> implements Serializable {

  private static final long serialVersionUID = -5819480515422423254L;
  private T responseResult;
  private StatusCode statusCode;
  private List<String> failureReasons;

  public Response(StatusCode statusCode) {
    this.statusCode = statusCode;
    this.responseResult = null;
    failureReasons = new ArrayList<String>();
  }

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

  public void setResponseResult(T responseResult) {
    this.responseResult = responseResult;
  }

  public void setStatusCode(StatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public void setFailureReasons(List<String> failureReasons) {
    this.failureReasons = failureReasons;
  }

  @Override
  public String toString() {
    return "Response [responseResult=" + responseResult + ", statusCode=" + statusCode + ", failureReasons="
        + failureReasons + "]";
  }

}
