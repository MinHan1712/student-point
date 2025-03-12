package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassRegistrationTestSamples.*;
import static student.point.domain.ClassesTestSamples.*;
import static student.point.domain.CourseTestSamples.*;
import static student.point.domain.GradesTestSamples.*;
import static student.point.domain.TeachersTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ClassesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classes.class);
        Classes classes1 = getClassesSample1();
        Classes classes2 = new Classes();
        assertThat(classes1).isNotEqualTo(classes2);

        classes2.setId(classes1.getId());
        assertThat(classes1).isEqualTo(classes2);

        classes2 = getClassesSample2();
        assertThat(classes1).isNotEqualTo(classes2);
    }

    @Test
    void classRegistrationTest() {
        Classes classes = getClassesRandomSampleGenerator();
        ClassRegistration classRegistrationBack = getClassRegistrationRandomSampleGenerator();

        classes.addClassRegistration(classRegistrationBack);
        assertThat(classes.getClassRegistrations()).containsOnly(classRegistrationBack);
        assertThat(classRegistrationBack.getClasses()).isEqualTo(classes);

        classes.removeClassRegistration(classRegistrationBack);
        assertThat(classes.getClassRegistrations()).doesNotContain(classRegistrationBack);
        assertThat(classRegistrationBack.getClasses()).isNull();

        classes.classRegistrations(new HashSet<>(Set.of(classRegistrationBack)));
        assertThat(classes.getClassRegistrations()).containsOnly(classRegistrationBack);
        assertThat(classRegistrationBack.getClasses()).isEqualTo(classes);

        classes.setClassRegistrations(new HashSet<>());
        assertThat(classes.getClassRegistrations()).doesNotContain(classRegistrationBack);
        assertThat(classRegistrationBack.getClasses()).isNull();
    }

    @Test
    void gradesTest() {
        Classes classes = getClassesRandomSampleGenerator();
        Grades gradesBack = getGradesRandomSampleGenerator();

        classes.addGrades(gradesBack);
        assertThat(classes.getGrades()).containsOnly(gradesBack);
        assertThat(gradesBack.getClasses()).isEqualTo(classes);

        classes.removeGrades(gradesBack);
        assertThat(classes.getGrades()).doesNotContain(gradesBack);
        assertThat(gradesBack.getClasses()).isNull();

        classes.grades(new HashSet<>(Set.of(gradesBack)));
        assertThat(classes.getGrades()).containsOnly(gradesBack);
        assertThat(gradesBack.getClasses()).isEqualTo(classes);

        classes.setGrades(new HashSet<>());
        assertThat(classes.getGrades()).doesNotContain(gradesBack);
        assertThat(gradesBack.getClasses()).isNull();
    }

    @Test
    void courseTest() {
        Classes classes = getClassesRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        classes.setCourse(courseBack);
        assertThat(classes.getCourse()).isEqualTo(courseBack);

        classes.course(null);
        assertThat(classes.getCourse()).isNull();
    }

    @Test
    void teachersTest() {
        Classes classes = getClassesRandomSampleGenerator();
        Teachers teachersBack = getTeachersRandomSampleGenerator();

        classes.setTeachers(teachersBack);
        assertThat(classes.getTeachers()).isEqualTo(teachersBack);

        classes.teachers(null);
        assertThat(classes.getTeachers()).isNull();
    }
}
