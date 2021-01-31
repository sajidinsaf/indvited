package com.ef.member.registration.service.validation;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ef.common.LRPair;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.MemberType;

public class UniqueValueValidator implements Validator<MemberRegistrationBindingModel, String> {

  private final Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType;
  private final Query<Pair<String, MemberType>, Member> queryMemberByPhoneAndMemberType;
  private final MemberTypeCache memberTypeCache;

  public UniqueValueValidator(MemberTypeCache memberTypeCache,
      Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType,
      Query<Pair<String, MemberType>, Member> queryMemberByPhoneAndMemberType) {
    this.queryMemberByEmailAndMemberType = queryMemberByEmailAndMemberType;
    this.queryMemberByPhoneAndMemberType = queryMemberByPhoneAndMemberType;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public String validate(MemberRegistrationBindingModel data) {

    StringBuilder sb = null;

    MemberType memberType = memberTypeCache.getMemberType(data.getMemberType());
    MemberLoginBindingModel loginModel = new MemberLoginBindingModel(data.getEmail(), null);
    loginModel.setMemberType(memberType);

    Member member = null;

    try {
      member = queryMemberByEmailAndMemberType.data(loginModel);
    } catch (EmptyResultDataAccessException e) {
    }

    if (member != null) {
      sb = new StringBuilder();
      sb.append("email not unique");
    }

    member = null;
    try {
      LRPair<String, MemberType> phoneAndMemberType = new LRPair<String, MemberType>(data.getPhone(), memberType);
      member = queryMemberByPhoneAndMemberType.data(phoneAndMemberType);
    } catch (EmptyResultDataAccessException e) {

    }

    if (member != null) {
      if (sb == null) {
        sb = new StringBuilder();
      } else {
        sb.append(",");
      }
      sb.append("phone not unique");
    }
    return sb == null ? null : sb.toString();
  }

}
