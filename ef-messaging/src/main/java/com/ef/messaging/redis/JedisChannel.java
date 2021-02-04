package com.ef.messaging.redis;

import com.ef.common.Context;
import com.ef.common.message.Channel;

import redis.clients.jedis.Jedis;

public class JedisChannel implements Channel {

  private final Jedis jedis;
  private final String channelName;

  public JedisChannel(String channelName, Jedis jedis) {
    this.channelName = channelName;
    this.jedis = jedis;

  }

  @Override
  public Long publish(String message, Context context) {

    return jedis.publish(channelName, message);
  }

  @Override
  public String getName() {

    return channelName;
  }

}
