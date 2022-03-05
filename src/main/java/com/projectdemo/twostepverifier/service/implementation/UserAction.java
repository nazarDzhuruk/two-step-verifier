package com.projectdemo.twostepverifier.service.implementation;

import com.projectdemo.twostepverifier.domain.User;
import com.projectdemo.twostepverifier.exception.ApplicationServiceException;
import com.projectdemo.twostepverifier.repository.UserRepository;
import com.projectdemo.twostepverifier.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAction implements UserService {
    private static final String EXPECTED_EXCEPTION = "User not exist!";
    private final UserRepository repository;

    public UserAction(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User addUser(User user) {
        Boolean emailExist = repository.selectExistEmail(user.getEmail());
        if (emailExist)
            throw new ApplicationServiceException("Email " + user.getEmail() + " exist");
        else repository.save(user);
        return user;
    }

    @Override
    public User deleteUserById(int userId) {
        User user = repository.findById(userId).orElseThrow(() -> new ApplicationServiceException(EXPECTED_EXCEPTION));
        repository.delete(user);
        return user;
    }

    @Override
    public User findUserById(int userId) {
        return repository.findById(userId).orElseThrow(() -> new ApplicationServiceException(EXPECTED_EXCEPTION));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
