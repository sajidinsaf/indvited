package com.ef.member.registration.service.validation;

import com.ef.common.validation.Validator;
import com.ef.model.member.MemberRegistrationBindingModel;

public class MemberRegistrationBindingModelPasswordValidator extends PasswordValidator
    implements Validator<MemberRegistrationBindingModel, String> {

  @Override
  public String validate(MemberRegistrationBindingModel data) {
    return super.validate(data);
  }

}
