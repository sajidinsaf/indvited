package com.ef.member.login.service.validation;

import com.ef.common.validation.NullOrEmptyValueValidator;
import com.ef.model.member.MemberLoginBindingModel;

public class EmailNotNullOrEmptyValidator extends NullOrEmptyValueValidator<MemberLoginBindingModel> {

  @Override
  protected FieldNameAndValuePair getFieldNameAsLeftAndItsValueToCheckRight(MemberLoginBindingModel data) {

    return new FieldNameAndValuePair("email", data.getEmail());
  }

}
