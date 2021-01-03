package com.ef.model.event;

public class PREventLocationBindingModel {
  private int id;
  private String venueName;
  private String venueAddress;
  private String zomatoUrl;
  private String venueUrl;

  public PREventLocationBindingModel() {

  }

  public PREventLocationBindingModel(String venueName, String venueAddress, String zomatoUrl, String venueUrl) {
    super();
    this.venueName = venueName;
    this.venueAddress = venueAddress;
    this.zomatoUrl = zomatoUrl;
  }

  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  public String getVenueAddress() {
    return venueAddress;
  }

  public void setVenueAddress(String venueAddress) {
    this.venueAddress = venueAddress;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "PREventLocationBindingModel [venueName=" + venueName + ", venueAddress=" + venueAddress + ", zomatoUrl="
        + zomatoUrl + ", venueUrl=" + venueUrl + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((venueName == null) ? 0 : venueName.hashCode());
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
    PREventLocationBindingModel other = (PREventLocationBindingModel) obj;
    if (venueName == null) {
      if (other.venueName != null)
        return false;
    } else if (!venueName.equals(other.venueName))
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

}
