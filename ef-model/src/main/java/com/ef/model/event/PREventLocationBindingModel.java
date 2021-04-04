package com.ef.model.event;

public class PREventLocationBindingModel {
  private int id;
  private String venueName;
  private String venueAddress;
  private String zomatoUrl;
  private String venueUrl;
  private String city;

  public PREventLocationBindingModel() {

  }

  public PREventLocationBindingModel(String venueName, String venueAddress, String zomatoUrl, String venueUrl,
      String city) {
    super();
    this.venueName = venueName;
    this.venueAddress = venueAddress;
    this.zomatoUrl = zomatoUrl;
    this.city = city;
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

  public int getEventId() {
    return id;
  }

  public void setEventId(int id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "PREventLocationBindingModel [id=" + id + ", venueName=" + venueName + ", venueAddress=" + venueAddress
        + ", zomatoUrl=" + zomatoUrl + ", venueUrl=" + venueUrl + ", city=" + city + "]";
  }

}
