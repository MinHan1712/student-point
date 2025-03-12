package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GradesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Grades getGradesSample1() {
        return new Grades()
            .id(1L)
            .gradesCode("gradesCode1")
            .credit(1)
            .studyAttempt(1)
            .examAttempt(1)
            .notes("notes1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Grades getGradesSample2() {
        return new Grades()
            .id(2L)
            .gradesCode("gradesCode2")
            .credit(2)
            .studyAttempt(2)
            .examAttempt(2)
            .notes("notes2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Grades getGradesRandomSampleGenerator() {
        return new Grades()
            .id(longCount.incrementAndGet())
            .gradesCode(UUID.randomUUID().toString())
            .credit(intCount.incrementAndGet())
            .studyAttempt(intCount.incrementAndGet())
            .examAttempt(intCount.incrementAndGet())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
