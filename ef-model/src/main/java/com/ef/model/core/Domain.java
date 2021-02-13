package com.ef.model.core;

public class Domain implements Identifiable {

  private static final long serialVersionUID = 8226190935821525428L;

  public static final int KNOWN_DOMAIN_ID_RESTAURANT = 1;
  public static final int KNOWN_DOMAIN_ID_HOTEL = 2;
  public static final int KNOWN_DOMAIN_ID_LIFESTYLE = 3;
  public static final int KNOWN_DOMAIN_ID_FASHION = 4;
  public static final int KNOWN_DOMAIN_ID_DIET_AND_NUTRITION = 5;
  public static final int KNOWN_DOMAIN_ID_BABY_PRODUCTS = 6;
  public static final int KNOWN_DOMAIN_ID_HEALTH_AND_BEAUTY = 7;
  public static final int KNOWN_DOMAIN_ID_TRAVEL = 8;
  public static final int KNOWN_DOMAIN_ID_TECHNOLOGY = 9;

  private final int id;
  private final String name;

  public Domain(int id, String name) {
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
    return "Domain [id=" + id + ", name=" + name + "]";
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
    Domain other = (Domain) obj;
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
