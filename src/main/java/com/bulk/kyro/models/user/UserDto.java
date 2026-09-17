package com.bulk.kyro.models.user;

import com.bulk.kyro.entities.UserEntity;
import com.bulk.kyro.enums.UserRole;

public record UserDto(
        Long id,
        String username,
        String password,
        UserRole role
) {

    public static UserDto fromEntity(UserEntity u) {
        return new UserDto(
                u.getId(),
                u.getUsername(),
                u.getPassword(),
                u.getRole()
        );
    }
}
