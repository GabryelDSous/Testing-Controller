package dev.gabryel.testingcontroller.usertest;

import dev.gabryel.testingcontroller.controller.UserController;
import dev.gabryel.testingcontroller.dto.CreateUserRequest;
import dev.gabryel.testingcontroller.entity.UserEntity;
import dev.gabryel.testingcontroller.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Test
    void shouldReturnCreatedStatusWhenCreateUser() throws Exception {
        int id = 1;
        String name = "Gabryel";
        String email = "gabryel@gmail.com";

        UserEntity user = new UserEntity(id, name, email);
        when(service.createUser(any(CreateUserRequest.class)))
                .thenReturn(user);

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name":"%s", "email":"%s"
                                }
                                """.formatted(name, email))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email));
    }

    @ParameterizedTest
    @MethodSource("invalidUserData")
    void shouldReturnBadRequestWhenCreateUserWithInvalidData(String name, String email) throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name":"%s", "email":"%s"
                                }
                                """.formatted(name, email)
                        )
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name or Email invalid. Enter a valid value"));

        verifyNoInteractions(service);
    }

    private static Stream<Arguments> invalidUserData() {
        return Stream.of(
                Arguments.of("Valid Name", ""),
                Arguments.of("Valid Name", "  "),
                Arguments.of("Valid Name", "invalidEmail"),
                Arguments.of("", "validEmail@gmail.com"),
                Arguments.of("  ", "validEmail@gmail.com")
        );
    }
}
