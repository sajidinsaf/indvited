package com.ef.member.login.service.validation;

import com.ef.common.validation.NullOrEmptyValueValidator;
import com.ef.model.member.MemberLoginBindingModel;

public class PasswordNotNullOrEmptyValidator extends NullOrEmptyValueValidator<MemberLoginBindingModel> {

  @Override
  protected FieldNameAndValuePair getFieldNameAsLeftAndItsValueToCheckRight(MemberLoginBindingModel data) {

    return new FieldNameAndValuePair("password", data.getPassword());
  }

}
