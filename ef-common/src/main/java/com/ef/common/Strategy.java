package com.ef.common;

public interface Strategy<T extends Context, R> {

  public R apply(T context);

}
