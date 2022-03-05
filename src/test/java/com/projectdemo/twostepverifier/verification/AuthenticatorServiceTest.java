package com.projectdemo.twostepverifier.verification;

import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.service.EmailService;
import com.projectdemo.twostepverifier.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticatorServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;

    private AuthenticatorService underTest;

    @BeforeEach
    void setUp() {
        underTest = new Authenticator(userService, emailService);
    }

    @Test
    void checkIfVerificationNumberSetCorrectly() {
        User user = new User("testName", "testEmail@gmail.com");

        underTest.createUser(user);

        ArgumentCaptor<User> userServiceArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<User> emailServiceArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService).addUser(userServiceArgumentCaptor.capture());
        verify(emailService).sendEmail(emailServiceArgumentCaptor.capture());

        User capturedUserFromService = userServiceArgumentCaptor.getValue();
        User capturedUserFromEmail = emailServiceArgumentCaptor.getValue();

        assertThat(capturedUserFromService.getVerificationNumber())
                .isEqualTo(capturedUserFromEmail.getVerificationNumber());

    }

    @Test
    void checkIfVerificationNumberVerifiedCorrectly() {
        User user = new User("testName", "testEmail@gmail.com");
        user.setVerificationNumber(101);
        given(userService.findUserById(123)).willReturn(user);

        assertThat(underTest.verifyUser(123, 101)).isTrue();
    }
}