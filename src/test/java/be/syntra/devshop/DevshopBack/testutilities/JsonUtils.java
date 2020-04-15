package be.syntra.devshop.DevshopBack.testutilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class JsonUtils {

    private ObjectMapper mapper;

    @Autowired
    public JsonUtils(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String asJsonString(final Object object) {
        try {
            return mapper.registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Customized Objectmapper for reading values compatible with this class' other methods
     * @Return the desired object you want from a JSON
     * IMPORTANT! -your return object should be a class that has a @NoArgsConstructor-
     */
    public Object readValue(final String input, final Class<?> classToRead) {
        try {
            return mapper
                    .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                    .readValue(input, classToRead);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
