package com.ef.common.logging;

import org.slf4j.Logger;

public class ServiceLoggingUtil {
  public void info(Logger logger, Object... info) {
    if (logger.isInfoEnabled()) {
      StringBuilder sb = new StringBuilder();
      for (Object s : info) {
        sb.append(s).append(" ");
      }
      logger.info(sb.toString());
    }
  }

  public void debug(Logger logger, Object... info) {
    if (logger.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      for (Object s : info) {
        sb.append(s).append(" ");
      }
      logger.debug(sb.toString());
    }
  }

  public void warn(Logger logger, Object... info) {
    StringBuilder sb = new StringBuilder();
    for (Object s : info) {
      sb.append(s).append(" ");
    }
    logger.warn(sb.toString());
  }

  public void exception(Logger logger, Exception e, Object... info) {
    StringBuilder sb = new StringBuilder();
    for (Object s : info) {
      sb.append(s).append(" ");
    }
    logger.error(sb.toString(), e);
  }

  public void logStackTrace(Logger logger, StackTraceElement[] stackTrace) {
    if (logger.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder(Thread.currentThread().getName());
      for (StackTraceElement e : stackTrace) {
        sb.append(e.toString()).append(System.getProperty("line.separator"));
      }
      logger.debug(sb.toString());
    }

  }
}
