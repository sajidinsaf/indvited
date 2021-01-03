package com.ef.common.validation;

public interface Validator<T, R> {

  public R validate(T data);
}
