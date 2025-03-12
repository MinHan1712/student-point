package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassesTestSamples.*;
import static student.point.domain.CourseTestSamples.*;
import static student.point.domain.FacultiesTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class CourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = getCourseSample1();
        Course course2 = new Course();
        assertThat(course1).isNotEqualTo(course2);

        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);

        course2 = getCourseSample2();
        assertThat(course1).isNotEqualTo(course2);
    }

    @Test
    void classesTest() {
        Course course = getCourseRandomSampleGenerator();
        Classes classesBack = getClassesRandomSampleGenerator();

        course.addClasses(classesBack);
        assertThat(course.getClasses()).containsOnly(classesBack);
        assertThat(classesBack.getCourse()).isEqualTo(course);

        course.removeClasses(classesBack);
        assertThat(course.getClasses()).doesNotContain(classesBack);
        assertThat(classesBack.getCourse()).isNull();

        course.classes(new HashSet<>(Set.of(classesBack)));
        assertThat(course.getClasses()).containsOnly(classesBack);
        assertThat(classesBack.getCourse()).isEqualTo(course);

        course.setClasses(new HashSet<>());
        assertThat(course.getClasses()).doesNotContain(classesBack);
        assertThat(classesBack.getCourse()).isNull();
    }

    @Test
    void facultiesTest() {
        Course course = getCourseRandomSampleGenerator();
        Faculties facultiesBack = getFacultiesRandomSampleGenerator();

        course.addFaculties(facultiesBack);
        assertThat(course.getFaculties()).containsOnly(facultiesBack);
        assertThat(facultiesBack.getCourse()).isEqualTo(course);

        course.removeFaculties(facultiesBack);
        assertThat(course.getFaculties()).doesNotContain(facultiesBack);
        assertThat(facultiesBack.getCourse()).isNull();

        course.faculties(new HashSet<>(Set.of(facultiesBack)));
        assertThat(course.getFaculties()).containsOnly(facultiesBack);
        assertThat(facultiesBack.getCourse()).isEqualTo(course);

        course.setFaculties(new HashSet<>());
        assertThat(course.getFaculties()).doesNotContain(facultiesBack);
        assertThat(facultiesBack.getCourse()).isNull();
    }
}
