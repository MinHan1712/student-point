package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class GradesCriteriaTest {

    @Test
    void newGradesCriteriaHasAllFiltersNullTest() {
        var gradesCriteria = new GradesCriteria();
        assertThat(gradesCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void gradesCriteriaFluentMethodsCreatesFiltersTest() {
        var gradesCriteria = new GradesCriteria();

        setAllFilters(gradesCriteria);

        assertThat(gradesCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void gradesCriteriaCopyCreatesNullFilterTest() {
        var gradesCriteria = new GradesCriteria();
        var copy = gradesCriteria.copy();

        assertThat(gradesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(gradesCriteria)
        );
    }

    @Test
    void gradesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var gradesCriteria = new GradesCriteria();
        setAllFilters(gradesCriteria);

        var copy = gradesCriteria.copy();

        assertThat(gradesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(gradesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var gradesCriteria = new GradesCriteria();

        assertThat(gradesCriteria).hasToString("GradesCriteria{}");
    }

    private static void setAllFilters(GradesCriteria gradesCriteria) {
        gradesCriteria.id();
        gradesCriteria.gradesCode();
        gradesCriteria.credit();
        gradesCriteria.studyAttempt();
        gradesCriteria.examAttempt();
        gradesCriteria.processScore();
        gradesCriteria.examScore();
        gradesCriteria.score10();
        gradesCriteria.score4();
        gradesCriteria.letterGrade();
        gradesCriteria.evaluation();
        gradesCriteria.notes();
        gradesCriteria.status();
        gradesCriteria.createdBy();
        gradesCriteria.createdDate();
        gradesCriteria.lastModifiedBy();
        gradesCriteria.lastModifiedDate();
        gradesCriteria.studentId();
        gradesCriteria.classesId();
        gradesCriteria.distinct();
    }

    private static Condition<GradesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getGradesCode()) &&
                condition.apply(criteria.getCredit()) &&
                condition.apply(criteria.getStudyAttempt()) &&
                condition.apply(criteria.getExamAttempt()) &&
                condition.apply(criteria.getProcessScore()) &&
                condition.apply(criteria.getExamScore()) &&
                condition.apply(criteria.getScore10()) &&
                condition.apply(criteria.getScore4()) &&
                condition.apply(criteria.getLetterGrade()) &&
                condition.apply(criteria.getEvaluation()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId()) &&
                condition.apply(criteria.getClassesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<GradesCriteria> copyFiltersAre(GradesCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getGradesCode(), copy.getGradesCode()) &&
                condition.apply(criteria.getCredit(), copy.getCredit()) &&
                condition.apply(criteria.getStudyAttempt(), copy.getStudyAttempt()) &&
                condition.apply(criteria.getExamAttempt(), copy.getExamAttempt()) &&
                condition.apply(criteria.getProcessScore(), copy.getProcessScore()) &&
                condition.apply(criteria.getExamScore(), copy.getExamScore()) &&
                condition.apply(criteria.getScore10(), copy.getScore10()) &&
                condition.apply(criteria.getScore4(), copy.getScore4()) &&
                condition.apply(criteria.getLetterGrade(), copy.getLetterGrade()) &&
                condition.apply(criteria.getEvaluation(), copy.getEvaluation()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId(), copy.getStudentId()) &&
                condition.apply(criteria.getClassesId(), copy.getClassesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
