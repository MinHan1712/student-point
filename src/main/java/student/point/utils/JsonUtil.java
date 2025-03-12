package student.point.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.apache.logging.log4j.util.Strings;

public class JsonUtil {

    public static <T> T jsonToObject(String jsonString, Class<T> responseType) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(jsonString, responseType);
    }

    public static <T> String objectToJson(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                .registerModules(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            return Strings.EMPTY;
        }
    }

    public static JsonNode convertObjectToJsonNode(Object data) {
        try {
            if (data == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper()
                .registerModules(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.valueToTree(data);
        } catch (Exception ex) {
            return null;
        }
    }
}
