package com.ef.member.registration.service.validation;

import com.ef.common.validation.Validator;
import com.ef.model.member.MemberTokenAuthBindingModel;

public class MemberTokenAuthBindingModelPasswordValidator extends PasswordValidator
    implements Validator<MemberTokenAuthBindingModel, String> {

  @Override
  public String validate(MemberTokenAuthBindingModel data) {
    return super.validate(data);
  }

}
