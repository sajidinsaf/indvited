package com.ef.dataaccess;

public interface Query<T, R> {

  public R data(T params);
}
