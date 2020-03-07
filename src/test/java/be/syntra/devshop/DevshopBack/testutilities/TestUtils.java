package be.syntra.devshop.DevshopBack.testutilities;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
