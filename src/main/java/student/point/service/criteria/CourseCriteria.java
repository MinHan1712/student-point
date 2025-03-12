package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.CourseType;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Course} entity. This class is used
 * in {@link student.point.web.rest.CourseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /courses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CourseType
     */
    public static class CourseTypeFilter extends Filter<CourseType> {

        public CourseTypeFilter() {}

        public CourseTypeFilter(CourseTypeFilter filter) {
            super(filter);
        }

        @Override
        public CourseTypeFilter copy() {
            return new CourseTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter courseCode;

    private StringFilter courseTitle;

    private InstantFilter credits;

    private IntegerFilter lecture;

    private IntegerFilter tutorialDiscussion;

    private IntegerFilter practical;

    private IntegerFilter laboratory;

    private IntegerFilter selfStudy;

    private IntegerFilter numberOfSessions;

    private CourseTypeFilter courseType;

    private StringFilter notes;

    private BooleanFilter status;

    private StringFilter semester;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter classesId;

    private LongFilter facultiesId;

    private Boolean distinct;

    public CourseCriteria() {}

    public CourseCriteria(CourseCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.courseCode = other.optionalCourseCode().map(StringFilter::copy).orElse(null);
        this.courseTitle = other.optionalCourseTitle().map(StringFilter::copy).orElse(null);
        this.credits = other.optionalCredits().map(InstantFilter::copy).orElse(null);
        this.lecture = other.optionalLecture().map(IntegerFilter::copy).orElse(null);
        this.tutorialDiscussion = other.optionalTutorialDiscussion().map(IntegerFilter::copy).orElse(null);
        this.practical = other.optionalPractical().map(IntegerFilter::copy).orElse(null);
        this.laboratory = other.optionalLaboratory().map(IntegerFilter::copy).orElse(null);
        this.selfStudy = other.optionalSelfStudy().map(IntegerFilter::copy).orElse(null);
        this.numberOfSessions = other.optionalNumberOfSessions().map(IntegerFilter::copy).orElse(null);
        this.courseType = other.optionalCourseType().map(CourseTypeFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.semester = other.optionalSemester().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.classesId = other.optionalClassesId().map(LongFilter::copy).orElse(null);
        this.facultiesId = other.optionalFacultiesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CourseCriteria copy() {
        return new CourseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCourseCode() {
        return courseCode;
    }

    public Optional<StringFilter> optionalCourseCode() {
        return Optional.ofNullable(courseCode);
    }

    public StringFilter courseCode() {
        if (courseCode == null) {
            setCourseCode(new StringFilter());
        }
        return courseCode;
    }

    public void setCourseCode(StringFilter courseCode) {
        this.courseCode = courseCode;
    }

    public StringFilter getCourseTitle() {
        return courseTitle;
    }

    public Optional<StringFilter> optionalCourseTitle() {
        return Optional.ofNullable(courseTitle);
    }

    public StringFilter courseTitle() {
        if (courseTitle == null) {
            setCourseTitle(new StringFilter());
        }
        return courseTitle;
    }

    public void setCourseTitle(StringFilter courseTitle) {
        this.courseTitle = courseTitle;
    }

    public InstantFilter getCredits() {
        return credits;
    }

    public Optional<InstantFilter> optionalCredits() {
        return Optional.ofNullable(credits);
    }

    public InstantFilter credits() {
        if (credits == null) {
            setCredits(new InstantFilter());
        }
        return credits;
    }

    public void setCredits(InstantFilter credits) {
        this.credits = credits;
    }

    public IntegerFilter getLecture() {
        return lecture;
    }

    public Optional<IntegerFilter> optionalLecture() {
        return Optional.ofNullable(lecture);
    }

    public IntegerFilter lecture() {
        if (lecture == null) {
            setLecture(new IntegerFilter());
        }
        return lecture;
    }

    public void setLecture(IntegerFilter lecture) {
        this.lecture = lecture;
    }

    public IntegerFilter getTutorialDiscussion() {
        return tutorialDiscussion;
    }

    public Optional<IntegerFilter> optionalTutorialDiscussion() {
        return Optional.ofNullable(tutorialDiscussion);
    }

    public IntegerFilter tutorialDiscussion() {
        if (tutorialDiscussion == null) {
            setTutorialDiscussion(new IntegerFilter());
        }
        return tutorialDiscussion;
    }

    public void setTutorialDiscussion(IntegerFilter tutorialDiscussion) {
        this.tutorialDiscussion = tutorialDiscussion;
    }

    public IntegerFilter getPractical() {
        return practical;
    }

    public Optional<IntegerFilter> optionalPractical() {
        return Optional.ofNullable(practical);
    }

    public IntegerFilter practical() {
        if (practical == null) {
            setPractical(new IntegerFilter());
        }
        return practical;
    }

    public void setPractical(IntegerFilter practical) {
        this.practical = practical;
    }

    public IntegerFilter getLaboratory() {
        return laboratory;
    }

    public Optional<IntegerFilter> optionalLaboratory() {
        return Optional.ofNullable(laboratory);
    }

    public IntegerFilter laboratory() {
        if (laboratory == null) {
            setLaboratory(new IntegerFilter());
        }
        return laboratory;
    }

    public void setLaboratory(IntegerFilter laboratory) {
        this.laboratory = laboratory;
    }

    public IntegerFilter getSelfStudy() {
        return selfStudy;
    }

    public Optional<IntegerFilter> optionalSelfStudy() {
        return Optional.ofNullable(selfStudy);
    }

    public IntegerFilter selfStudy() {
        if (selfStudy == null) {
            setSelfStudy(new IntegerFilter());
        }
        return selfStudy;
    }

    public void setSelfStudy(IntegerFilter selfStudy) {
        this.selfStudy = selfStudy;
    }

    public IntegerFilter getNumberOfSessions() {
        return numberOfSessions;
    }

    public Optional<IntegerFilter> optionalNumberOfSessions() {
        return Optional.ofNullable(numberOfSessions);
    }

    public IntegerFilter numberOfSessions() {
        if (numberOfSessions == null) {
            setNumberOfSessions(new IntegerFilter());
        }
        return numberOfSessions;
    }

    public void setNumberOfSessions(IntegerFilter numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public CourseTypeFilter getCourseType() {
        return courseType;
    }

    public Optional<CourseTypeFilter> optionalCourseType() {
        return Optional.ofNullable(courseType);
    }

    public CourseTypeFilter courseType() {
        if (courseType == null) {
            setCourseType(new CourseTypeFilter());
        }
        return courseType;
    }

    public void setCourseType(CourseTypeFilter courseType) {
        this.courseType = courseType;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public Optional<BooleanFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public BooleanFilter status() {
        if (status == null) {
            setStatus(new BooleanFilter());
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public StringFilter getSemester() {
        return semester;
    }

    public Optional<StringFilter> optionalSemester() {
        return Optional.ofNullable(semester);
    }

    public StringFilter semester() {
        if (semester == null) {
            setSemester(new StringFilter());
        }
        return semester;
    }

    public void setSemester(StringFilter semester) {
        this.semester = semester;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<StringFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new StringFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Optional<StringFilter> optionalLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            setLastModifiedBy(new StringFilter());
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Optional<InstantFilter> optionalLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            setLastModifiedDate(new InstantFilter());
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getClassesId() {
        return classesId;
    }

    public Optional<LongFilter> optionalClassesId() {
        return Optional.ofNullable(classesId);
    }

    public LongFilter classesId() {
        if (classesId == null) {
            setClassesId(new LongFilter());
        }
        return classesId;
    }

    public void setClassesId(LongFilter classesId) {
        this.classesId = classesId;
    }

    public LongFilter getFacultiesId() {
        return facultiesId;
    }

    public Optional<LongFilter> optionalFacultiesId() {
        return Optional.ofNullable(facultiesId);
    }

    public LongFilter facultiesId() {
        if (facultiesId == null) {
            setFacultiesId(new LongFilter());
        }
        return facultiesId;
    }

    public void setFacultiesId(LongFilter facultiesId) {
        this.facultiesId = facultiesId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourseCriteria that = (CourseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(courseCode, that.courseCode) &&
            Objects.equals(courseTitle, that.courseTitle) &&
            Objects.equals(credits, that.credits) &&
            Objects.equals(lecture, that.lecture) &&
            Objects.equals(tutorialDiscussion, that.tutorialDiscussion) &&
            Objects.equals(practical, that.practical) &&
            Objects.equals(laboratory, that.laboratory) &&
            Objects.equals(selfStudy, that.selfStudy) &&
            Objects.equals(numberOfSessions, that.numberOfSessions) &&
            Objects.equals(courseType, that.courseType) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(status, that.status) &&
            Objects.equals(semester, that.semester) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(classesId, that.classesId) &&
            Objects.equals(facultiesId, that.facultiesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            courseCode,
            courseTitle,
            credits,
            lecture,
            tutorialDiscussion,
            practical,
            laboratory,
            selfStudy,
            numberOfSessions,
            courseType,
            notes,
            status,
            semester,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            classesId,
            facultiesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCourseCode().map(f -> "courseCode=" + f + ", ").orElse("") +
            optionalCourseTitle().map(f -> "courseTitle=" + f + ", ").orElse("") +
            optionalCredits().map(f -> "credits=" + f + ", ").orElse("") +
            optionalLecture().map(f -> "lecture=" + f + ", ").orElse("") +
            optionalTutorialDiscussion().map(f -> "tutorialDiscussion=" + f + ", ").orElse("") +
            optionalPractical().map(f -> "practical=" + f + ", ").orElse("") +
            optionalLaboratory().map(f -> "laboratory=" + f + ", ").orElse("") +
            optionalSelfStudy().map(f -> "selfStudy=" + f + ", ").orElse("") +
            optionalNumberOfSessions().map(f -> "numberOfSessions=" + f + ", ").orElse("") +
            optionalCourseType().map(f -> "courseType=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalSemester().map(f -> "semester=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalClassesId().map(f -> "classesId=" + f + ", ").orElse("") +
            optionalFacultiesId().map(f -> "facultiesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
