package com.ef.messaging.redis;

import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.MapBasedContext;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.work.Worker;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

  private final List<Worker<MessagePacket<String>, Response<String>, MapBasedContext>> workers;
  private final Predicate<String>[] predicates;

  public Subscriber(List<Worker<MessagePacket<String>, Response<String>, MapBasedContext>> workers,
      Predicate<String>... predicates) {
    this.workers = workers;
    this.predicates = predicates;
  }

  private static Logger logger = LoggerFactory.getLogger(Subscriber.class);

  @Override
  public void onMessage(String channel, String message) {

    logger.info("Message received. Channel: {}, Msg: {}", channel, message);

    MessagePacket<String> messagePacket = new MessagePacket<String>(message, predicates);

    for (Worker<MessagePacket<String>, Response<String>, MapBasedContext> worker : workers) {
      worker.perform(messagePacket, new MapBasedContext());
    }
  }

  @Override
  public void onPMessage(String pattern, String channel, String message) {

  }

  @Override
  public void onSubscribe(String channel, int subscribedChannels) {

  }

  @Override
  public void onUnsubscribe(String channel, int subscribedChannels) {

  }

  @Override
  public void onPUnsubscribe(String pattern, int subscribedChannels) {

  }

  @Override
  public void onPSubscribe(String pattern, int subscribedChannels) {

  }
}
