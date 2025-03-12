package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StatisticsDetails getStatisticsDetailsSample1() {
        return new StatisticsDetails().id(1L).code("code1").notes("notes1").createdBy("createdBy1").lastModifiedBy("lastModifiedBy1");
    }

    public static StatisticsDetails getStatisticsDetailsSample2() {
        return new StatisticsDetails().id(2L).code("code2").notes("notes2").createdBy("createdBy2").lastModifiedBy("lastModifiedBy2");
    }

    public static StatisticsDetails getStatisticsDetailsRandomSampleGenerator() {
        return new StatisticsDetails()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
