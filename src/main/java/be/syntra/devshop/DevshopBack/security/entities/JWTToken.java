package be.syntra.devshop.DevshopBack.security.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken {

    private String token;
}
