package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ConductScoresCriteriaTest {

    @Test
    void newConductScoresCriteriaHasAllFiltersNullTest() {
        var conductScoresCriteria = new ConductScoresCriteria();
        assertThat(conductScoresCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void conductScoresCriteriaFluentMethodsCreatesFiltersTest() {
        var conductScoresCriteria = new ConductScoresCriteria();

        setAllFilters(conductScoresCriteria);

        assertThat(conductScoresCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void conductScoresCriteriaCopyCreatesNullFilterTest() {
        var conductScoresCriteria = new ConductScoresCriteria();
        var copy = conductScoresCriteria.copy();

        assertThat(conductScoresCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(conductScoresCriteria)
        );
    }

    @Test
    void conductScoresCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var conductScoresCriteria = new ConductScoresCriteria();
        setAllFilters(conductScoresCriteria);

        var copy = conductScoresCriteria.copy();

        assertThat(conductScoresCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(conductScoresCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var conductScoresCriteria = new ConductScoresCriteria();

        assertThat(conductScoresCriteria).hasToString("ConductScoresCriteria{}");
    }

    private static void setAllFilters(ConductScoresCriteria conductScoresCriteria) {
        conductScoresCriteria.id();
        conductScoresCriteria.conductScoresCode();
        conductScoresCriteria.academicYear();
        conductScoresCriteria.score();
        conductScoresCriteria.classification();
        conductScoresCriteria.evaluation();
        conductScoresCriteria.createdBy();
        conductScoresCriteria.createdDate();
        conductScoresCriteria.lastModifiedBy();
        conductScoresCriteria.lastModifiedDate();
        conductScoresCriteria.studentId();
        conductScoresCriteria.distinct();
    }

    private static Condition<ConductScoresCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getConductScoresCode()) &&
                condition.apply(criteria.getAcademicYear()) &&
                condition.apply(criteria.getScore()) &&
                condition.apply(criteria.getClassification()) &&
                condition.apply(criteria.getEvaluation()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ConductScoresCriteria> copyFiltersAre(
        ConductScoresCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getConductScoresCode(), copy.getConductScoresCode()) &&
                condition.apply(criteria.getAcademicYear(), copy.getAcademicYear()) &&
                condition.apply(criteria.getScore(), copy.getScore()) &&
                condition.apply(criteria.getClassification(), copy.getClassification()) &&
                condition.apply(criteria.getEvaluation(), copy.getEvaluation()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId(), copy.getStudentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
