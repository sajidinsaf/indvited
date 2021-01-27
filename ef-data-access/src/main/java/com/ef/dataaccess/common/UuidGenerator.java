package com.ef.dataaccess.common;

import com.fasterxml.uuid.Generators;

public class UuidGenerator {

  public UuidGenerator() {

  }

  public String getUuid() {
    return Generators.timeBasedGenerator().generate().toString();
  }

}
