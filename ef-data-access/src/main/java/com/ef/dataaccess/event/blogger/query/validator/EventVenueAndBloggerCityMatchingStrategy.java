package com.ef.dataaccess.event.blogger.query.validator;

import com.ef.model.event.EventVenue;
import com.ef.model.member.Member;
import com.ef.model.member.MemberAddress;

public class EventVenueAndBloggerCityMatchingStrategy implements BloggerEligibleEventValidator {

  public EventVenueAndBloggerCityMatchingStrategy() {

  }

  @Override
  public Boolean validate(BloggerProfileValidationPayload data) {

    EventVenue venue = data.getVenue();
    Member member = data.getBlogger();

    String city = venue.getCity();

    MemberAddress address = member.getMemberAddress();
    return address != null && address.getCity() != null && address.getCity().equals(city);
  }

}
