package com.ef.member.registration.service.validation;

import java.util.regex.Pattern;

import com.ef.common.validation.Validator;
import com.ef.model.member.MemberRegistrationBindingModel;

public class PasswordValidator implements Validator<MemberRegistrationBindingModel, String> {
  public static final String PASSWORD_VALID_CRITERIA_MESSAGE = "password must be 8-20 characters in length, contain at least one number, at least one lower case letter, at least on capital letter, at least one special character '@#$%^&-+=() and no spaces";
  public static final String PASSWORD_NULLOREMPTY_MESSAGE = "password cannot be null or empty";
//  ^ represents starting character of the string.
//  (?=.*[0-9]) represents a digit must occur at least once.
//  (?=.*[a-z]) represents a lower case alphabet must occur at least once.
//  (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
//  (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
//  (?=\\S+$) white spaces don’t allowed in the entire string.
//  .{8, 20} represents at least 8 characters and at most 20 characters.
//  $ represents the end of the string.
  private static final Pattern PASSWORD_CHECK = Pattern
      .compile("^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$");

  public PasswordValidator() {
  }

  @Override
  public String validate(MemberRegistrationBindingModel data) {

    String pwd = data.getPassword();
    if (pwd == null || pwd.trim().equals("")) {
      return PASSWORD_NULLOREMPTY_MESSAGE;
    }

    if (PASSWORD_CHECK.matcher(pwd).matches()) {
      return null;
    }

    return PASSWORD_VALID_CRITERIA_MESSAGE;
  }

}
