package com.ef.model.event;

import java.io.Serializable;

public class InvitedBloggersBindingModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 915312919252427278L;
  private boolean inviteAllEligible, inviteInnerCircle, inviteMyBloggers;

  public InvitedBloggersBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public InvitedBloggersBindingModel(boolean inviteAllEligible, boolean inviteInnerCircle, boolean inviteMyBloggers) {
    super();
    this.inviteAllEligible = inviteAllEligible;
    this.inviteInnerCircle = inviteInnerCircle;
    this.inviteMyBloggers = inviteMyBloggers;
  }

  public boolean isInviteAllEligible() {
    return inviteAllEligible;
  }

  public void setInviteAllEligible(boolean inviteAllEligible) {
    this.inviteAllEligible = inviteAllEligible;
  }

  public boolean isInviteInnerCircle() {
    return inviteInnerCircle;
  }

  public void setInviteInnerCircle(boolean inviteInnerCircle) {
    this.inviteInnerCircle = inviteInnerCircle;
  }

  public boolean isInviteMyBloggers() {
    return inviteMyBloggers;
  }

  public void setInviteMyBloggers(boolean inviteMyBloggers) {
    this.inviteMyBloggers = inviteMyBloggers;
  }

  @Override
  public String toString() {
    return "InvitedBloggersBindingModel [inviteAllEligible=" + inviteAllEligible + ", inviteInnerCircle="
        + inviteInnerCircle + ", inviteMyBloggers=" + inviteMyBloggers + "]";
  }

}
