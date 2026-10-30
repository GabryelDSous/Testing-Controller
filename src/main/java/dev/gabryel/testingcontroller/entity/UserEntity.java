package dev.gabryel.testingcontroller.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
    private int id;
    private String name;
    private String email;
}
