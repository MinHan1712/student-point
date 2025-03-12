package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ClassesCriteriaTest {

    @Test
    void newClassesCriteriaHasAllFiltersNullTest() {
        var classesCriteria = new ClassesCriteria();
        assertThat(classesCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void classesCriteriaFluentMethodsCreatesFiltersTest() {
        var classesCriteria = new ClassesCriteria();

        setAllFilters(classesCriteria);

        assertThat(classesCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void classesCriteriaCopyCreatesNullFilterTest() {
        var classesCriteria = new ClassesCriteria();
        var copy = classesCriteria.copy();

        assertThat(classesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(classesCriteria)
        );
    }

    @Test
    void classesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var classesCriteria = new ClassesCriteria();
        setAllFilters(classesCriteria);

        var copy = classesCriteria.copy();

        assertThat(classesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(classesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var classesCriteria = new ClassesCriteria();

        assertThat(classesCriteria).hasToString("ClassesCriteria{}");
    }

    private static void setAllFilters(ClassesCriteria classesCriteria) {
        classesCriteria.id();
        classesCriteria.classCode();
        classesCriteria.className();
        classesCriteria.classroom();
        classesCriteria.credits();
        classesCriteria.numberOfSessions();
        classesCriteria.totalNumberOfStudents();
        classesCriteria.startDate();
        classesCriteria.endDate();
        classesCriteria.classType();
        classesCriteria.deliveryMode();
        classesCriteria.status();
        classesCriteria.notes();
        classesCriteria.description();
        classesCriteria.academicYear();
        classesCriteria.parentId();
        classesCriteria.createdBy();
        classesCriteria.createdDate();
        classesCriteria.lastModifiedBy();
        classesCriteria.lastModifiedDate();
        classesCriteria.classRegistrationId();
        classesCriteria.gradesId();
        classesCriteria.courseId();
        classesCriteria.teachersId();
        classesCriteria.distinct();
    }

    private static Condition<ClassesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getClassCode()) &&
                condition.apply(criteria.getClassName()) &&
                condition.apply(criteria.getClassroom()) &&
                condition.apply(criteria.getCredits()) &&
                condition.apply(criteria.getNumberOfSessions()) &&
                condition.apply(criteria.getTotalNumberOfStudents()) &&
                condition.apply(criteria.getStartDate()) &&
                condition.apply(criteria.getEndDate()) &&
                condition.apply(criteria.getClassType()) &&
                condition.apply(criteria.getDeliveryMode()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getAcademicYear()) &&
                condition.apply(criteria.getParentId()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getClassRegistrationId()) &&
                condition.apply(criteria.getGradesId()) &&
                condition.apply(criteria.getCourseId()) &&
                condition.apply(criteria.getTeachersId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ClassesCriteria> copyFiltersAre(ClassesCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getClassCode(), copy.getClassCode()) &&
                condition.apply(criteria.getClassName(), copy.getClassName()) &&
                condition.apply(criteria.getClassroom(), copy.getClassroom()) &&
                condition.apply(criteria.getCredits(), copy.getCredits()) &&
                condition.apply(criteria.getNumberOfSessions(), copy.getNumberOfSessions()) &&
                condition.apply(criteria.getTotalNumberOfStudents(), copy.getTotalNumberOfStudents()) &&
                condition.apply(criteria.getStartDate(), copy.getStartDate()) &&
                condition.apply(criteria.getEndDate(), copy.getEndDate()) &&
                condition.apply(criteria.getClassType(), copy.getClassType()) &&
                condition.apply(criteria.getDeliveryMode(), copy.getDeliveryMode()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getAcademicYear(), copy.getAcademicYear()) &&
                condition.apply(criteria.getParentId(), copy.getParentId()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getClassRegistrationId(), copy.getClassRegistrationId()) &&
                condition.apply(criteria.getGradesId(), copy.getGradesId()) &&
                condition.apply(criteria.getCourseId(), copy.getCourseId()) &&
                condition.apply(criteria.getTeachersId(), copy.getTeachersId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
