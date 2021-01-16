package com.ef.model.event;

import java.util.Arrays;

import com.google.gson.Gson;

public class PREventBindingModel {

  private String eventCreatorEmailId;
  private String eventType;
  private String domainName;
  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;
  private String cap;
  private String exclusions;
  private PREventCriteriaBindingModel[] eventCriteria;
  private PREventLocationBindingModel eventLocation;
  private String notes;

  public PREventBindingModel() {

  }

  public PREventBindingModel(String eventCreatorEmailId, String eventType, String domainName,
      PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel, String cap, String exclusions,
      PREventCriteriaBindingModel[] eventCriteria, PREventLocationBindingModel eventLocation, String notes) {
    super();
    this.eventCreatorEmailId = eventCreatorEmailId;
    this.eventType = eventType;
    this.domainName = domainName;
    this.prEventTimeSlotBindingModel = prEventTimeSlotBindingModel;
    this.cap = cap;
    this.exclusions = exclusions;
    this.eventCriteria = eventCriteria;
    this.eventLocation = eventLocation;
    this.notes = notes;
  }

  public String getEventCreatorEmailId() {
    return eventCreatorEmailId;
  }

  public void setEventCreatorEmailId(String eventCreatorEmailId) {
    this.eventCreatorEmailId = eventCreatorEmailId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getExclusions() {
    return exclusions;
  }

  public void setExclusions(String exclusions) {
    this.exclusions = exclusions;
  }

  public PREventCriteriaBindingModel[] getEventCriteria() {
    return eventCriteria;
  }

  public void setEventCriteria(PREventCriteriaBindingModel[] eventCriteria) {
    this.eventCriteria = eventCriteria;
  }

  public PREventLocationBindingModel getEventLocation() {
    return eventLocation;
  }

  public void setEventLocation(PREventLocationBindingModel eventLocation) {
    this.eventLocation = eventLocation;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public PREventTimeSlotBindingModel[] getPrEventTimeSlotBindingModel() {
    return prEventTimeSlotBindingModel;
  }

  public void setPrEventTimeSlotBindingModel(PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel) {
    this.prEventTimeSlotBindingModel = prEventTimeSlotBindingModel;
  }

  @Override
  public String toString() {
    return "PREventBindingModel [eventCreatorEmailId=" + eventCreatorEmailId + ", eventType=" + eventType
        + ", prEventTimeSlotBindingModel=" + Arrays.toString(prEventTimeSlotBindingModel) + ", cap=" + cap
        + ", exclusions=" + exclusions + ", eventCriteria=" + Arrays.toString(eventCriteria) + ", eventLocation="
        + eventLocation + ", notes=" + notes + "]";
  }

  public static void main(String[] args) {
    PREventBindingModel event = new PREventBindingModel();
    event.setCap("1 cocktail each / 2 starters / 2 mains");
    event.setEventCreatorEmailId("myemail@email.com");

    event.setDomainName("Restaurant");
    PREventCriteriaBindingModel prEventCriteriaBindingModel1 = new PREventCriteriaBindingModel("Mininum Zomato reviews",
        175);
    PREventCriteriaBindingModel prEventCriteriaBindingModel2 = new PREventCriteriaBindingModel(
        "Minimum Instagram followers", 9000);

    event.setEventCriteria(
        new PREventCriteriaBindingModel[] { prEventCriteriaBindingModel1, prEventCriteriaBindingModel2 });

    PREventLocationBindingModel eventLocation = new PREventLocationBindingModel("Esora",
        "1st Floor, Commerz 2, International Business Park, Oberoi Garden City, Near Oberoi Mall, Goregaon East, Mumbai",
        "http://zoma.to/r/18789802", null);
    event.setEventLocation(eventLocation);
    event.setEventType("Restaurant Review");
    event.setExclusions(
        "*no red meat , no fish* IN case u want to order a *dessert or any dish apart of the above cap it would be *PAYABLE*");
    event.setNotes(
        "Find this restaurant on Zomato | Bhukkad, Shop 13, Mohid Heights, R. T. O. Road, Andheri Lokhandwala, Andheri West, Mumbai  http://zoma.to/r/19213346 (You have to place an order for 1 dish from menu lowest item need to be ordered  amount for which will not be refunded). *Timings 12pm till 3pm & 7pm till 9pm Available From Monday Till Sunday Only on Friday & Saturday  Available From 12pm till 3pm*");
    PREventTimeSlotBindingModel prEventTimeSlot1 = new PREventTimeSlotBindingModel();
    prEventTimeSlot1.setEventDate("15/01/2021");
    prEventTimeSlot1.setTimeFrom("1200");
    prEventTimeSlot1.setTimeTo("1600");

    PREventTimeSlotBindingModel prEventTimeSlot2 = new PREventTimeSlotBindingModel();
    prEventTimeSlot2.setEventDate("15/01/2021");
    prEventTimeSlot2.setTimeFrom("1800");
    prEventTimeSlot2.setTimeTo("2000");

    PREventTimeSlotBindingModel prEventTimeSlot3 = new PREventTimeSlotBindingModel();
    prEventTimeSlot3.setEventDate("16/01/2021");
    prEventTimeSlot3.setTimeFrom("1200");
    prEventTimeSlot3.setTimeTo("1600");

    PREventTimeSlotBindingModel prEventTimeSlot4 = new PREventTimeSlotBindingModel();
    prEventTimeSlot4.setEventDate("16/01/2021");
    prEventTimeSlot4.setTimeFrom("1800");
    prEventTimeSlot4.setTimeTo("2000");

    PREventTimeSlotBindingModel prEventTimeSlot5 = new PREventTimeSlotBindingModel();
    prEventTimeSlot5.setEventDate("17/01/2021");
    prEventTimeSlot5.setTimeFrom("1200");
    prEventTimeSlot5.setTimeTo("1600");

    PREventTimeSlotBindingModel prEventTimeSlot6 = new PREventTimeSlotBindingModel();
    prEventTimeSlot6.setEventDate("17/01/2021");
    prEventTimeSlot6.setTimeFrom("1800");
    prEventTimeSlot6.setTimeTo("2000");

    event.setPrEventTimeSlotBindingModel(new PREventTimeSlotBindingModel[] { prEventTimeSlot1, prEventTimeSlot2,
        prEventTimeSlot3, prEventTimeSlot4, prEventTimeSlot5, prEventTimeSlot6 });

    System.out.println(new Gson().toJson(event));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((domainName == null) ? 0 : domainName.hashCode());
    result = prime * result + ((eventCreatorEmailId == null) ? 0 : eventCreatorEmailId.hashCode());
    result = prime * result + Arrays.hashCode(eventCriteria);
    result = prime * result + ((eventLocation == null) ? 0 : eventLocation.hashCode());
    result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
    result = prime * result + Arrays.hashCode(prEventTimeSlotBindingModel);
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
    PREventBindingModel other = (PREventBindingModel) obj;
    if (domainName == null) {
      if (other.domainName != null)
        return false;
    } else if (!domainName.equals(other.domainName))
      return false;
    if (eventCreatorEmailId == null) {
      if (other.eventCreatorEmailId != null)
        return false;
    } else if (!eventCreatorEmailId.equals(other.eventCreatorEmailId))
      return false;
    if (!Arrays.equals(eventCriteria, other.eventCriteria))
      return false;
    if (eventLocation == null) {
      if (other.eventLocation != null)
        return false;
    } else if (!eventLocation.equals(other.eventLocation))
      return false;
    if (eventType == null) {
      if (other.eventType != null)
        return false;
    } else if (!eventType.equals(other.eventType))
      return false;
    if (!Arrays.equals(prEventTimeSlotBindingModel, other.prEventTimeSlotBindingModel))
      return false;
    return true;
  }

}
