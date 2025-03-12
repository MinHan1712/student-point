package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MasterDataCriteriaTest {

    @Test
    void newMasterDataCriteriaHasAllFiltersNullTest() {
        var masterDataCriteria = new MasterDataCriteria();
        assertThat(masterDataCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void masterDataCriteriaFluentMethodsCreatesFiltersTest() {
        var masterDataCriteria = new MasterDataCriteria();

        setAllFilters(masterDataCriteria);

        assertThat(masterDataCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void masterDataCriteriaCopyCreatesNullFilterTest() {
        var masterDataCriteria = new MasterDataCriteria();
        var copy = masterDataCriteria.copy();

        assertThat(masterDataCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(masterDataCriteria)
        );
    }

    @Test
    void masterDataCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var masterDataCriteria = new MasterDataCriteria();
        setAllFilters(masterDataCriteria);

        var copy = masterDataCriteria.copy();

        assertThat(masterDataCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(masterDataCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var masterDataCriteria = new MasterDataCriteria();

        assertThat(masterDataCriteria).hasToString("MasterDataCriteria{}");
    }

    private static void setAllFilters(MasterDataCriteria masterDataCriteria) {
        masterDataCriteria.id();
        masterDataCriteria.key();
        masterDataCriteria.code();
        masterDataCriteria.name();
        masterDataCriteria.description();
        masterDataCriteria.status();
        masterDataCriteria.createdBy();
        masterDataCriteria.createdDate();
        masterDataCriteria.lastModifiedBy();
        masterDataCriteria.lastModifiedDate();
        masterDataCriteria.distinct();
    }

    private static Condition<MasterDataCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getKey()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MasterDataCriteria> copyFiltersAre(MasterDataCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getKey(), copy.getKey()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
