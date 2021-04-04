package com.ef.model.event;

import com.ef.model.core.Identifiable;

public class EventVenue implements Identifiable {

  /**
   * 
   */
  private static final long serialVersionUID = -2538901031005159933L;
  private int id;
  private String name;
  private String zomatoUrl;
  private String venueUrl;
  private String address;
  private String city;

  public EventVenue() {

  }

  public EventVenue(int id, String name, String zomatoUrl, String venueUrl, String address, String city) {
    super();
    this.id = id;
    this.name = name;
    this.zomatoUrl = zomatoUrl;
    this.venueUrl = venueUrl;
    this.address = address;
    this.city = city;
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

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "EventVenue [id=" + id + ", name=" + name + ", zomatoUrl=" + zomatoUrl + ", venueUrl=" + venueUrl
        + ", address=" + address + ", city=" + city + "]";
  }

}
