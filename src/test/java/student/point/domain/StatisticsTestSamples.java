package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Statistics getStatisticsSample1() {
        return new Statistics()
            .id(1L)
            .statisticsCode("statisticsCode1")
            .academicYear("academicYear1")
            .notes("notes1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Statistics getStatisticsSample2() {
        return new Statistics()
            .id(2L)
            .statisticsCode("statisticsCode2")
            .academicYear("academicYear2")
            .notes("notes2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Statistics getStatisticsRandomSampleGenerator() {
        return new Statistics()
            .id(longCount.incrementAndGet())
            .statisticsCode(UUID.randomUUID().toString())
            .academicYear(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
