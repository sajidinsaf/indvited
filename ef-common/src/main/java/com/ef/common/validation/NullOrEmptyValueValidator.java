package com.ef.common.validation;

import org.apache.commons.lang3.tuple.Pair;

public abstract class NullOrEmptyValueValidator<T> implements Validator<T, String> {

  @Override
  public String validate(T data) {

    Pair<String, String> fieldNameAndValue = getFieldNameAsLeftAndItsValueToCheckRight(data);

    String fieldName = fieldNameAndValue.getLeft();
    String value = fieldNameAndValue.getRight();

    if (value == null || value.trim().equals("")) {
      return fieldName + " has null or empty value";
    }

    return null;
  }

  protected abstract FieldNameAndValuePair getFieldNameAsLeftAndItsValueToCheckRight(final T data);

  protected class FieldNameAndValuePair extends Pair<String, String> {

    private static final long serialVersionUID = -3467597739276111262L;

    private final String fieldName;
    private final String value;

    public FieldNameAndValuePair(String fieldName, String value) {
      super();
      this.fieldName = fieldName;
      this.value = value;
    }

    @Override
    public String setValue(String value) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String getLeft() {
      return fieldName;
    }

    @Override
    public String getRight() {
      return value;
    }

  }
}
