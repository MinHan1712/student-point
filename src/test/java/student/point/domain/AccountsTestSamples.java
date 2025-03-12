package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AccountsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Accounts getAccountsSample1() {
        return new Accounts()
            .id(1L)
            .accountNumber("accountNumber1")
            .login("login1")
            .password("password1")
            .mail("mail1")
            .phone("phone1")
            .notes("notes1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Accounts getAccountsSample2() {
        return new Accounts()
            .id(2L)
            .accountNumber("accountNumber2")
            .login("login2")
            .password("password2")
            .mail("mail2")
            .phone("phone2")
            .notes("notes2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Accounts getAccountsRandomSampleGenerator() {
        return new Accounts()
            .id(longCount.incrementAndGet())
            .accountNumber(UUID.randomUUID().toString())
            .login(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .mail(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
