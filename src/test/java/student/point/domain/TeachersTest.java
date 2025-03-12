package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassesTestSamples.*;
import static student.point.domain.FacultiesTestSamples.*;
import static student.point.domain.TeachersTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class TeachersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teachers.class);
        Teachers teachers1 = getTeachersSample1();
        Teachers teachers2 = new Teachers();
        assertThat(teachers1).isNotEqualTo(teachers2);

        teachers2.setId(teachers1.getId());
        assertThat(teachers1).isEqualTo(teachers2);

        teachers2 = getTeachersSample2();
        assertThat(teachers1).isNotEqualTo(teachers2);
    }

    @Test
    void classesTest() {
        Teachers teachers = getTeachersRandomSampleGenerator();
        Classes classesBack = getClassesRandomSampleGenerator();

        teachers.addClasses(classesBack);
        assertThat(teachers.getClasses()).containsOnly(classesBack);
        assertThat(classesBack.getTeachers()).isEqualTo(teachers);

        teachers.removeClasses(classesBack);
        assertThat(teachers.getClasses()).doesNotContain(classesBack);
        assertThat(classesBack.getTeachers()).isNull();

        teachers.classes(new HashSet<>(Set.of(classesBack)));
        assertThat(teachers.getClasses()).containsOnly(classesBack);
        assertThat(classesBack.getTeachers()).isEqualTo(teachers);

        teachers.setClasses(new HashSet<>());
        assertThat(teachers.getClasses()).doesNotContain(classesBack);
        assertThat(classesBack.getTeachers()).isNull();
    }

    @Test
    void facultiesTest() {
        Teachers teachers = getTeachersRandomSampleGenerator();
        Faculties facultiesBack = getFacultiesRandomSampleGenerator();

        teachers.setFaculties(facultiesBack);
        assertThat(teachers.getFaculties()).isEqualTo(facultiesBack);

        teachers.faculties(null);
        assertThat(teachers.getFaculties()).isNull();
    }
}
