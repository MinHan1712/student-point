package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.CourseTestSamples.*;
import static student.point.domain.FacultiesTestSamples.*;
import static student.point.domain.TeachersTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class FacultiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Faculties.class);
        Faculties faculties1 = getFacultiesSample1();
        Faculties faculties2 = new Faculties();
        assertThat(faculties1).isNotEqualTo(faculties2);

        faculties2.setId(faculties1.getId());
        assertThat(faculties1).isEqualTo(faculties2);

        faculties2 = getFacultiesSample2();
        assertThat(faculties1).isNotEqualTo(faculties2);
    }

    @Test
    void teachersTest() {
        Faculties faculties = getFacultiesRandomSampleGenerator();
        Teachers teachersBack = getTeachersRandomSampleGenerator();

        faculties.addTeachers(teachersBack);
        assertThat(faculties.getTeachers()).containsOnly(teachersBack);
        assertThat(teachersBack.getFaculties()).isEqualTo(faculties);

        faculties.removeTeachers(teachersBack);
        assertThat(faculties.getTeachers()).doesNotContain(teachersBack);
        assertThat(teachersBack.getFaculties()).isNull();

        faculties.teachers(new HashSet<>(Set.of(teachersBack)));
        assertThat(faculties.getTeachers()).containsOnly(teachersBack);
        assertThat(teachersBack.getFaculties()).isEqualTo(faculties);

        faculties.setTeachers(new HashSet<>());
        assertThat(faculties.getTeachers()).doesNotContain(teachersBack);
        assertThat(teachersBack.getFaculties()).isNull();
    }

    @Test
    void courseTest() {
        Faculties faculties = getFacultiesRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        faculties.setCourse(courseBack);
        assertThat(faculties.getCourse()).isEqualTo(courseBack);

        faculties.course(null);
        assertThat(faculties.getCourse()).isNull();
    }
}
