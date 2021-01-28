package com.ef.member.registration.model;

import com.ef.model.member.Member;

public class RegistrationConfirmationMessageModel {

  private final String toEmailAddress;
  private final String message;
  private static final String LINE_BREAK = "<br/>";

  public RegistrationConfirmationMessageModel(Member member) {
    this.toEmailAddress = member.getEmail();
    this.message = createMessage(member);
  }

  public String getToEmailAddress() {
    return toEmailAddress;
  }

  public String getMessage() {
    return message;
  }

  private String createMessage(Member member) {

    String firstName = member.getFirstName();
    StringBuilder sb = new StringBuilder("<body>Dear ");
    sb.append(firstName).append(",");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Thank you for registering at Indvited");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Your registration is enabled");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Your member id is: ").append(member.getId());
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Please quote this member id when communicating with customer services.");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Your are registered as a: ").append(member.getMemberType().getName());
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("You can now login to the Invited Application");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("For any queries, please contact indvited@codeczar.co.uk. Quote your member id in the subject.");

    sb.append("</body>");
    return sb.toString();
  }

  @Override
  public String toString() {
    return "RegistrationConfirmationMessageModel [toEmailAddress=" + toEmailAddress + ", message=" + message + "]";
  }

}
