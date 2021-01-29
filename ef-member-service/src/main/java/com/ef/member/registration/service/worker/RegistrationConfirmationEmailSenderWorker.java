package com.ef.member.registration.service.worker;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.EmailSender;
import com.ef.common.MapBasedContext;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.work.Worker;
import com.ef.member.registration.model.RegistrationConfirmationMessageModel;

public class RegistrationConfirmationEmailSenderWorker
    implements Worker<MessagePacket<RegistrationConfirmationMessageModel>, Response<String>, MapBasedContext> {

  private final EmailSender<MimeMessage, String> mailSender;
  private final ServiceLoggingUtil loggingUtil;
  private final Logger logger = LoggerFactory.getLogger(RegistrationConfirmationEmailSenderWorker.class);
  private final String senderEmailAddress;
  private Session session;

  public RegistrationConfirmationEmailSenderWorker(EmailSender<MimeMessage, String> mailSender, Session session,
      String senderEmailAddress) {
    this.mailSender = mailSender;

    loggingUtil = new ServiceLoggingUtil();
    this.senderEmailAddress = senderEmailAddress;
    this.session = session;
  }

  @Override
  public Response<String> perform(MessagePacket<RegistrationConfirmationMessageModel> messagePacket,
      MapBasedContext context) {

    String toEmail = messagePacket.getPayload().getToEmailAddress();
    String messageBody = messagePacket.getPayload().getMessage();

    MimeMessage mailMessage = new MimeMessage(session);
    try {
      mailMessage.setFrom(new InternetAddress(senderEmailAddress));
      mailMessage.setSender(new InternetAddress(senderEmailAddress));

      mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
      mailMessage.setSubject("Welcome to Invited");
      mailMessage.setContent(messageBody, "text/html");

      mailMessage.setSentDate(new Date());
      logger.info("Sending mail message to:" + mailMessage.getAllRecipients()[0]);
      mailSender.send(mailMessage);
      logger.info("Mail message sent to:" + mailMessage.getAllRecipients()[0]);
    } catch (Exception e) {
      String message = "Exception while sending registration confiramtion email to " + toEmail;
      loggingUtil.exception(logger, e, message);
      throw new RuntimeException(message, e);

    }
    StringBuilder responseText = new StringBuilder("Email sent to following recipient ");
    responseText.append(toEmail);

    loggingUtil.debug(logger, responseText);
    return new Response<String>(responseText.toString(), StatusCode.OK);
  }

}
