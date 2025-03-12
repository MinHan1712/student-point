package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TeachersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Teachers getTeachersSample1() {
        return new Teachers()
            .id(1L)
            .teachersCode("teachersCode1")
            .name("name1")
            .email("email1")
            .phoneNumber("phoneNumber1")
            .notes("notes1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Teachers getTeachersSample2() {
        return new Teachers()
            .id(2L)
            .teachersCode("teachersCode2")
            .name("name2")
            .email("email2")
            .phoneNumber("phoneNumber2")
            .notes("notes2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Teachers getTeachersRandomSampleGenerator() {
        return new Teachers()
            .id(longCount.incrementAndGet())
            .teachersCode(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
