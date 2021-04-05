package com.ef.dataaccess.event.blogger.query.validator;

import com.ef.model.event.EventVenue;
import com.ef.model.member.Member;

public class BloggerProfileValidationPayload {

  private Member blogger;
  private EventVenue venue;

  public BloggerProfileValidationPayload() {
  }

  public void setBlogger(Member member) {
    this.blogger = member;
  }

  public Member getBlogger() {
    return blogger;
  }

  public void setVenue(EventVenue venue) {
    this.venue = venue;
  }

  public EventVenue getVenue() {
    return venue;
  }

}
