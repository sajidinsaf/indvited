package com.ef.member.login.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;
import com.google.gson.Gson;

public class LoginService {
  private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Query<MemberLoginBindingModel, Member> loginMember;
  private final List<Validator<MemberLoginBindingModel, String>> validators;
  public static final String CREDENTIALS_INVALID_MESSAGE = "Username not found, password not matched or member not enabled";

  public LoginService(Query<MemberLoginBindingModel, Member> loginMember) {
    this.loginMember = loginMember;
    validators = new ArrayList<Validator<MemberLoginBindingModel, String>>();
  }

  public LoginService(Query<MemberLoginBindingModel, Member> loginMember,
      List<Validator<MemberLoginBindingModel, String>> validators) {
    this.loginMember = loginMember;
    this.validators = validators;
  }

  public Response<Member> loginMember(MemberLoginBindingModel memberLoginData) {
    logUtil.info(logger, "logging in member: ", memberLoginData);

    List<String> validationResults = validate(memberLoginData);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    Member member = loginMember.data(memberLoginData);

    if (member == null) {
      validationResults = new ArrayList<String>();
      validationResults.add(CREDENTIALS_INVALID_MESSAGE);
      logUtil.info(logger, "member login failed", CREDENTIALS_INVALID_MESSAGE);
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    return new Response<Member>(member, StatusCode.OK);
  }

  private List<String> validate(MemberLoginBindingModel memberLoginData) {
    List<String> validationResults = new ArrayList<String>();
    for (Validator<MemberLoginBindingModel, String> validator : validators) {
      String result = validator.validate(memberLoginData);
      if (result != null) {
        validationResults.add(result);
      }
    }
    return validationResults;
  }

  public static void main(String[] args) {
    MemberLoginBindingModel mlbm = new MemberLoginBindingModel("ashish@hotmail.com", "Bh1m@n1Street");
    System.out.println(new Gson().toJson(mlbm));
  }
}
