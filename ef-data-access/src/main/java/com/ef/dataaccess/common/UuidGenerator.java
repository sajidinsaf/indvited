package com.ef.dataaccess.common;

import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;

@Component("uuidGenerator")
public class UuidGenerator {

  public UuidGenerator() {

  }

  public String getUuid() {
    return Generators.timeBasedGenerator().generate().toString();
  }

}
