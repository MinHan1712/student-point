package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ClassRegistrationTestSamples.*;
import static student.point.domain.ConductScoresTestSamples.*;
import static student.point.domain.GradesTestSamples.*;
import static student.point.domain.StatisticsDetailsTestSamples.*;
import static student.point.domain.StudentTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class StudentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = getStudentSample1();
        Student student2 = new Student();
        assertThat(student1).isNotEqualTo(student2);

        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);

        student2 = getStudentSample2();
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    void classRegistrationTest() {
        Student student = getStudentRandomSampleGenerator();
        ClassRegistration classRegistrationBack = getClassRegistrationRandomSampleGenerator();

        student.addClassRegistration(classRegistrationBack);
        assertThat(student.getClassRegistrations()).containsOnly(classRegistrationBack);
        assertThat(classRegistrationBack.getStudent()).isEqualTo(student);

        student.removeClassRegistration(classRegistrationBack);
        assertThat(student.getClassRegistrations()).doesNotContain(classRegistrationBack);
        assertThat(classRegistrationBack.getStudent()).isNull();

        student.classRegistrations(new HashSet<>(Set.of(classRegistrationBack)));
        assertThat(student.getClassRegistrations()).containsOnly(classRegistrationBack);
        assertThat(classRegistrationBack.getStudent()).isEqualTo(student);

        student.setClassRegistrations(new HashSet<>());
        assertThat(student.getClassRegistrations()).doesNotContain(classRegistrationBack);
        assertThat(classRegistrationBack.getStudent()).isNull();
    }

    @Test
    void conductScoresTest() {
        Student student = getStudentRandomSampleGenerator();
        ConductScores conductScoresBack = getConductScoresRandomSampleGenerator();

        student.addConductScores(conductScoresBack);
        assertThat(student.getConductScores()).containsOnly(conductScoresBack);
        assertThat(conductScoresBack.getStudent()).isEqualTo(student);

        student.removeConductScores(conductScoresBack);
        assertThat(student.getConductScores()).doesNotContain(conductScoresBack);
        assertThat(conductScoresBack.getStudent()).isNull();

        student.conductScores(new HashSet<>(Set.of(conductScoresBack)));
        assertThat(student.getConductScores()).containsOnly(conductScoresBack);
        assertThat(conductScoresBack.getStudent()).isEqualTo(student);

        student.setConductScores(new HashSet<>());
        assertThat(student.getConductScores()).doesNotContain(conductScoresBack);
        assertThat(conductScoresBack.getStudent()).isNull();
    }

    @Test
    void statisticsDetailsTest() {
        Student student = getStudentRandomSampleGenerator();
        StatisticsDetails statisticsDetailsBack = getStatisticsDetailsRandomSampleGenerator();

        student.addStatisticsDetails(statisticsDetailsBack);
        assertThat(student.getStatisticsDetails()).containsOnly(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStudent()).isEqualTo(student);

        student.removeStatisticsDetails(statisticsDetailsBack);
        assertThat(student.getStatisticsDetails()).doesNotContain(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStudent()).isNull();

        student.statisticsDetails(new HashSet<>(Set.of(statisticsDetailsBack)));
        assertThat(student.getStatisticsDetails()).containsOnly(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStudent()).isEqualTo(student);

        student.setStatisticsDetails(new HashSet<>());
        assertThat(student.getStatisticsDetails()).doesNotContain(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStudent()).isNull();
    }

    @Test
    void gradesTest() {
        Student student = getStudentRandomSampleGenerator();
        Grades gradesBack = getGradesRandomSampleGenerator();

        student.addGrades(gradesBack);
        assertThat(student.getGrades()).containsOnly(gradesBack);
        assertThat(gradesBack.getStudent()).isEqualTo(student);

        student.removeGrades(gradesBack);
        assertThat(student.getGrades()).doesNotContain(gradesBack);
        assertThat(gradesBack.getStudent()).isNull();

        student.grades(new HashSet<>(Set.of(gradesBack)));
        assertThat(student.getGrades()).containsOnly(gradesBack);
        assertThat(gradesBack.getStudent()).isEqualTo(student);

        student.setGrades(new HashSet<>());
        assertThat(student.getGrades()).doesNotContain(gradesBack);
        assertThat(gradesBack.getStudent()).isNull();
    }
}
