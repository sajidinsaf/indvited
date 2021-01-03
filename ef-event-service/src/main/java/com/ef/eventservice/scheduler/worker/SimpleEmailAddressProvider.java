package com.ef.eventservice.scheduler.worker;

import java.util.Arrays;
import java.util.List;

import com.ef.eventservice.scheduler.MessagePacket;

public class SimpleEmailAddressProvider implements Worker<MessagePacket, List<String>> {
  private final String[] emailAddresses = new String[] { "sajidinsaf@gmail.com", "sajidinsaf@yahoo.com",
      "sajid@codeczar.co.uk" };

  @Override
  public List<String> perform(MessagePacket job) {
    return Arrays.asList(emailAddresses);
  }

}
