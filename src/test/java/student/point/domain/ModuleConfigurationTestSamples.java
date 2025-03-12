package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ModuleConfigurationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ModuleConfiguration getModuleConfigurationSample1() {
        return new ModuleConfiguration()
            .id(1L)
            .name("name1")
            .prefix("prefix1")
            .padding(1L)
            .numberNextActual(1L)
            .numberIncrement(1L)
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static ModuleConfiguration getModuleConfigurationSample2() {
        return new ModuleConfiguration()
            .id(2L)
            .name("name2")
            .prefix("prefix2")
            .padding(2L)
            .numberNextActual(2L)
            .numberIncrement(2L)
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static ModuleConfiguration getModuleConfigurationRandomSampleGenerator() {
        return new ModuleConfiguration()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .prefix(UUID.randomUUID().toString())
            .padding(longCount.incrementAndGet())
            .numberNextActual(longCount.incrementAndGet())
            .numberIncrement(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
