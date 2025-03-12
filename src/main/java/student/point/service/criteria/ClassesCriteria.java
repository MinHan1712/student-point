package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.ClassesType;
import student.point.domain.enumeration.DeliveryMode;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Classes} entity. This class is used
 * in {@link student.point.web.rest.ClassesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ClassesType
     */
    public static class ClassesTypeFilter extends Filter<ClassesType> {

        public ClassesTypeFilter() {}

        public ClassesTypeFilter(ClassesTypeFilter filter) {
            super(filter);
        }

        @Override
        public ClassesTypeFilter copy() {
            return new ClassesTypeFilter(this);
        }
    }

    /**
     * Class for filtering DeliveryMode
     */
    public static class DeliveryModeFilter extends Filter<DeliveryMode> {

        public DeliveryModeFilter() {}

        public DeliveryModeFilter(DeliveryModeFilter filter) {
            super(filter);
        }

        @Override
        public DeliveryModeFilter copy() {
            return new DeliveryModeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter classCode;

    private StringFilter className;

    private StringFilter classroom;

    private IntegerFilter credits;

    private IntegerFilter numberOfSessions;

    private IntegerFilter totalNumberOfStudents;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private ClassesTypeFilter classType;

    private DeliveryModeFilter deliveryMode;

    private BooleanFilter status;

    private StringFilter notes;

    private StringFilter description;

    private StringFilter academicYear;

    private LongFilter parentId;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter classRegistrationId;

    private LongFilter gradesId;

    private LongFilter courseId;

    private LongFilter teachersId;

    private Boolean distinct;

    public ClassesCriteria() {}

    public ClassesCriteria(ClassesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.classCode = other.optionalClassCode().map(StringFilter::copy).orElse(null);
        this.className = other.optionalClassName().map(StringFilter::copy).orElse(null);
        this.classroom = other.optionalClassroom().map(StringFilter::copy).orElse(null);
        this.credits = other.optionalCredits().map(IntegerFilter::copy).orElse(null);
        this.numberOfSessions = other.optionalNumberOfSessions().map(IntegerFilter::copy).orElse(null);
        this.totalNumberOfStudents = other.optionalTotalNumberOfStudents().map(IntegerFilter::copy).orElse(null);
        this.startDate = other.optionalStartDate().map(InstantFilter::copy).orElse(null);
        this.endDate = other.optionalEndDate().map(InstantFilter::copy).orElse(null);
        this.classType = other.optionalClassType().map(ClassesTypeFilter::copy).orElse(null);
        this.deliveryMode = other.optionalDeliveryMode().map(DeliveryModeFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.academicYear = other.optionalAcademicYear().map(StringFilter::copy).orElse(null);
        this.parentId = other.optionalParentId().map(LongFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.classRegistrationId = other.optionalClassRegistrationId().map(LongFilter::copy).orElse(null);
        this.gradesId = other.optionalGradesId().map(LongFilter::copy).orElse(null);
        this.courseId = other.optionalCourseId().map(LongFilter::copy).orElse(null);
        this.teachersId = other.optionalTeachersId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ClassesCriteria copy() {
        return new ClassesCriteria(this);
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

    public StringFilter getClassCode() {
        return classCode;
    }

    public Optional<StringFilter> optionalClassCode() {
        return Optional.ofNullable(classCode);
    }

    public StringFilter classCode() {
        if (classCode == null) {
            setClassCode(new StringFilter());
        }
        return classCode;
    }

    public void setClassCode(StringFilter classCode) {
        this.classCode = classCode;
    }

    public StringFilter getClassName() {
        return className;
    }

    public Optional<StringFilter> optionalClassName() {
        return Optional.ofNullable(className);
    }

    public StringFilter className() {
        if (className == null) {
            setClassName(new StringFilter());
        }
        return className;
    }

    public void setClassName(StringFilter className) {
        this.className = className;
    }

    public StringFilter getClassroom() {
        return classroom;
    }

    public Optional<StringFilter> optionalClassroom() {
        return Optional.ofNullable(classroom);
    }

    public StringFilter classroom() {
        if (classroom == null) {
            setClassroom(new StringFilter());
        }
        return classroom;
    }

    public void setClassroom(StringFilter classroom) {
        this.classroom = classroom;
    }

    public IntegerFilter getCredits() {
        return credits;
    }

    public Optional<IntegerFilter> optionalCredits() {
        return Optional.ofNullable(credits);
    }

    public IntegerFilter credits() {
        if (credits == null) {
            setCredits(new IntegerFilter());
        }
        return credits;
    }

    public void setCredits(IntegerFilter credits) {
        this.credits = credits;
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

    public IntegerFilter getTotalNumberOfStudents() {
        return totalNumberOfStudents;
    }

    public Optional<IntegerFilter> optionalTotalNumberOfStudents() {
        return Optional.ofNullable(totalNumberOfStudents);
    }

    public IntegerFilter totalNumberOfStudents() {
        if (totalNumberOfStudents == null) {
            setTotalNumberOfStudents(new IntegerFilter());
        }
        return totalNumberOfStudents;
    }

    public void setTotalNumberOfStudents(IntegerFilter totalNumberOfStudents) {
        this.totalNumberOfStudents = totalNumberOfStudents;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public Optional<InstantFilter> optionalStartDate() {
        return Optional.ofNullable(startDate);
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            setStartDate(new InstantFilter());
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public Optional<InstantFilter> optionalEndDate() {
        return Optional.ofNullable(endDate);
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            setEndDate(new InstantFilter());
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public ClassesTypeFilter getClassType() {
        return classType;
    }

    public Optional<ClassesTypeFilter> optionalClassType() {
        return Optional.ofNullable(classType);
    }

    public ClassesTypeFilter classType() {
        if (classType == null) {
            setClassType(new ClassesTypeFilter());
        }
        return classType;
    }

    public void setClassType(ClassesTypeFilter classType) {
        this.classType = classType;
    }

    public DeliveryModeFilter getDeliveryMode() {
        return deliveryMode;
    }

    public Optional<DeliveryModeFilter> optionalDeliveryMode() {
        return Optional.ofNullable(deliveryMode);
    }

    public DeliveryModeFilter deliveryMode() {
        if (deliveryMode == null) {
            setDeliveryMode(new DeliveryModeFilter());
        }
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryModeFilter deliveryMode) {
        this.deliveryMode = deliveryMode;
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

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public Optional<StringFilter> optionalAcademicYear() {
        return Optional.ofNullable(academicYear);
    }

    public StringFilter academicYear() {
        if (academicYear == null) {
            setAcademicYear(new StringFilter());
        }
        return academicYear;
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public Optional<LongFilter> optionalParentId() {
        return Optional.ofNullable(parentId);
    }

    public LongFilter parentId() {
        if (parentId == null) {
            setParentId(new LongFilter());
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
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

    public LongFilter getClassRegistrationId() {
        return classRegistrationId;
    }

    public Optional<LongFilter> optionalClassRegistrationId() {
        return Optional.ofNullable(classRegistrationId);
    }

    public LongFilter classRegistrationId() {
        if (classRegistrationId == null) {
            setClassRegistrationId(new LongFilter());
        }
        return classRegistrationId;
    }

    public void setClassRegistrationId(LongFilter classRegistrationId) {
        this.classRegistrationId = classRegistrationId;
    }

    public LongFilter getGradesId() {
        return gradesId;
    }

    public Optional<LongFilter> optionalGradesId() {
        return Optional.ofNullable(gradesId);
    }

    public LongFilter gradesId() {
        if (gradesId == null) {
            setGradesId(new LongFilter());
        }
        return gradesId;
    }

    public void setGradesId(LongFilter gradesId) {
        this.gradesId = gradesId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public Optional<LongFilter> optionalCourseId() {
        return Optional.ofNullable(courseId);
    }

    public LongFilter courseId() {
        if (courseId == null) {
            setCourseId(new LongFilter());
        }
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    public LongFilter getTeachersId() {
        return teachersId;
    }

    public Optional<LongFilter> optionalTeachersId() {
        return Optional.ofNullable(teachersId);
    }

    public LongFilter teachersId() {
        if (teachersId == null) {
            setTeachersId(new LongFilter());
        }
        return teachersId;
    }

    public void setTeachersId(LongFilter teachersId) {
        this.teachersId = teachersId;
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
        final ClassesCriteria that = (ClassesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(classCode, that.classCode) &&
            Objects.equals(className, that.className) &&
            Objects.equals(classroom, that.classroom) &&
            Objects.equals(credits, that.credits) &&
            Objects.equals(numberOfSessions, that.numberOfSessions) &&
            Objects.equals(totalNumberOfStudents, that.totalNumberOfStudents) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(classType, that.classType) &&
            Objects.equals(deliveryMode, that.deliveryMode) &&
            Objects.equals(status, that.status) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(description, that.description) &&
            Objects.equals(academicYear, that.academicYear) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(classRegistrationId, that.classRegistrationId) &&
            Objects.equals(gradesId, that.gradesId) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(teachersId, that.teachersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            classCode,
            className,
            classroom,
            credits,
            numberOfSessions,
            totalNumberOfStudents,
            startDate,
            endDate,
            classType,
            deliveryMode,
            status,
            notes,
            description,
            academicYear,
            parentId,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            classRegistrationId,
            gradesId,
            courseId,
            teachersId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalClassCode().map(f -> "classCode=" + f + ", ").orElse("") +
            optionalClassName().map(f -> "className=" + f + ", ").orElse("") +
            optionalClassroom().map(f -> "classroom=" + f + ", ").orElse("") +
            optionalCredits().map(f -> "credits=" + f + ", ").orElse("") +
            optionalNumberOfSessions().map(f -> "numberOfSessions=" + f + ", ").orElse("") +
            optionalTotalNumberOfStudents().map(f -> "totalNumberOfStudents=" + f + ", ").orElse("") +
            optionalStartDate().map(f -> "startDate=" + f + ", ").orElse("") +
            optionalEndDate().map(f -> "endDate=" + f + ", ").orElse("") +
            optionalClassType().map(f -> "classType=" + f + ", ").orElse("") +
            optionalDeliveryMode().map(f -> "deliveryMode=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalAcademicYear().map(f -> "academicYear=" + f + ", ").orElse("") +
            optionalParentId().map(f -> "parentId=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalClassRegistrationId().map(f -> "classRegistrationId=" + f + ", ").orElse("") +
            optionalGradesId().map(f -> "gradesId=" + f + ", ").orElse("") +
            optionalCourseId().map(f -> "courseId=" + f + ", ").orElse("") +
            optionalTeachersId().map(f -> "teachersId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
