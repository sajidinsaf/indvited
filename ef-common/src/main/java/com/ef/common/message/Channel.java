package com.ef.common.message;

import com.ef.common.Context;

public interface Channel {

  public Long publish(String message, Context context);

  public String getName();

}
