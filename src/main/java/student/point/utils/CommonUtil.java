package student.point.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public final class CommonUtil {

    public static Map<String, Object> convertObjectToMap(Object obj) {
        ObjectMapper mapper = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.convertValue(obj, Map.class);
    }

    public static Map<String, Object> convertToMap(String jsonString) throws Exception {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(jsonString, Map.class);
    }

    public static BigDecimal convertStringToBigDecimal(String data) {
        try {
            if (Strings.isBlank(data)) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(data);
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }
}
