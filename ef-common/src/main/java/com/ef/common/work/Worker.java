package com.ef.common.work;

public interface Worker<T, R> {

  public R perform(T job);

}
