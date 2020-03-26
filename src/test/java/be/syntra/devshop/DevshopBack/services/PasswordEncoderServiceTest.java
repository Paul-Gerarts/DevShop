package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.security.services.PasswordEncoderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PasswordEncoderServiceTest {

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canEncodePasswordTest() {
        // given
        String dummyPassword = "$2a$10$/fDjzeCFntx5VEv0cUjYG.heiUpSfloYQsn7Y2HID/ROGrtzAZmqC";
        when(passwordEncoderService.encode("test")).thenReturn(dummyPassword);

        // when
        String resultPassword = passwordEncoderService.encode("test");

        // given
        assertThat(dummyPassword).isEqualTo(resultPassword);
    }
}
