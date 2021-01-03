package com.ef.eventservice.scheduler;

import java.util.function.Predicate;

public class MessagePacket {

  private String message;
  private Predicate<String>[] conditions;

  public MessagePacket() {

  }

  public MessagePacket(String message, Predicate<String>[] conditions) {
    super();
    this.message = message;
    this.conditions = conditions;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Predicate<String>[] getConditions() {
    return conditions;
  }

  public void setConditions(Predicate<String>[] conditions) {
    this.conditions = conditions;
  }

}
