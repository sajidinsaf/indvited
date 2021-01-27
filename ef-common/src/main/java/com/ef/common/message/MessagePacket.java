package com.ef.common.message;

import java.util.Arrays;
import java.util.function.Predicate;

public class MessagePacket<T> {

  private T payload;
  private Predicate<T>[] conditions;

  public MessagePacket() {

  }

  public MessagePacket(T payload, Predicate<T>... conditions) {
    super();
    this.payload = payload;
    this.conditions = conditions;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public Predicate<T>[] getConditions() {
    return conditions;
  }

  public void setConditions(Predicate<T>[] conditions) {
    this.conditions = conditions;
  }

  @Override
  public String toString() {
    return "MessagePacket [payload=" + payload + ", conditions=" + Arrays.toString(conditions) + "]";
  }

}
