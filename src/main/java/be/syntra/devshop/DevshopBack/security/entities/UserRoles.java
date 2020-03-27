package be.syntra.devshop.DevshopBack.security.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoles {

    ROLE_USER,
    ROLE_ADMIN;

}
