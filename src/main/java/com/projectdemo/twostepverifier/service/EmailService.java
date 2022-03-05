package com.projectdemo.twostepverifier.service;

import com.projectdemo.twostepverifier.domain.User;

public interface EmailService {
    void sendEmail(User user);
}
