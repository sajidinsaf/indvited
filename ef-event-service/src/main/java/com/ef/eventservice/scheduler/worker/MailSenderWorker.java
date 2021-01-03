package com.ef.eventservice.scheduler.worker;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.eventservice.scheduler.MessagePacket;
import com.ef.eventservice.scheduler.Subscriber;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;

public class MailSenderWorker implements Worker<MessagePacket, Response<String>> {

  private final JavaMailSender mailSender;
  private final Worker<MessagePacket, List<String>> emailAddressProvider;
  private final ServiceLoggingUtil loggingUtil;
  private final Logger logger = LoggerFactory.getLogger(Subscriber.class);
  private final String senderEmailAddress;

  public MailSenderWorker(JavaMailSender mailSender, String senderEmailAddress,
      Worker<MessagePacket, List<String>> emailAddressProvider) {
    this.mailSender = mailSender;
    this.emailAddressProvider = emailAddressProvider;
    loggingUtil = new ServiceLoggingUtil();
    this.senderEmailAddress = senderEmailAddress;
  }

  @Override
  public Response<String> perform(MessagePacket messagePacket) {

    List<String> emailAddresses = emailAddressProvider.perform(messagePacket);

    for (String toEmail : emailAddresses) {
      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      simpleMailMessage.setFrom(senderEmailAddress);
      simpleMailMessage.setTo(toEmail);
      simpleMailMessage.setSubject("Message from PR to " + toEmail);
      simpleMailMessage.setText(messagePacket.getMessage());
      simpleMailMessage.setReplyTo("indvited@codeczar.co.uk");
      simpleMailMessage.setSentDate(new Date());
      logger.info("Sending mail message to:" + simpleMailMessage.getTo()[0]);
      mailSender.send(simpleMailMessage);
      logger.info("Mail message sent to:" + simpleMailMessage.getTo()[0]);
    }

    StringBuilder responseText = new StringBuilder("Email sent to following recipients ");
    responseText.append(emailAddresses.toString());

    loggingUtil.debug(logger, responseText);
    return new Response<String>(responseText.toString(), StatusCode.OK);
  }

}
