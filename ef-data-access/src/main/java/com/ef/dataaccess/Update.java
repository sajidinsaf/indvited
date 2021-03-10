package com.ef.dataaccess;

public interface Update<T, R> {

  String METHOD_INSERT = "insert";
  String METHOD_UPDATE = "update";

  public R data(T input);

}
