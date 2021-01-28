package com.ef.eventservice.validation;

import org.springframework.dao.EmptyResultDataAccessException;

import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

public class MemberTypeValidator implements Validator<PREventBindingModel, String> {

  private final Query<String, Member> queryMember;

  public MemberTypeValidator(Query<String, Member> queryMember) {
    this.queryMember = queryMember;

  }

  @Override
  public String validate(PREventBindingModel data) {
    try {
      Member member = queryMember.data(data.getEventCreatorEmailId());
      if (!MemberType.PR.equals(member.getMemberType())) {
        return data.getEventCreatorEmailId() + " is not authorised to create events.";
      }
    } catch (EmptyResultDataAccessException e) {
      return "Member does not exist: " + data.getEventCreatorEmailId();
    }
    return null;
  }

}
