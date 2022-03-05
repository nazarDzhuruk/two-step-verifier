package com.projectdemo.twostepverifier.verification;

import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.exception.ApplicationAuthException;
import com.projectdemo.twostepverifier.service.EmailService;
import com.projectdemo.twostepverifier.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Authenticator implements AuthenticatorService {
    private static final String EXPECTED_EXCEPTION = "Bad verification number";
    private final UserService userService;
    private final EmailService emailService;
    private final Random random = new Random();

    public Authenticator(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void createUser(User user) {
        int randomNumber = verificationNumber();
        user.setVerificationNumber(randomNumber);
        user.setAccountStatus(false);
        userService.addUser(user);
        emailService.sendEmail(user);
    }

    @Override
    public boolean verifyUser(int userId, int verificationNumber) {
        User userFromDatabase = userService.findUserById(userId);
        if (userFromDatabase.getVerificationNumber() == verificationNumber) {
            userFromDatabase.setAccountStatus(true);
            userService.addUser(userFromDatabase);
            return true;
        } else throw new ApplicationAuthException(EXPECTED_EXCEPTION);
    }

    private int verificationNumber() {
        int maxValue = 9999;
        return this.random.nextInt(maxValue - 1);
    }
}
