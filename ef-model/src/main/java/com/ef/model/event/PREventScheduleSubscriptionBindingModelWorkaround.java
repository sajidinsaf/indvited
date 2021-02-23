package com.ef.model.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.google.gson.Gson;

public class PREventScheduleSubscriptionBindingModelWorkaround {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleSubscriptionBindingModelWorkaround.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private int memberId;
  private String preferredDate1;
  private String preferredTime1;
  private String preferredDate2;
  private String preferredTime2;
  private String preferredDate3;
  private String preferredTime3;

  public PREventScheduleSubscriptionBindingModelWorkaround() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleSubscriptionBindingModelWorkaround(int memberId, String preferredDate1, String preferredTime1,
      String preferredDate2, String preferredTime2, String preferredDate3, String preferredTime3) {
    super();
    this.memberId = memberId;
    this.preferredDate1 = preferredDate1;
    this.preferredTime1 = preferredTime1;
    this.preferredDate2 = preferredDate2;
    this.preferredTime2 = preferredTime2;
    this.preferredDate3 = preferredDate3;
    this.preferredTime3 = preferredTime3;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public String getPreferredDate1() {
    return preferredDate1;
  }

  public void setPreferredDate1(String preferredDate1) {
    this.preferredDate1 = preferredDate1;
  }

  public String getPreferredTime1() {
    return preferredTime1;
  }

  public void setPreferredTime1(String preferredTime1) {
    this.preferredTime1 = preferredTime1;
  }

  public String getPreferredDate2() {
    return preferredDate2;
  }

  public void setPreferredDate2(String preferredDate2) {
    this.preferredDate2 = preferredDate2;
  }

  public String getPreferredTime2() {
    return preferredTime2;
  }

  public void setPreferredTime2(String preferredTime2) {
    this.preferredTime2 = preferredTime2;
  }

  public String getPreferredDate3() {
    return preferredDate3;
  }

  public void setPreferredDate3(String preferredDate3) {
    this.preferredDate3 = preferredDate3;
  }

  public String getPreferredTime3() {
    return preferredTime3;
  }

  public void setPreferredTime3(String preferredTime3) {
    this.preferredTime3 = preferredTime3;
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionBindingModelWorkaround [memberId=" + memberId + ", preferredDate1="
        + preferredDate1 + ", preferredTime1=" + preferredTime1 + ", preferredDate2=" + preferredDate2
        + ", preferredTime2=" + preferredTime2 + ", preferredDate3=" + preferredDate3 + ", preferredTime3="
        + preferredTime3 + "]";
  }

  public PREventScheduleSubscriptionBindingModel[] getSubscriptions() {
    PREventScheduleSubscriptionBindingModel[] subscriptions = new PREventScheduleSubscriptionBindingModel[3];
    subscriptions[0] = getSubscription(preferredDate1, preferredTime1);
    subscriptions[1] = getSubscription(preferredDate2, preferredTime2);
    subscriptions[2] = getSubscription(preferredDate3, preferredTime3);

    return subscriptions;
  }

  private PREventScheduleSubscriptionBindingModel getSubscription(String preferredDate, String preferredTime) {
    if (preferredDate == null || preferredTime == null) {
      logUtil.info(logger, "Null parameter received. Ignoring parse and returning null", preferredDate, preferredTime);
      return null;
    }
    String date = preferredDate.split("\\-")[0];
    String subscriptionId = preferredDate.split("\\-")[1];
    return new PREventScheduleSubscriptionBindingModel(Long.parseLong(subscriptionId), memberId, date, preferredTime);
  }

  public static void main(String args[]) {
    int memberId = 242342;
    String preferredDate1 = "Thu 15 Jan 2020-23";
    String preferredTime1 = "12:30";
    String preferredDate2 = "Fri 17 Jan 2020-23";
    String preferredTime2 = "2:30";
    String preferredDate3 = "Sat 18 Jan 2020-23";
    String preferredTime3 = "20:30";
    PREventScheduleSubscriptionBindingModelWorkaround w = new PREventScheduleSubscriptionBindingModelWorkaround(
        memberId, preferredDate1, preferredTime1, preferredDate2, preferredTime2, preferredDate3, preferredTime3);

    System.out.println(new Gson().toJson(w));

  }

}
