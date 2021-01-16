package com.ef.model.core;

public interface Criteria<T> extends Identifiable {

  boolean isSatisfiedBy(T matchableObject);
}
