package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StatisticsCriteriaTest {

    @Test
    void newStatisticsCriteriaHasAllFiltersNullTest() {
        var statisticsCriteria = new StatisticsCriteria();
        assertThat(statisticsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void statisticsCriteriaFluentMethodsCreatesFiltersTest() {
        var statisticsCriteria = new StatisticsCriteria();

        setAllFilters(statisticsCriteria);

        assertThat(statisticsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void statisticsCriteriaCopyCreatesNullFilterTest() {
        var statisticsCriteria = new StatisticsCriteria();
        var copy = statisticsCriteria.copy();

        assertThat(statisticsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(statisticsCriteria)
        );
    }

    @Test
    void statisticsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var statisticsCriteria = new StatisticsCriteria();
        setAllFilters(statisticsCriteria);

        var copy = statisticsCriteria.copy();

        assertThat(statisticsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(statisticsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var statisticsCriteria = new StatisticsCriteria();

        assertThat(statisticsCriteria).hasToString("StatisticsCriteria{}");
    }

    private static void setAllFilters(StatisticsCriteria statisticsCriteria) {
        statisticsCriteria.id();
        statisticsCriteria.statisticsCode();
        statisticsCriteria.academicYear();
        statisticsCriteria.type();
        statisticsCriteria.notes();
        statisticsCriteria.status();
        statisticsCriteria.createdBy();
        statisticsCriteria.createdDate();
        statisticsCriteria.lastModifiedBy();
        statisticsCriteria.lastModifiedDate();
        statisticsCriteria.statisticsDetailsId();
        statisticsCriteria.distinct();
    }

    private static Condition<StatisticsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStatisticsCode()) &&
                condition.apply(criteria.getAcademicYear()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStatisticsDetailsId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StatisticsCriteria> copyFiltersAre(StatisticsCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStatisticsCode(), copy.getStatisticsCode()) &&
                condition.apply(criteria.getAcademicYear(), copy.getAcademicYear()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStatisticsDetailsId(), copy.getStatisticsDetailsId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
