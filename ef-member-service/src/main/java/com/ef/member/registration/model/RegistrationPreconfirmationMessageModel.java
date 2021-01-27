package com.ef.member.registration.model;

import static com.ef.member.registration.controller.RegistrationControllerConstants.CONFIRM_REGISTER_MEMBER;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ef.model.member.PreconfirmationMemberRegistrationModel;

public class RegistrationPreconfirmationMessageModel {

  private final String toEmailAddress;
  private final String message;
  private static final String LINE_BREAK = "<br/>";

  public RegistrationPreconfirmationMessageModel(PreconfirmationMemberRegistrationModel registrationConfirmationModel) {
    this.toEmailAddress = registrationConfirmationModel.getMember().getEmail();
    this.message = createMessage(registrationConfirmationModel);
  }

  public String getToEmailAddress() {
    return toEmailAddress;
  }

  public String getMessage() {
    return message;
  }

  private String createMessage(PreconfirmationMemberRegistrationModel registrationConfirmationModel) {
    String confirmationCode = registrationConfirmationModel.getRegistrationConfirmationCode().getCode();
    String firstName = registrationConfirmationModel.getMember().getFirstName();
    StringBuilder sb = new StringBuilder("<body>Dear ");
    sb.append(firstName).append(",");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Thank you for registering at Indvited");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append("Click below to confirm your email address");
    sb.append(LINE_BREAK);
    sb.append("<h2><a href=\"https://secure.codeczar.co.uk/ef-member-service").append(CONFIRM_REGISTER_MEMBER)
        .append("/").append(confirmationCode).append("\">Confirm Indvited Registration</a></h2>");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);

    Date expiryDate = new Date(
        registrationConfirmationModel.getRegistrationConfirmationCode().getExpiryTimestamp().getTime());
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    String strDate = formatter.format(expiryDate);
    sb.append("<p><span style=\"color:red\"><h3>This link will expire on ").append(strDate).append("</h3></span></p>");
    sb.append(LINE_BREAK);
    sb.append(LINE_BREAK);
    sb.append(
        "If you did register this email address, please forward this email to indvited@codeczar.co.uk with subject \"I did not register this email address\"");
    sb.append("</body>");
    return sb.toString();
  }

  @Override
  public String toString() {
    return "RegistrationPreconfirmationMessageModel [toEmailAddress=" + toEmailAddress + ", message=" + message + "]";
  }

}
