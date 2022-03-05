package com.projectdemo.twostepverifier.verification;

import com.projectdemo.twostepverifier.domain.User;

public interface AuthenticatorService {
    void createUser(User user);
    boolean verifyUser(int userId, int verificationNumber);
}
