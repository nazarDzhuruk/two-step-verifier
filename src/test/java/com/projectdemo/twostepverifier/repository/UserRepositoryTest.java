package com.projectdemo.twostepverifier.repository;

import com.projectdemo.twostepverifier.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfUserEmailExist() {
        //Given
        String email = "testEmail@gmail.com";
        User user = new User("testName", email);
        underTest.save(user);

        //When
        boolean expected = underTest.selectExistEmail(email);

        //Then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfUserEmailDoesNotExist(){
        //Given
        String email = "testEmail@gmail.com";

        //When
        boolean expected = underTest.selectExistEmail(email);

        //Then
        assertThat(expected).isFalse();
    }
}