package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FacultiesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Faculties getFacultiesSample1() {
        return new Faculties()
            .id(1L)
            .facultyCode("facultyCode1")
            .facultyName("facultyName1")
            .description("description1")
            .phoneNumber("phoneNumber1")
            .location("location1")
            .notes("notes1")
            .parentId(1L);
    }

    public static Faculties getFacultiesSample2() {
        return new Faculties()
            .id(2L)
            .facultyCode("facultyCode2")
            .facultyName("facultyName2")
            .description("description2")
            .phoneNumber("phoneNumber2")
            .location("location2")
            .notes("notes2")
            .parentId(2L);
    }

    public static Faculties getFacultiesRandomSampleGenerator() {
        return new Faculties()
            .id(longCount.incrementAndGet())
            .facultyCode(UUID.randomUUID().toString())
            .facultyName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .parentId(longCount.incrementAndGet());
    }
}
