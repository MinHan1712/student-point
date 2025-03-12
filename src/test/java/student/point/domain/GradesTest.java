package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassesTestSamples.*;
import static student.point.domain.GradesTestSamples.*;
import static student.point.domain.StudentTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class GradesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grades.class);
        Grades grades1 = getGradesSample1();
        Grades grades2 = new Grades();
        assertThat(grades1).isNotEqualTo(grades2);

        grades2.setId(grades1.getId());
        assertThat(grades1).isEqualTo(grades2);

        grades2 = getGradesSample2();
        assertThat(grades1).isNotEqualTo(grades2);
    }

    @Test
    void studentTest() {
        Grades grades = getGradesRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        grades.setStudent(studentBack);
        assertThat(grades.getStudent()).isEqualTo(studentBack);

        grades.student(null);
        assertThat(grades.getStudent()).isNull();
    }

    @Test
    void classesTest() {
        Grades grades = getGradesRandomSampleGenerator();
        Classes classesBack = getClassesRandomSampleGenerator();

        grades.setClasses(classesBack);
        assertThat(grades.getClasses()).isEqualTo(classesBack);

        grades.classes(null);
        assertThat(grades.getClasses()).isNull();
    }
}
