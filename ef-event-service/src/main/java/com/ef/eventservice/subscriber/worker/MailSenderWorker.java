package com.ef.eventservice.subscriber.worker;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.ef.common.MapBasedContext;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.work.Worker;

public class MailSenderWorker implements Worker<MessagePacket<String>, Response<String>, MapBasedContext> {

  private final JavaMailSender mailSender;
  private final Worker<MessagePacket<String>, List<String>, MapBasedContext> emailAddressProvider;
  private final ServiceLoggingUtil loggingUtil;
  private final Logger logger = LoggerFactory.getLogger(MailSenderWorker.class);
  private final String senderEmailAddress;

  public MailSenderWorker(JavaMailSender mailSender, String senderEmailAddress,
      Worker<MessagePacket<String>, List<String>, MapBasedContext> emailAddressProvider) {
    this.mailSender = mailSender;
    this.emailAddressProvider = emailAddressProvider;
    loggingUtil = new ServiceLoggingUtil();
    this.senderEmailAddress = senderEmailAddress;
  }

  @Override
  public Response<String> perform(MessagePacket<String> messagePacket, MapBasedContext context) {

    List<String> emailAddresses = emailAddressProvider.perform(messagePacket, context);

    for (String toEmail : emailAddresses) {
      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      simpleMailMessage.setFrom(senderEmailAddress);
      simpleMailMessage.setTo(toEmail);
      simpleMailMessage.setSubject("Message from PR to " + toEmail);
      simpleMailMessage.setText(messagePacket.getPayload());
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
