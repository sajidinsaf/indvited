package com.ef.eventservice.scheduler.worker;

public interface Worker<T, R> {

  public R perform(T job);

}
