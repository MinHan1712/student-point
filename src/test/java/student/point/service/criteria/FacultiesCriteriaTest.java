package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FacultiesCriteriaTest {

    @Test
    void newFacultiesCriteriaHasAllFiltersNullTest() {
        var facultiesCriteria = new FacultiesCriteria();
        assertThat(facultiesCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void facultiesCriteriaFluentMethodsCreatesFiltersTest() {
        var facultiesCriteria = new FacultiesCriteria();

        setAllFilters(facultiesCriteria);

        assertThat(facultiesCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void facultiesCriteriaCopyCreatesNullFilterTest() {
        var facultiesCriteria = new FacultiesCriteria();
        var copy = facultiesCriteria.copy();

        assertThat(facultiesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(facultiesCriteria)
        );
    }

    @Test
    void facultiesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var facultiesCriteria = new FacultiesCriteria();
        setAllFilters(facultiesCriteria);

        var copy = facultiesCriteria.copy();

        assertThat(facultiesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(facultiesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var facultiesCriteria = new FacultiesCriteria();

        assertThat(facultiesCriteria).hasToString("FacultiesCriteria{}");
    }

    private static void setAllFilters(FacultiesCriteria facultiesCriteria) {
        facultiesCriteria.id();
        facultiesCriteria.facultyCode();
        facultiesCriteria.facultyName();
        facultiesCriteria.description();
        facultiesCriteria.establishedDate();
        facultiesCriteria.phoneNumber();
        facultiesCriteria.location();
        facultiesCriteria.notes();
        facultiesCriteria.parentId();
        facultiesCriteria.teachersId();
        facultiesCriteria.courseId();
        facultiesCriteria.distinct();
    }

    private static Condition<FacultiesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFacultyCode()) &&
                condition.apply(criteria.getFacultyName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getEstablishedDate()) &&
                condition.apply(criteria.getPhoneNumber()) &&
                condition.apply(criteria.getLocation()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getParentId()) &&
                condition.apply(criteria.getTeachersId()) &&
                condition.apply(criteria.getCourseId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FacultiesCriteria> copyFiltersAre(FacultiesCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFacultyCode(), copy.getFacultyCode()) &&
                condition.apply(criteria.getFacultyName(), copy.getFacultyName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getEstablishedDate(), copy.getEstablishedDate()) &&
                condition.apply(criteria.getPhoneNumber(), copy.getPhoneNumber()) &&
                condition.apply(criteria.getLocation(), copy.getLocation()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getParentId(), copy.getParentId()) &&
                condition.apply(criteria.getTeachersId(), copy.getTeachersId()) &&
                condition.apply(criteria.getCourseId(), copy.getCourseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
