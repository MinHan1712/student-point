package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StudentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Student getStudentSample1() {
        return new Student()
            .id(1L)
            .studentCode("studentCode1")
            .fullName("fullName1")
            .address("address1")
            .phoneNumber("phoneNumber1")
            .email("email1")
            .notes("notes1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Student getStudentSample2() {
        return new Student()
            .id(2L)
            .studentCode("studentCode2")
            .fullName("fullName2")
            .address("address2")
            .phoneNumber("phoneNumber2")
            .email("email2")
            .notes("notes2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Student getStudentRandomSampleGenerator() {
        return new Student()
            .id(longCount.incrementAndGet())
            .studentCode(UUID.randomUUID().toString())
            .fullName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
