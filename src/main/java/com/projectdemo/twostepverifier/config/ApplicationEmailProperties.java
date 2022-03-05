package com.projectdemo.twostepverifier.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Configuration
public class ApplicationEmailProperties {

    private static final String HOST = "mail.smtp.host";
    private static final String PORT = "mail.smtp.port";
    private static final String AUTH = "mail.smtp.auth";
    private static final String START_TLS = "mail.smtp.starttls.enable";

    private EmailConfiguration configuration;
    @Autowired
    public ApplicationEmailProperties(EmailConfiguration configuration) {
        this.configuration = configuration;
    }

    public ApplicationEmailProperties() {
    }

    public Message configuredMessage() throws MessagingException {
        Message message = new MimeMessage(configureSession());
        message.setFrom(new InternetAddress(configuration.getMyEmail()));
        return message;
    }

    private Session configureSession() {
        return Session.getInstance(configuredProperties(),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configuration.getMyEmail(), configuration.getEmailPassword());
                    }
                });
    }


    private Properties configuredProperties() {
        Properties properties = new Properties();
        properties.put(HOST, configuration.getHost());
        properties.put(PORT, configuration.getPort());
        properties.put(AUTH, configuration.getAuth());
        properties.put(START_TLS, configuration.getStarttls());
        return properties;
    }
}
