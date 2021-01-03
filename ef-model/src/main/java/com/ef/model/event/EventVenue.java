package com.ef.model.event;

public class EventVenue {

  private int id;
  private String name;
  private String zomatoUrl;
  private String venueUrl;
  private String address;

  public EventVenue() {

  }

  public EventVenue(int id, String name, String zomatoUrl, String venueUrl, String address) {
    super();
    this.id = id;
    this.name = name;
    this.zomatoUrl = zomatoUrl;
    this.venueUrl = venueUrl;
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getZomatoUrl() {
    return zomatoUrl;
  }

  public void setZomatoUrl(String zomatoUrl) {
    this.zomatoUrl = zomatoUrl;
  }

  public String getVenueUrl() {
    return venueUrl;
  }

  public void setVenueUrl(String venueUrl) {
    this.venueUrl = venueUrl;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((venueUrl == null) ? 0 : venueUrl.hashCode());
    result = prime * result + ((zomatoUrl == null) ? 0 : zomatoUrl.hashCode());
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
    EventVenue other = (EventVenue) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (venueUrl == null) {
      if (other.venueUrl != null)
        return false;
    } else if (!venueUrl.equals(other.venueUrl))
      return false;
    if (zomatoUrl == null) {
      if (other.zomatoUrl != null)
        return false;
    } else if (!zomatoUrl.equals(other.zomatoUrl))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "EventVenue [id=" + id + ", name=" + name + ", zomatoUrl=" + zomatoUrl + ", venueUrl=" + venueUrl
        + ", address=" + address + "]";
  }

}
