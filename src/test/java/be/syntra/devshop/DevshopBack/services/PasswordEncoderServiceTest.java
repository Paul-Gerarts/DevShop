package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.security.services.PasswordEncoderService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderServiceTest {

    private PasswordEncoderService passwordEncoderService = new PasswordEncoderService();

    @Test
    void canEncodePasswordTest() {
        // given
        String plainPassword = "test";

        // when
        String resultPassword = passwordEncoderService.encode(plainPassword);

        // given
        assertThat(plainPassword).isNotEqualTo(resultPassword);
    }
}
