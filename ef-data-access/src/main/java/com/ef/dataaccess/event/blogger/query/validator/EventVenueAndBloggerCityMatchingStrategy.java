package com.ef.dataaccess.event.blogger.query.validator;

import org.apache.commons.lang3.tuple.Pair;

import com.ef.common.validation.Validator;
import com.ef.model.event.EventVenue;
import com.ef.model.member.Member;

public class EventVenueAndBloggerCityMatchingStrategy implements Validator<Pair<EventVenue, Member>, Boolean> {

  public EventVenueAndBloggerCityMatchingStrategy() {

  }

  @Override
  public Boolean validate(Pair<EventVenue, Member> data) {

    EventVenue venue = data.getLeft();
    Member member = data.getRight();

    String city = venue.getCity();
    return null;
  }

}
