package com.projectdemo.twostepverifier.service;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.projectdemo.twostepverifier.config.ApplicationEmailProperties;
import com.projectdemo.twostepverifier.config.EmailConfiguration;
import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.service.implementation.EmailAction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = EmailConfiguration.class)
class EmailServiceTest {


    private EmailService underTest;

    private MimeMessage mimeMessage;

    @BeforeEach
    void before() throws MessagingException {
        mimeMessage = new MimeMessage((Session) null);
        ApplicationEmailProperties applicationEmailProperties = mock(ApplicationEmailProperties.class);
        when(applicationEmailProperties.configuredMessage()).thenReturn(mimeMessage);
        underTest = new EmailAction(applicationEmailProperties);
    }

    @Test
    void sendEmail() throws MessagingException, IOException {
        User user = new User("test", "test@gmail.com");
        underTest.sendEmail(user);
        assertEquals(user.getEmail(),mimeMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
    }

}