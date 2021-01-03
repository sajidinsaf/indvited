package com.ef.common;

public class FieldCannotBeMutatedException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 2259927472014635316L;

  public FieldCannotBeMutatedException(String fieldName) {
    super(fieldName + " cannot be mutated");
  }

}
