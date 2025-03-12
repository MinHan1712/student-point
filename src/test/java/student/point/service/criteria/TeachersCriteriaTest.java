package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TeachersCriteriaTest {

    @Test
    void newTeachersCriteriaHasAllFiltersNullTest() {
        var teachersCriteria = new TeachersCriteria();
        assertThat(teachersCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void teachersCriteriaFluentMethodsCreatesFiltersTest() {
        var teachersCriteria = new TeachersCriteria();

        setAllFilters(teachersCriteria);

        assertThat(teachersCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void teachersCriteriaCopyCreatesNullFilterTest() {
        var teachersCriteria = new TeachersCriteria();
        var copy = teachersCriteria.copy();

        assertThat(teachersCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(teachersCriteria)
        );
    }

    @Test
    void teachersCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var teachersCriteria = new TeachersCriteria();
        setAllFilters(teachersCriteria);

        var copy = teachersCriteria.copy();

        assertThat(teachersCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(teachersCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var teachersCriteria = new TeachersCriteria();

        assertThat(teachersCriteria).hasToString("TeachersCriteria{}");
    }

    private static void setAllFilters(TeachersCriteria teachersCriteria) {
        teachersCriteria.id();
        teachersCriteria.teachersCode();
        teachersCriteria.name();
        teachersCriteria.email();
        teachersCriteria.phoneNumber();
        teachersCriteria.startDate();
        teachersCriteria.endDate();
        teachersCriteria.position();
        teachersCriteria.qualification();
        teachersCriteria.status();
        teachersCriteria.notes();
        teachersCriteria.createdBy();
        teachersCriteria.createdDate();
        teachersCriteria.lastModifiedBy();
        teachersCriteria.lastModifiedDate();
        teachersCriteria.classesId();
        teachersCriteria.facultiesId();
        teachersCriteria.distinct();
    }

    private static Condition<TeachersCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTeachersCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getPhoneNumber()) &&
                condition.apply(criteria.getStartDate()) &&
                condition.apply(criteria.getEndDate()) &&
                condition.apply(criteria.getPosition()) &&
                condition.apply(criteria.getQualification()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getClassesId()) &&
                condition.apply(criteria.getFacultiesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TeachersCriteria> copyFiltersAre(TeachersCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTeachersCode(), copy.getTeachersCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getPhoneNumber(), copy.getPhoneNumber()) &&
                condition.apply(criteria.getStartDate(), copy.getStartDate()) &&
                condition.apply(criteria.getEndDate(), copy.getEndDate()) &&
                condition.apply(criteria.getPosition(), copy.getPosition()) &&
                condition.apply(criteria.getQualification(), copy.getQualification()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getClassesId(), copy.getClassesId()) &&
                condition.apply(criteria.getFacultiesId(), copy.getFacultiesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
