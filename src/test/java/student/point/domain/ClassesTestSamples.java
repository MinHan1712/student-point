package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClassesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Classes getClassesSample1() {
        return new Classes()
            .id(1L)
            .classCode("classCode1")
            .className("className1")
            .classroom("classroom1")
            .credits(1)
            .numberOfSessions(1)
            .totalNumberOfStudents(1)
            .notes("notes1")
            .description("description1")
            .academicYear("academicYear1")
            .parentId(1L)
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Classes getClassesSample2() {
        return new Classes()
            .id(2L)
            .classCode("classCode2")
            .className("className2")
            .classroom("classroom2")
            .credits(2)
            .numberOfSessions(2)
            .totalNumberOfStudents(2)
            .notes("notes2")
            .description("description2")
            .academicYear("academicYear2")
            .parentId(2L)
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Classes getClassesRandomSampleGenerator() {
        return new Classes()
            .id(longCount.incrementAndGet())
            .classCode(UUID.randomUUID().toString())
            .className(UUID.randomUUID().toString())
            .classroom(UUID.randomUUID().toString())
            .credits(intCount.incrementAndGet())
            .numberOfSessions(intCount.incrementAndGet())
            .totalNumberOfStudents(intCount.incrementAndGet())
            .notes(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .academicYear(UUID.randomUUID().toString())
            .parentId(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
