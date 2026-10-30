package dev.gabryel.testingcontroller.service;

import dev.gabryel.testingcontroller.dto.CreateUserRequest;
import dev.gabryel.testingcontroller.entity.UserEntity;
import dev.gabryel.testingcontroller.exception.InvalidEmailException;
import dev.gabryel.testingcontroller.exception.InvalidNameException;
import dev.gabryel.testingcontroller.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private List<UserEntity> users = List.of();

    public UserEntity createUser(CreateUserRequest request) {
        if (request.name() == null || request.name().isBlank())
            throw new InvalidNameException();
        if (request.email() == null || request.email().isBlank())
            throw new InvalidEmailException();

        UserEntity userEntity = new UserEntity(
                users.size()+1,
                request.name(),
                request.email()
        );
        users.add(userEntity);
        return userEntity;
    }

    public UserEntity findUserById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("Please, enter a valid ID");

        UserEntity userEntity = users.get(id);

        if (userEntity == null)
            throw new UserNotFoundException();

        return userEntity;
    }

    public String deleteUserById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("Please, enter a valid ID");
        if (users.remove(id))
            return "User successful deleted";
        else
            throw new UserNotFoundException();
    }
}
