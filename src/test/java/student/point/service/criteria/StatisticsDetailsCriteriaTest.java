package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StatisticsDetailsCriteriaTest {

    @Test
    void newStatisticsDetailsCriteriaHasAllFiltersNullTest() {
        var statisticsDetailsCriteria = new StatisticsDetailsCriteria();
        assertThat(statisticsDetailsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void statisticsDetailsCriteriaFluentMethodsCreatesFiltersTest() {
        var statisticsDetailsCriteria = new StatisticsDetailsCriteria();

        setAllFilters(statisticsDetailsCriteria);

        assertThat(statisticsDetailsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void statisticsDetailsCriteriaCopyCreatesNullFilterTest() {
        var statisticsDetailsCriteria = new StatisticsDetailsCriteria();
        var copy = statisticsDetailsCriteria.copy();

        assertThat(statisticsDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(statisticsDetailsCriteria)
        );
    }

    @Test
    void statisticsDetailsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var statisticsDetailsCriteria = new StatisticsDetailsCriteria();
        setAllFilters(statisticsDetailsCriteria);

        var copy = statisticsDetailsCriteria.copy();

        assertThat(statisticsDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(statisticsDetailsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var statisticsDetailsCriteria = new StatisticsDetailsCriteria();

        assertThat(statisticsDetailsCriteria).hasToString("StatisticsDetailsCriteria{}");
    }

    private static void setAllFilters(StatisticsDetailsCriteria statisticsDetailsCriteria) {
        statisticsDetailsCriteria.id();
        statisticsDetailsCriteria.code();
        statisticsDetailsCriteria.totalScholarship();
        statisticsDetailsCriteria.graduationDate();
        statisticsDetailsCriteria.notes();
        statisticsDetailsCriteria.status();
        statisticsDetailsCriteria.createdBy();
        statisticsDetailsCriteria.createdDate();
        statisticsDetailsCriteria.lastModifiedBy();
        statisticsDetailsCriteria.lastModifiedDate();
        statisticsDetailsCriteria.studentId();
        statisticsDetailsCriteria.statisticsId();
        statisticsDetailsCriteria.distinct();
    }

    private static Condition<StatisticsDetailsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getTotalScholarship()) &&
                condition.apply(criteria.getGraduationDate()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId()) &&
                condition.apply(criteria.getStatisticsId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StatisticsDetailsCriteria> copyFiltersAre(
        StatisticsDetailsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getTotalScholarship(), copy.getTotalScholarship()) &&
                condition.apply(criteria.getGraduationDate(), copy.getGraduationDate()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStudentId(), copy.getStudentId()) &&
                condition.apply(criteria.getStatisticsId(), copy.getStatisticsId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
