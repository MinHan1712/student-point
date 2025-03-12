package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ModuleConfigurationCriteriaTest {

    @Test
    void newModuleConfigurationCriteriaHasAllFiltersNullTest() {
        var moduleConfigurationCriteria = new ModuleConfigurationCriteria();
        assertThat(moduleConfigurationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void moduleConfigurationCriteriaFluentMethodsCreatesFiltersTest() {
        var moduleConfigurationCriteria = new ModuleConfigurationCriteria();

        setAllFilters(moduleConfigurationCriteria);

        assertThat(moduleConfigurationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void moduleConfigurationCriteriaCopyCreatesNullFilterTest() {
        var moduleConfigurationCriteria = new ModuleConfigurationCriteria();
        var copy = moduleConfigurationCriteria.copy();

        assertThat(moduleConfigurationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(moduleConfigurationCriteria)
        );
    }

    @Test
    void moduleConfigurationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var moduleConfigurationCriteria = new ModuleConfigurationCriteria();
        setAllFilters(moduleConfigurationCriteria);

        var copy = moduleConfigurationCriteria.copy();

        assertThat(moduleConfigurationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(moduleConfigurationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var moduleConfigurationCriteria = new ModuleConfigurationCriteria();

        assertThat(moduleConfigurationCriteria).hasToString("ModuleConfigurationCriteria{}");
    }

    private static void setAllFilters(ModuleConfigurationCriteria moduleConfigurationCriteria) {
        moduleConfigurationCriteria.id();
        moduleConfigurationCriteria.name();
        moduleConfigurationCriteria.prefix();
        moduleConfigurationCriteria.padding();
        moduleConfigurationCriteria.numberNextActual();
        moduleConfigurationCriteria.numberIncrement();
        moduleConfigurationCriteria.createdBy();
        moduleConfigurationCriteria.createdDate();
        moduleConfigurationCriteria.lastModifiedBy();
        moduleConfigurationCriteria.lastModifiedDate();
        moduleConfigurationCriteria.distinct();
    }

    private static Condition<ModuleConfigurationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getPrefix()) &&
                condition.apply(criteria.getPadding()) &&
                condition.apply(criteria.getNumberNextActual()) &&
                condition.apply(criteria.getNumberIncrement()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ModuleConfigurationCriteria> copyFiltersAre(
        ModuleConfigurationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getPrefix(), copy.getPrefix()) &&
                condition.apply(criteria.getPadding(), copy.getPadding()) &&
                condition.apply(criteria.getNumberNextActual(), copy.getNumberNextActual()) &&
                condition.apply(criteria.getNumberIncrement(), copy.getNumberIncrement()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
