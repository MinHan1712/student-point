package student.point.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CourseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Course getCourseSample1() {
        return new Course()
            .id(1L)
            .courseCode("courseCode1")
            .courseTitle("courseTitle1")
            .lecture(1)
            .tutorialDiscussion(1)
            .practical(1)
            .laboratory(1)
            .selfStudy(1)
            .numberOfSessions(1)
            .notes("notes1")
            .semester("semester1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Course getCourseSample2() {
        return new Course()
            .id(2L)
            .courseCode("courseCode2")
            .courseTitle("courseTitle2")
            .lecture(2)
            .tutorialDiscussion(2)
            .practical(2)
            .laboratory(2)
            .selfStudy(2)
            .numberOfSessions(2)
            .notes("notes2")
            .semester("semester2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Course getCourseRandomSampleGenerator() {
        return new Course()
            .id(longCount.incrementAndGet())
            .courseCode(UUID.randomUUID().toString())
            .courseTitle(UUID.randomUUID().toString())
            .lecture(intCount.incrementAndGet())
            .tutorialDiscussion(intCount.incrementAndGet())
            .practical(intCount.incrementAndGet())
            .laboratory(intCount.incrementAndGet())
            .selfStudy(intCount.incrementAndGet())
            .numberOfSessions(intCount.incrementAndGet())
            .notes(UUID.randomUUID().toString())
            .semester(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
