package com.ef.external.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initialiser extends AbstractHttpSessionApplicationInitializer {

  public Initialiser() {
    super(HttpSessionConfig.class);
  }

}
