package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CourseCriteriaTest {

    @Test
    void newCourseCriteriaHasAllFiltersNullTest() {
        var courseCriteria = new CourseCriteria();
        assertThat(courseCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void courseCriteriaFluentMethodsCreatesFiltersTest() {
        var courseCriteria = new CourseCriteria();

        setAllFilters(courseCriteria);

        assertThat(courseCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void courseCriteriaCopyCreatesNullFilterTest() {
        var courseCriteria = new CourseCriteria();
        var copy = courseCriteria.copy();

        assertThat(courseCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(courseCriteria)
        );
    }

    @Test
    void courseCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var courseCriteria = new CourseCriteria();
        setAllFilters(courseCriteria);

        var copy = courseCriteria.copy();

        assertThat(courseCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(courseCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var courseCriteria = new CourseCriteria();

        assertThat(courseCriteria).hasToString("CourseCriteria{}");
    }

    private static void setAllFilters(CourseCriteria courseCriteria) {
        courseCriteria.id();
        courseCriteria.courseCode();
        courseCriteria.courseTitle();
        courseCriteria.credits();
        courseCriteria.lecture();
        courseCriteria.tutorialDiscussion();
        courseCriteria.practical();
        courseCriteria.laboratory();
        courseCriteria.selfStudy();
        courseCriteria.numberOfSessions();
        courseCriteria.courseType();
        courseCriteria.notes();
        courseCriteria.status();
        courseCriteria.semester();
        courseCriteria.createdBy();
        courseCriteria.createdDate();
        courseCriteria.lastModifiedBy();
        courseCriteria.lastModifiedDate();
        courseCriteria.classesId();
        courseCriteria.facultiesId();
        courseCriteria.distinct();
    }

    private static Condition<CourseCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCourseCode()) &&
                condition.apply(criteria.getCourseTitle()) &&
                condition.apply(criteria.getCredits()) &&
                condition.apply(criteria.getLecture()) &&
                condition.apply(criteria.getTutorialDiscussion()) &&
                condition.apply(criteria.getPractical()) &&
                condition.apply(criteria.getLaboratory()) &&
                condition.apply(criteria.getSelfStudy()) &&
                condition.apply(criteria.getNumberOfSessions()) &&
                condition.apply(criteria.getCourseType()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getSemester()) &&
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

    private static Condition<CourseCriteria> copyFiltersAre(CourseCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCourseCode(), copy.getCourseCode()) &&
                condition.apply(criteria.getCourseTitle(), copy.getCourseTitle()) &&
                condition.apply(criteria.getCredits(), copy.getCredits()) &&
                condition.apply(criteria.getLecture(), copy.getLecture()) &&
                condition.apply(criteria.getTutorialDiscussion(), copy.getTutorialDiscussion()) &&
                condition.apply(criteria.getPractical(), copy.getPractical()) &&
                condition.apply(criteria.getLaboratory(), copy.getLaboratory()) &&
                condition.apply(criteria.getSelfStudy(), copy.getSelfStudy()) &&
                condition.apply(criteria.getNumberOfSessions(), copy.getNumberOfSessions()) &&
                condition.apply(criteria.getCourseType(), copy.getCourseType()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getSemester(), copy.getSemester()) &&
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
