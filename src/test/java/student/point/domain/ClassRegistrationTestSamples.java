package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClassRegistrationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ClassRegistration getClassRegistrationSample1() {
        return new ClassRegistration().id(1L).remarks("remarks1");
    }

    public static ClassRegistration getClassRegistrationSample2() {
        return new ClassRegistration().id(2L).remarks("remarks2");
    }

    public static ClassRegistration getClassRegistrationRandomSampleGenerator() {
        return new ClassRegistration().id(longCount.incrementAndGet()).remarks(UUID.randomUUID().toString());
    }
}
