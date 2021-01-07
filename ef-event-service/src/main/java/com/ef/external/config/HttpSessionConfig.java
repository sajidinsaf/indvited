package com.ef.external.config;

import javax.servlet.ServletContext;
import javax.servlet.SessionCookieConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.ef.common.logging.ServiceLoggingUtil;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

  private static final Logger logger = LoggerFactory.getLogger(HttpSessionConfig.class);

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {

    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("127.0.0.1", 41029);
    config.setPassword("6w1KiI5TVRHQcPTlvctgs2zgw1y2Lwro");
    return new JedisConnectionFactory(config);
  }

  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

  /*
   * 2021-01-04 16:47:03 WARN RedisHttpSessionConfiguration:166 - Unable to obtain
   * SessionCookieConfig: Section 4.4 of the Servlet 3.0 specification does not
   * permit this method to be called from a ServletContextListener that was not
   * defined in web.xml, a web-fragment.xml file nor annotated with @WebListener
   * 
   * https://github.com/spring-projects/spring-framework/issues/22319
   */

  @Bean
  public CookieSerializer cookieSerializer(ServletContext ctx) {
    logUtil.info(logger, "Creating cookie serializer");
    DefaultCookieSerializer cs = new DefaultCookieSerializer();

    try {
      SessionCookieConfig cfg = ctx.getSessionCookieConfig();
      cs.setCookieName(cfg.getName());
      cs.setDomainName(cfg.getDomain());
      cs.setCookiePath(cfg.getPath());
      cs.setCookieMaxAge(cfg.getMaxAge());
    } catch (UnsupportedOperationException e) {
      cs.setCookieName("MY_SESSIONID");
      cs.setCookiePath(ctx.getContextPath());
    }

    return cs;
  }
}
