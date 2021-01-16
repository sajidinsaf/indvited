package com.ef.common;

import org.apache.commons.lang3.tuple.Pair;

public class LRPair<L, R> extends Pair<L, R> {
  private static final long serialVersionUID = 1023929892626731777L;

  private L left;
  private R right;

  public LRPair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public R setValue(R value) {
    return right;
  }

  @Override
  public L getLeft() {
    return left;
  }

  @Override
  public R getRight() {
    return right;
  }

  @Override
  public String toString() {
    return "LRPair [left=" + left + ", right=" + right + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((left == null) ? 0 : left.hashCode());
    result = prime * result + ((right == null) ? 0 : right.hashCode());
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    LRPair other = (LRPair) obj;
    if (left == null) {
      if (other.left != null)
        return false;
    } else if (!left.equals(other.left))
      return false;
    if (right == null) {
      if (other.right != null)
        return false;
    } else if (!right.equals(other.right))
      return false;
    return true;
  }

}