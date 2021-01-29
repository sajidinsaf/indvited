package com.ef.common.work;

import com.ef.common.Context;

public interface Worker<T, R, S extends Context> {

  public R perform(T job, S context);

}
