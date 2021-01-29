package com.ef.common;

import java.util.HashMap;
import java.util.Map;

public class MapBasedContext implements Context {

  private Map<String, Object> objMap;

  public MapBasedContext() {
    objMap = new HashMap<String, Object>();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(String key) {
    Object obj = objMap.get(key);
    if (obj == null) {
      return null;
    }

    return (T) objMap.get(key);
  }

  public Object put(String key, Object obj) {
    return objMap.put(key, obj);
  }

}
