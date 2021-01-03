package com.ef.eventservice.scheduler;

import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.eventservice.scheduler.worker.Worker;
import com.ef.model.response.Response;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

  private final List<Worker<MessagePacket, Response<String>>> workers;
  private final Predicate<String>[] predicates;

  private int i = 0;

  public Subscriber(List<Worker<MessagePacket, Response<String>>> workers, Predicate<String>... predicates) {
    this.workers = workers;
    this.predicates = predicates;
  }

  private static Logger logger = LoggerFactory.getLogger(Subscriber.class);

  @Override
  public void onMessage(String channel, String message) {

    logger.info("Message received. Channel: {}, Msg: {}", channel, message);

    MessagePacket messagePacket = new MessagePacket(message, predicates);

    for (Worker<MessagePacket, Response<String>> worker : workers) {
      worker.perform(messagePacket);
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
