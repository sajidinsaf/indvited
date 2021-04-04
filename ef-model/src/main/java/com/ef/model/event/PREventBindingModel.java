package com.ef.model.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ef.model.member.MemberType;
import com.google.gson.Gson;

public class PREventBindingModel {

  private int memberId;
  private MemberType memberType;
  private int eventType;
  private String domainName;
  private String cap;
  private String exclusions;
  private List<PREventCriteriaBindingModel> eventCriteria;
  private List<PREventDeliverableBindingModel> eventDeliverable;
  private PREventLocationBindingModel eventLocation = new PREventLocationBindingModel();
  private String notes;

  // These variable were created to overcome the limitation of form element to
  // Page Variable Object binding on AppGyver
  private int minZomatoLevel, minZomatoReviews, minInstagramFollowers;
  private boolean deliverZomatoReview, deliverInstagramPost, deliverInstagramStories, deliverLiveInstagramStories,
      deliverGoogleReview, deliverPlusOneReview;
  private InvitedBloggersBindingModel invitedBloggersBindingModel;
  private String venueName, venueAddress, venueUrl, city;

  public PREventBindingModel() {
    this.eventCriteria = new ArrayList<PREventCriteriaBindingModel>();
    this.eventDeliverable = new ArrayList<PREventDeliverableBindingModel>();
  }

  public PREventBindingModel(int memberId, MemberType memberType, int eventType, String domainName, String cap,
      String exclusions, PREventLocationBindingModel eventLocation, String notes, int minZomatoLevel,
      int minZomatoReviews, int minInstagramFollowers) {
    super();
    this.memberId = memberId;
    this.memberType = memberType;
    this.eventType = eventType;
    this.domainName = domainName;
    this.cap = cap;
    this.exclusions = exclusions;
    this.eventCriteria = new ArrayList<PREventCriteriaBindingModel>();
    this.eventDeliverable = new ArrayList<PREventDeliverableBindingModel>();
    this.eventLocation = eventLocation;
    this.notes = notes;
    this.minZomatoLevel = minZomatoLevel;
    this.minZomatoReviews = minZomatoReviews;
    this.minInstagramFollowers = minInstagramFollowers;
  }

  public PREventBindingModel(int memberId, MemberType memberType, int eventType, String domainName, String cap,
      String exclusions, List<PREventCriteriaBindingModel> eventCriteria,
      List<PREventDeliverableBindingModel> eventDeliverable, PREventLocationBindingModel eventLocation, String notes) {
    super();
    this.memberId = memberId;
    this.memberType = memberType;
    this.eventType = eventType;
    this.domainName = domainName;
    this.cap = cap;
    this.exclusions = exclusions;
    this.eventCriteria = eventCriteria;
    this.eventDeliverable = eventDeliverable;
    this.eventLocation = eventLocation;
    this.notes = notes;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getEventType() {
    return eventType;
  }

  public void setEventType(int eventType) {
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

  public int getMinZomatoLevel() {
    return minZomatoLevel;
  }

  public void setMinZomatoLevel(int minZomatoLevel) {
    if (minZomatoLevel > 0) {
      eventCriteria.add(new PREventCriteriaBindingModel("Minimum Zomato Level", minZomatoLevel));
    }
    this.minZomatoLevel = minZomatoLevel;
  }

  public int getMinZomatoReviews() {
    return minZomatoReviews;
  }

  public void setMinZomatoReviews(int minZomatoReviews) {
    if (minZomatoReviews > 0) {
      eventCriteria.add(new PREventCriteriaBindingModel("Minimum Zomato reviews", minZomatoReviews));
    }
    this.minZomatoReviews = minZomatoReviews;
  }

  public int getMinInstagramFollowers() {
    return minInstagramFollowers;
  }

  public void setMinInstagramFollowers(int minInstagramFollowers) {
    if (minInstagramFollowers > 0) {
      eventCriteria.add(new PREventCriteriaBindingModel("Minimum Instagram followers", minInstagramFollowers));
    }
    this.minInstagramFollowers = minInstagramFollowers;
  }

  public PREventLocationBindingModel getEventLocation() {
    return eventLocation;
  }

  public void setEventLocation(PREventLocationBindingModel eventLocation) {
    this.eventLocation = eventLocation;
  }

  public List<PREventCriteriaBindingModel> getEventCriteria() {
    return eventCriteria;
  }

  public void setEventCriteria(List<PREventCriteriaBindingModel> eventCriteria) {
    this.eventCriteria = eventCriteria;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public static void main(String[] args) {
    PREventBindingModel event = new PREventBindingModel();
    event.setCap("1 cocktail each / 2 starters / 2 mains");
    event.setMemberId(100010004);

    event.setDomainName("Restaurant");

    PREventCriteriaBindingModel prEventCriteriaBindingModel1 = new PREventCriteriaBindingModel("Mininum Zomato reviews",
        175);
    PREventCriteriaBindingModel prEventCriteriaBindingModel2 = new PREventCriteriaBindingModel(
        "Minimum Instagram followers", 9000);

    event.setEventCriteria(Arrays.asList(prEventCriteriaBindingModel1, prEventCriteriaBindingModel2));

    PREventDeliverableBindingModel pREventDeliverableBindingModel = new PREventDeliverableBindingModel("Zomato Review");
    PREventDeliverableBindingModel pREventDeliverableBindingModel2 = new PREventDeliverableBindingModel(
        "Instagram Post");

    event.setEventDeliverable(Arrays.asList(pREventDeliverableBindingModel, pREventDeliverableBindingModel2));

    PREventLocationBindingModel eventLocation = new PREventLocationBindingModel("Esora",
        "1st Floor, Commerz 2, International Business Park, Oberoi Garden City, Near Oberoi Mall, Goregaon East, Mumbai",
        "http://zoma.to/r/18789802", null, "Mumbai");
    event.setEventLocation(eventLocation);
    event.setEventType(1);
    event.setExclusions(
        "*no red meat , no fish* IN case u want to order a *dessert or any dish apart of the above cap it would be *PAYABLE*");
    event.setNotes(
        "Find this restaurant on Zomato | Bhukkad, Shop 13, Mohid Heights, R. T. O. Road, Andheri Lokhandwala, Andheri West, Mumbai  http://zoma.to/r/19213346 (You have to place an order for 1 dish from menu lowest item need to be ordered  amount for which will not be refunded). *Timings 12pm till 3pm & 7pm till 9pm Available From Monday Till Sunday Only on Friday & Saturday  Available From 12pm till 3pm*");
//    PREventTimeSlotBindingModel prEventTimeSlot1 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot1.setEventDate("15/01/2021");
//    prEventTimeSlot1.setTimeFrom("1200");
//    prEventTimeSlot1.setTimeTo("1600");
//
//    PREventTimeSlotBindingModel prEventTimeSlot2 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot2.setEventDate("15/01/2021");
//    prEventTimeSlot2.setTimeFrom("1800");
//    prEventTimeSlot2.setTimeTo("2000");
//
//    PREventTimeSlotBindingModel prEventTimeSlot3 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot3.setEventDate("16/01/2021");
//    prEventTimeSlot3.setTimeFrom("1200");
//    prEventTimeSlot3.setTimeTo("1600");
//
//    PREventTimeSlotBindingModel prEventTimeSlot4 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot4.setEventDate("16/01/2021");
//    prEventTimeSlot4.setTimeFrom("1800");
//    prEventTimeSlot4.setTimeTo("2000");
//
//    PREventTimeSlotBindingModel prEventTimeSlot5 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot5.setEventDate("17/01/2021");
//    prEventTimeSlot5.setTimeFrom("1200");
//    prEventTimeSlot5.setTimeTo("1600");
//
//    PREventTimeSlotBindingModel prEventTimeSlot6 = new PREventTimeSlotBindingModel();
//    prEventTimeSlot6.setEventDate("17/01/2021");
//    prEventTimeSlot6.setTimeFrom("1800");
//    prEventTimeSlot6.setTimeTo("2000");
//
//    event.setPrEventTimeSlotBindingModel(new PREventTimeSlotBindingModel[] { prEventTimeSlot1, prEventTimeSlot2,
//        prEventTimeSlot3, prEventTimeSlot4, prEventTimeSlot5, prEventTimeSlot6 });

    System.out.println(new Gson().toJson(event));
  }

  public List<PREventDeliverableBindingModel> getEventDeliverable() {
    return eventDeliverable;
  }

  public void setEventDeliverable(List<PREventDeliverableBindingModel> eventDeliverable) {
    this.eventDeliverable = eventDeliverable;
  }

  public boolean isDeliverZomatoReview() {
    return deliverZomatoReview;
  }

//  INSERT INTO `event_deliverable_meta`(`name`, `description`) VALUES ("Zomato Review","Zomato Review Required");
//  INSERT INTO `event_deliverable_meta`(`name`, `description`) VALUES ("Instagram Post","Instagram Post Required");
//  INSERT INTO `event_deliverable_meta`(`name`, `description`) VALUES ("Instagram Stories","Instagram Stories Required");
//  INSERT INTO `event_deliverable_meta`(`name`, `description`) VALUES ("Live Instagram Stories","Live Instagram Stories Required");
//  INSERT INTO `event_deliverable_meta`(`name`, `description`) VALUES ("Google Review","Google Review Required");
//  
  public void setDeliverZomatoReview(boolean isZomatoReviewRequired) {
    if (isZomatoReviewRequired) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Zomato Review"));
    }
    this.deliverZomatoReview = isZomatoReviewRequired;
  }

  public boolean isDeliverInstagramPost() {
    return deliverInstagramPost;
  }

  public void setDeliverInstagramPost(boolean isInstagramPostRequired) {
    if (isInstagramPostRequired) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Instagram Post"));
    }
    this.deliverInstagramPost = isInstagramPostRequired;
  }

  public boolean isDeliverInstagramStories() {
    return deliverInstagramStories;
  }

  public void setDeliverInstagramStories(boolean isInstagramStoriesRquired) {
    if (isInstagramStoriesRquired) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Instagram Stories"));
    }
    this.deliverInstagramStories = isInstagramStoriesRquired;
  }

  public boolean isDeliverLiveInstagramStories() {
    return deliverLiveInstagramStories;
  }

  public void setDeliverLiveInstagramStories(boolean isLiveInstagramStoriesRequired) {
    if (isLiveInstagramStoriesRequired) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Live Instagram Stories"));
    }
    this.deliverLiveInstagramStories = isLiveInstagramStoriesRequired;
  }

  public boolean isDeliverGoogleReview() {
    return deliverGoogleReview;
  }

  public void setDeliverGoogleReview(boolean isGoogleReviewRequired) {
    if (isGoogleReviewRequired) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Google Review"));
    }
    this.deliverGoogleReview = isGoogleReviewRequired;
  }

  public boolean isDeliverPlusOneReview() {
    return deliverPlusOneReview;
  }

  public void setDeliverPlusOneReview(boolean deliverPlusOneReview) {
    if (deliverPlusOneReview) {
      eventDeliverable.add(new PREventDeliverableBindingModel("Plus One Review"));
    }
    this.deliverPlusOneReview = deliverPlusOneReview;
  }

  public InvitedBloggersBindingModel getInvitedBloggersBindingModel() {
    return invitedBloggersBindingModel;
  }

  public void setInvitedBloggersBindingModel(InvitedBloggersBindingModel invitedBloggersBindingModel) {
    this.invitedBloggersBindingModel = invitedBloggersBindingModel;
  }

  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    eventLocation.setVenueName(venueName);
    this.venueName = venueName;
  }

  public String getVenueAddress() {
    return venueAddress;
  }

  public void setVenueAddress(String venueAddress) {
    eventLocation.setVenueAddress(venueAddress);
    this.venueAddress = venueAddress;
  }

  public String getVenueUrl() {
    return venueUrl;
  }

  public void setVenueUrl(String venueUrl) {
    eventLocation.setVenueUrl(venueUrl);
    this.venueUrl = venueUrl;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    this.memberType = memberType;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    eventLocation.setCity(city);
    this.city = city;
  }

  @Override
  public String toString() {
    return "PREventBindingModel [memberId=" + memberId + ", memberType=" + memberType + ", eventType=" + eventType
        + ", domainName=" + domainName + ", cap=" + cap + ", exclusions=" + exclusions + ", eventCriteria="
        + eventCriteria + ", eventDeliverable=" + eventDeliverable + ", eventLocation=" + eventLocation + ", notes="
        + notes + ", minZomatoLevel=" + minZomatoLevel + ", minZomatoReviews=" + minZomatoReviews
        + ", minInstagramFollowers=" + minInstagramFollowers + ", deliverZomatoReview=" + deliverZomatoReview
        + ", deliverInstagramPost=" + deliverInstagramPost + ", deliverInstagramStories=" + deliverInstagramStories
        + ", deliverLiveInstagramStories=" + deliverLiveInstagramStories + ", deliverGoogleReview="
        + deliverGoogleReview + ", deliverPlusOneReview=" + deliverPlusOneReview + ", invitedBloggersBindingModel="
        + invitedBloggersBindingModel + ", venueName=" + venueName + ", venueAddress=" + venueAddress + ", venueUrl="
        + venueUrl + ", city=" + city + "]";
  }

}
