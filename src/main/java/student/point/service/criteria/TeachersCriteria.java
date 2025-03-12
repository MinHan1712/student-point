package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.TeacherPosition;
import student.point.domain.enumeration.TeacherQualification;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Teachers} entity. This class is used
 * in {@link student.point.web.rest.TeachersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /teachers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeachersCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TeacherPosition
     */
    public static class TeacherPositionFilter extends Filter<TeacherPosition> {

        public TeacherPositionFilter() {}

        public TeacherPositionFilter(TeacherPositionFilter filter) {
            super(filter);
        }

        @Override
        public TeacherPositionFilter copy() {
            return new TeacherPositionFilter(this);
        }
    }

    /**
     * Class for filtering TeacherQualification
     */
    public static class TeacherQualificationFilter extends Filter<TeacherQualification> {

        public TeacherQualificationFilter() {}

        public TeacherQualificationFilter(TeacherQualificationFilter filter) {
            super(filter);
        }

        @Override
        public TeacherQualificationFilter copy() {
            return new TeacherQualificationFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter teachersCode;

    private StringFilter name;

    private StringFilter email;

    private StringFilter phoneNumber;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private TeacherPositionFilter position;

    private TeacherQualificationFilter qualification;

    private BooleanFilter status;

    private StringFilter notes;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter classesId;

    private LongFilter facultiesId;

    private Boolean distinct;

    public TeachersCriteria() {}

    public TeachersCriteria(TeachersCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.teachersCode = other.optionalTeachersCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.phoneNumber = other.optionalPhoneNumber().map(StringFilter::copy).orElse(null);
        this.startDate = other.optionalStartDate().map(InstantFilter::copy).orElse(null);
        this.endDate = other.optionalEndDate().map(InstantFilter::copy).orElse(null);
        this.position = other.optionalPosition().map(TeacherPositionFilter::copy).orElse(null);
        this.qualification = other.optionalQualification().map(TeacherQualificationFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.classesId = other.optionalClassesId().map(LongFilter::copy).orElse(null);
        this.facultiesId = other.optionalFacultiesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TeachersCriteria copy() {
        return new TeachersCriteria(this);
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

    public StringFilter getTeachersCode() {
        return teachersCode;
    }

    public Optional<StringFilter> optionalTeachersCode() {
        return Optional.ofNullable(teachersCode);
    }

    public StringFilter teachersCode() {
        if (teachersCode == null) {
            setTeachersCode(new StringFilter());
        }
        return teachersCode;
    }

    public void setTeachersCode(StringFilter teachersCode) {
        this.teachersCode = teachersCode;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public Optional<StringFilter> optionalPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            setPhoneNumber(new StringFilter());
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public TeacherPositionFilter getPosition() {
        return position;
    }

    public Optional<TeacherPositionFilter> optionalPosition() {
        return Optional.ofNullable(position);
    }

    public TeacherPositionFilter position() {
        if (position == null) {
            setPosition(new TeacherPositionFilter());
        }
        return position;
    }

    public void setPosition(TeacherPositionFilter position) {
        this.position = position;
    }

    public TeacherQualificationFilter getQualification() {
        return qualification;
    }

    public Optional<TeacherQualificationFilter> optionalQualification() {
        return Optional.ofNullable(qualification);
    }

    public TeacherQualificationFilter qualification() {
        if (qualification == null) {
            setQualification(new TeacherQualificationFilter());
        }
        return qualification;
    }

    public void setQualification(TeacherQualificationFilter qualification) {
        this.qualification = qualification;
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
        final TeachersCriteria that = (TeachersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(teachersCode, that.teachersCode) &&
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(position, that.position) &&
            Objects.equals(qualification, that.qualification) &&
            Objects.equals(status, that.status) &&
            Objects.equals(notes, that.notes) &&
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
            teachersCode,
            name,
            email,
            phoneNumber,
            startDate,
            endDate,
            position,
            qualification,
            status,
            notes,
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
        return "TeachersCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTeachersCode().map(f -> "teachersCode=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalPhoneNumber().map(f -> "phoneNumber=" + f + ", ").orElse("") +
            optionalStartDate().map(f -> "startDate=" + f + ", ").orElse("") +
            optionalEndDate().map(f -> "endDate=" + f + ", ").orElse("") +
            optionalPosition().map(f -> "position=" + f + ", ").orElse("") +
            optionalQualification().map(f -> "qualification=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
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
