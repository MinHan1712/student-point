package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConductScoresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ConductScores getConductScoresSample1() {
        return new ConductScores()
            .id(1L)
            .conductScoresCode("conductScoresCode1")
            .academicYear("academicYear1")
            .score(1)
            .classification(1)
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static ConductScores getConductScoresSample2() {
        return new ConductScores()
            .id(2L)
            .conductScoresCode("conductScoresCode2")
            .academicYear("academicYear2")
            .score(2)
            .classification(2)
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static ConductScores getConductScoresRandomSampleGenerator() {
        return new ConductScores()
            .id(longCount.incrementAndGet())
            .conductScoresCode(UUID.randomUUID().toString())
            .academicYear(UUID.randomUUID().toString())
            .score(intCount.incrementAndGet())
            .classification(intCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
