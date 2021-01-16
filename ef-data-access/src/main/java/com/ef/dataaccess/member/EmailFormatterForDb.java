package com.ef.dataaccess.member;

import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;

@Component("emailFormatterForDb")
public class EmailFormatterForDb implements Query<String, String> {

  public EmailFormatterForDb() {
  }

  @Override
  public String data(String emailId) {
    return emailId.toLowerCase();
  }

}
