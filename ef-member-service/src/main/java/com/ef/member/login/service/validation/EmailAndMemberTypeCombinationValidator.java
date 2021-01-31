package com.ef.member.login.service.validation;

import org.springframework.dao.EmptyResultDataAccessException;

import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;

public class EmailAndMemberTypeCombinationValidator implements Validator<MemberLoginBindingModel, String> {

  private final Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType;

  public EmailAndMemberTypeCombinationValidator(
      Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType) {
    this.queryMemberByEmailAndMemberType = queryMemberByEmailAndMemberType;
  }

  @Override
  public String validate(MemberLoginBindingModel data) {
    try {
      queryMemberByEmailAndMemberType.data(data);
      return null;
    } catch (EmptyResultDataAccessException e) {
      return "Email not found or invalid email id and member type combination";
    }

  }

}
