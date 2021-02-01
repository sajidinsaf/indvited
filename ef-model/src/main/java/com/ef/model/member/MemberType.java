package com.ef.model.member;

import java.io.Serializable;

public class MemberType implements Serializable {

  private static final long serialVersionUID = 897249091518055980L;

  public static final int KNOWN_MEMBER_TYPE_ADMIN = 1;
  public static final int KNOWN_MEMBER_TYPE_PR = 2;
  public static final int KNOWN_MEMBER_TYPE_BLOGGER = 3;
  private final int id;
  private final String name;

  public MemberType(int id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "MemberType [id=" + id + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemberType other = (MemberType) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
