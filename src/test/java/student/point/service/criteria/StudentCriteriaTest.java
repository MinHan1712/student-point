package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StudentCriteriaTest {

    @Test
    void newStudentCriteriaHasAllFiltersNullTest() {
        var studentCriteria = new StudentCriteria();
        assertThat(studentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void studentCriteriaFluentMethodsCreatesFiltersTest() {
        var studentCriteria = new StudentCriteria();

        setAllFilters(studentCriteria);

        assertThat(studentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void studentCriteriaCopyCreatesNullFilterTest() {
        var studentCriteria = new StudentCriteria();
        var copy = studentCriteria.copy();

        assertThat(studentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(studentCriteria)
        );
    }

    @Test
    void studentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var studentCriteria = new StudentCriteria();
        setAllFilters(studentCriteria);

        var copy = studentCriteria.copy();

        assertThat(studentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(studentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var studentCriteria = new StudentCriteria();

        assertThat(studentCriteria).hasToString("StudentCriteria{}");
    }

    private static void setAllFilters(StudentCriteria studentCriteria) {
        studentCriteria.id();
        studentCriteria.studentCode();
        studentCriteria.fullName();
        studentCriteria.dateOfBirth();
        studentCriteria.gender();
        studentCriteria.address();
        studentCriteria.phoneNumber();
        studentCriteria.email();
        studentCriteria.notes();
        studentCriteria.status();
        studentCriteria.dateEnrollment();
        studentCriteria.createdBy();
        studentCriteria.createdDate();
        studentCriteria.lastModifiedBy();
        studentCriteria.lastModifiedDate();
        studentCriteria.classRegistrationId();
        studentCriteria.conductScoresId();
        studentCriteria.statisticsDetailsId();
        studentCriteria.gradesId();
        studentCriteria.distinct();
    }

    private static Condition<StudentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStudentCode()) &&
                condition.apply(criteria.getFullName()) &&
                condition.apply(criteria.getDateOfBirth()) &&
                condition.apply(criteria.getGender()) &&
                condition.apply(criteria.getAddress()) &&
                condition.apply(criteria.getPhoneNumber()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getDateEnrollment()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getClassRegistrationId()) &&
                condition.apply(criteria.getConductScoresId()) &&
                condition.apply(criteria.getStatisticsDetailsId()) &&
                condition.apply(criteria.getGradesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StudentCriteria> copyFiltersAre(StudentCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStudentCode(), copy.getStudentCode()) &&
                condition.apply(criteria.getFullName(), copy.getFullName()) &&
                condition.apply(criteria.getDateOfBirth(), copy.getDateOfBirth()) &&
                condition.apply(criteria.getGender(), copy.getGender()) &&
                condition.apply(criteria.getAddress(), copy.getAddress()) &&
                condition.apply(criteria.getPhoneNumber(), copy.getPhoneNumber()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getDateEnrollment(), copy.getDateEnrollment()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getClassRegistrationId(), copy.getClassRegistrationId()) &&
                condition.apply(criteria.getConductScoresId(), copy.getConductScoresId()) &&
                condition.apply(criteria.getStatisticsDetailsId(), copy.getStatisticsDetailsId()) &&
                condition.apply(criteria.getGradesId(), copy.getGradesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
