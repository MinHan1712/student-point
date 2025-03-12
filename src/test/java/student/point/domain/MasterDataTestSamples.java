package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MasterDataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MasterData getMasterDataSample1() {
        return new MasterData()
            .id(1L)
            .key("key1")
            .code("code1")
            .name("name1")
            .description("description1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static MasterData getMasterDataSample2() {
        return new MasterData()
            .id(2L)
            .key("key2")
            .code("code2")
            .name("name2")
            .description("description2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static MasterData getMasterDataRandomSampleGenerator() {
        return new MasterData()
            .id(longCount.incrementAndGet())
            .key(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
