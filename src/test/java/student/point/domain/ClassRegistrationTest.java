package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassRegistrationTestSamples.*;
import static student.point.domain.ClassesTestSamples.*;
import static student.point.domain.StudentTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ClassRegistrationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRegistration.class);
        ClassRegistration classRegistration1 = getClassRegistrationSample1();
        ClassRegistration classRegistration2 = new ClassRegistration();
        assertThat(classRegistration1).isNotEqualTo(classRegistration2);

        classRegistration2.setId(classRegistration1.getId());
        assertThat(classRegistration1).isEqualTo(classRegistration2);

        classRegistration2 = getClassRegistrationSample2();
        assertThat(classRegistration1).isNotEqualTo(classRegistration2);
    }

    @Test
    void studentTest() {
        ClassRegistration classRegistration = getClassRegistrationRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        classRegistration.setStudent(studentBack);
        assertThat(classRegistration.getStudent()).isEqualTo(studentBack);

        classRegistration.student(null);
        assertThat(classRegistration.getStudent()).isNull();
    }

    @Test
    void classesTest() {
        ClassRegistration classRegistration = getClassRegistrationRandomSampleGenerator();
        Classes classesBack = getClassesRandomSampleGenerator();

        classRegistration.setClasses(classesBack);
        assertThat(classRegistration.getClasses()).isEqualTo(classesBack);

        classRegistration.classes(null);
        assertThat(classRegistration.getClasses()).isNull();
    }
}
