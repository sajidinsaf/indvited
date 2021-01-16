package com.ef.model.core;

public abstract class Profile implements Identifiable {

  private static final long serialVersionUID = 4973109638118064442L;

  private final int id;
  private final String name;

  public Profile(int id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Profile [id=" + id + ", name=" + name + "]";
  }

  public boolean satisfies(Criteria<Profile>[] matchingCriteria) {
    for (Criteria<Profile> c : matchingCriteria) {
      if (!c.isSatisfiedBy(this)) {
        return false;
      }
    }
    return true;
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
    Profile other = (Profile) obj;
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
