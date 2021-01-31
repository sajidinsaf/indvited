package com.ef.eventservice.subscriber.worker;

import java.util.Arrays;
import java.util.List;

import com.ef.common.MapBasedContext;
import com.ef.common.message.MessagePacket;
import com.ef.common.work.Worker;

public class SimpleEmailAddressProvider implements Worker<MessagePacket<String>, List<String>, MapBasedContext> {
  private final String[] emailAddresses = new String[] { "sajidinsaf@gmail.com", "sajidinsaf@yahoo.com",
      "sajid@codeczar.co.uk" };

  @Override
  public List<String> perform(MessagePacket<String> job, MapBasedContext context) {
    return Arrays.asList(emailAddresses);
  }

}
