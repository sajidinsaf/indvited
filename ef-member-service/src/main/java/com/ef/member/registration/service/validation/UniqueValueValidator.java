package com.ef.member.registration.service.validation;

import org.springframework.dao.EmptyResultDataAccessException;

import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

public class UniqueValueValidator implements Validator<MemberRegistrationBindingModel, String> {

  private final Query<String, Member> queryMemberByEmail;
  private final Query<String, Member> queryMemberByPhone;

  public UniqueValueValidator(Query<String, Member> queryMemberByEmail, Query<String, Member> queryMemberByPhone) {
    this.queryMemberByEmail = queryMemberByEmail;
    this.queryMemberByPhone = queryMemberByPhone;
  }

  @Override
  public String validate(MemberRegistrationBindingModel data) {

    StringBuilder sb = null;

    if (isNotUnique(queryMemberByEmail, data.getEmail())) {
      sb = new StringBuilder();
      sb.append("email not unique");
    }
    if (isNotUnique(queryMemberByPhone, data.getPhone())) {
      if (sb == null) {
        sb = new StringBuilder();
      } else {
        sb.append(",");
      }
      sb.append("phone not unique");
    }

    return sb == null ? null : sb.toString();
  }

  private boolean isNotUnique(Query<String, Member> query, String data) {
    try {
      Member member = query.data(data);
      if (member != null) {
        return true;
      }
    } catch (EmptyResultDataAccessException e) {
    }
    return false;
  }

}
