package com.ef.member.registration.service.worker;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

import com.ef.common.EmailSender;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;

public class JavaMailEmailSender implements EmailSender<MimeMessage, String> {

  private JavaMailSender mailSender;

  public JavaMailEmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public Response<String> send(MimeMessage message) {
    mailSender.send(message);
    return new Response<String>("Message sent successfully: " + message, StatusCode.OK);
  }

}
