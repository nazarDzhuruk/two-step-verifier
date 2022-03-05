package com.projectdemo.twostepverifier.service.implementation;

import com.projectdemo.twostepverifier.config.ApplicationEmailProperties;
import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.exception.ApplicationServiceException;
import com.projectdemo.twostepverifier.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

@Service
public class EmailAction implements EmailService {
    private static final String EMAIL_SUBJECT = "VERIFICATION NUMBER";
    private final ApplicationEmailProperties applicationEmailProperties;

    public EmailAction(ApplicationEmailProperties applicationEmailProperties) {
        this.applicationEmailProperties = applicationEmailProperties;
    }

    @Override
    public void sendEmail(User user) {
        try {
            Message message = applicationEmailProperties.configuredMessage();
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));

            message.setSubject(EMAIL_SUBJECT);
            message.setText(String.valueOf(user.getVerificationNumber()));
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ApplicationServiceException(e.getMessage());
        }
    }
}
