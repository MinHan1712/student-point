package student.point.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public final class DateUtil {

    private DateUtil() {
    }

    public static String instantToString(Instant instant, String format) {
        if (Objects.isNull(instant) || instant.getEpochSecond() == 0) return StringUtils.EMPTY;

        return new SimpleDateFormat(format).format(Date.from(instant));
    }

    public static Instant convertTimeDate(String time) {
        try {
            // Step 2: Define the formatter for the date string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Step 3: Parse the date string to LocalDate
            LocalDate localDate = LocalDate.parse(time, formatter);

            // Step 4: Convert LocalDate to Instant by specifying a time zone
            return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } catch (Exception ex) {
            return null;
        }
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return LocalDate.now();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }
}
