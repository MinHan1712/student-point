package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ClassRegistrationCriteriaTest {

    @Test
    void newClassRegistrationCriteriaHasAllFiltersNullTest() {
        var classRegistrationCriteria = new ClassRegistrationCriteria();
        assertThat(classRegistrationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void classRegistrationCriteriaFluentMethodsCreatesFiltersTest() {
        var classRegistrationCriteria = new ClassRegistrationCriteria();

        setAllFilters(classRegistrationCriteria);

        assertThat(classRegistrationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void classRegistrationCriteriaCopyCreatesNullFilterTest() {
        var classRegistrationCriteria = new ClassRegistrationCriteria();
        var copy = classRegistrationCriteria.copy();

        assertThat(classRegistrationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(classRegistrationCriteria)
        );
    }

    @Test
    void classRegistrationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var classRegistrationCriteria = new ClassRegistrationCriteria();
        setAllFilters(classRegistrationCriteria);

        var copy = classRegistrationCriteria.copy();

        assertThat(classRegistrationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(classRegistrationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var classRegistrationCriteria = new ClassRegistrationCriteria();

        assertThat(classRegistrationCriteria).hasToString("ClassRegistrationCriteria{}");
    }

    private static void setAllFilters(ClassRegistrationCriteria classRegistrationCriteria) {
        classRegistrationCriteria.id();
        classRegistrationCriteria.registerDate();
        classRegistrationCriteria.status();
        classRegistrationCriteria.remarks();
        classRegistrationCriteria.studentId();
        classRegistrationCriteria.classesId();
        classRegistrationCriteria.distinct();
    }

    private static Condition<ClassRegistrationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRegisterDate()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getRemarks()) &&
                condition.apply(criteria.getStudentId()) &&
                condition.apply(criteria.getClassesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ClassRegistrationCriteria> copyFiltersAre(
        ClassRegistrationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRegisterDate(), copy.getRegisterDate()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getRemarks(), copy.getRemarks()) &&
                condition.apply(criteria.getStudentId(), copy.getStudentId()) &&
                condition.apply(criteria.getClassesId(), copy.getClassesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
