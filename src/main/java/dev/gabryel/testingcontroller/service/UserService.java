package dev.gabryel.testingcontroller.service;

import dev.gabryel.testingcontroller.dto.CreateUserRequest;
import dev.gabryel.testingcontroller.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private List<UserEntity> users = List.of();

    public UserEntity createUser(CreateUserRequest request) {
        UserEntity userEntity = new UserEntity(
                users.size()+1,
                request.name(),
                request.email()
        );
        users.add(userEntity);
        return userEntity;
    }
}
