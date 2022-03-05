package com.projectdemo.twostepverifier.service;

import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.exception.ApplicationServiceException;
import com.projectdemo.twostepverifier.repository.UserRepository;
import com.projectdemo.twostepverifier.service.implementation.UserAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService underTest;
    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        underTest = new UserAction(repository);
    }

    @Test
    void shouldAddUserWhenInvoked() {
        // Given
        User user = new User("testName", "testEmail@gmail.com");

        // When
        underTest.addUser(user);

        // Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void addUserWillThrowAnExceptionIfEmailTaken() {
        // Given
        User user = new User("testName", "testEmail@gmail.com");
        given(repository.selectExistEmail(user.getEmail())).willReturn(true);

        // When
        assertThatThrownBy(() -> underTest.addUser(user))
                .isInstanceOf(ApplicationServiceException.class)
                .hasMessageContaining("Email " + user.getEmail() + " exist");

    }

    @Test
    void deleteUserById() {
        // Given
        User user = new User("testName", "testEmail@gmail.com");
        given(repository.findById(123)).willReturn(java.util.Optional.of(user));

        // When
        underTest.deleteUserById(123);

        // Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).delete(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void deleteUserByIdWillThrowAnExceptionIfUserNotExist() {
        int id = 123;

        assertThatThrownBy(() -> underTest.deleteUserById(id))
                .isInstanceOf(ApplicationServiceException.class)
                .hasMessageContaining("User not exist!");
    }

    @Test
    void findUserById() {
        User user = new User("testName", "testEmail");
        given(repository.findById(123)).willReturn(java.util.Optional.of(user));

        User userById = underTest.findUserById(123);

        verify(repository).findById(123);
        assertThat(user).isEqualTo(userById);
    }

    @Test
    void getAllUsers() {
        // When
        underTest.getAllUsers();
        // Then
        verify(repository).findAll();
    }
}