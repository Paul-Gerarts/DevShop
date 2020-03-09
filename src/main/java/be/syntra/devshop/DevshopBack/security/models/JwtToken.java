package be.syntra.devshop.DevshopBack.security.models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class JwtToken {

    private final String token;
}
