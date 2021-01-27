package com.ef.member.login.service.validation;

import org.springframework.dao.EmptyResultDataAccessException;

import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.member.MemberLoginBindingModel;

public class EmailAndMemberTypeCombinationValidator implements Validator<MemberLoginBindingModel, String> {

  private final Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType;

  public EmailAndMemberTypeCombinationValidator(
      Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType) {
    this.queryMemberIdByEmailAndMemberType = queryMemberIdByEmailAndMemberType;
  }

  @Override
  public String validate(MemberLoginBindingModel data) {
    try {
      queryMemberIdByEmailAndMemberType.data(data);
      return null;
    } catch (EmptyResultDataAccessException e) {
      return "Email not found or invalid email id and member type combination";
    }

  }

}
