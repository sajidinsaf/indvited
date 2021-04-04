package com.ef.model.event.report;

import com.google.gson.Gson;

public class EventScheduleSubscribersQueryParameters {

  private String dateFrom, dateTo;
  private int prId;

  public EventScheduleSubscribersQueryParameters() {

  }

  public EventScheduleSubscribersQueryParameters(int prId, String dateFrom, String dateTo) {
    super();
    this.prId = prId;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

  public static void main(String[] args) {
    EventScheduleSubscribersQueryParameters params = new EventScheduleSubscribersQueryParameters(1000010016,
        "2021-04-10", "2021-04-17");
    System.out.println(new Gson().toJson(params));
  }

  public int getPrId() {
    return prId;
  }

  public void setPrId(int prId) {
    this.prId = prId;
  }

  @Override
  public String toString() {
    return "EventScheduleSubscribersQueryParameters [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", prId=" + prId
        + "]";
  }

}
