package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Faculties} entity. This class is used
 * in {@link student.point.web.rest.FacultiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /faculties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacultiesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter facultyCode;

    private StringFilter facultyName;

    private StringFilter description;

    private InstantFilter establishedDate;

    private StringFilter phoneNumber;

    private StringFilter location;

    private StringFilter notes;

    private LongFilter parentId;

    private LongFilter teachersId;

    private LongFilter courseId;

    private Boolean distinct;

    public FacultiesCriteria() {}

    public FacultiesCriteria(FacultiesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.facultyCode = other.optionalFacultyCode().map(StringFilter::copy).orElse(null);
        this.facultyName = other.optionalFacultyName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.establishedDate = other.optionalEstablishedDate().map(InstantFilter::copy).orElse(null);
        this.phoneNumber = other.optionalPhoneNumber().map(StringFilter::copy).orElse(null);
        this.location = other.optionalLocation().map(StringFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.parentId = other.optionalParentId().map(LongFilter::copy).orElse(null);
        this.teachersId = other.optionalTeachersId().map(LongFilter::copy).orElse(null);
        this.courseId = other.optionalCourseId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FacultiesCriteria copy() {
        return new FacultiesCriteria(this);
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

    public StringFilter getFacultyCode() {
        return facultyCode;
    }

    public Optional<StringFilter> optionalFacultyCode() {
        return Optional.ofNullable(facultyCode);
    }

    public StringFilter facultyCode() {
        if (facultyCode == null) {
            setFacultyCode(new StringFilter());
        }
        return facultyCode;
    }

    public void setFacultyCode(StringFilter facultyCode) {
        this.facultyCode = facultyCode;
    }

    public StringFilter getFacultyName() {
        return facultyName;
    }

    public Optional<StringFilter> optionalFacultyName() {
        return Optional.ofNullable(facultyName);
    }

    public StringFilter facultyName() {
        if (facultyName == null) {
            setFacultyName(new StringFilter());
        }
        return facultyName;
    }

    public void setFacultyName(StringFilter facultyName) {
        this.facultyName = facultyName;
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

    public InstantFilter getEstablishedDate() {
        return establishedDate;
    }

    public Optional<InstantFilter> optionalEstablishedDate() {
        return Optional.ofNullable(establishedDate);
    }

    public InstantFilter establishedDate() {
        if (establishedDate == null) {
            setEstablishedDate(new InstantFilter());
        }
        return establishedDate;
    }

    public void setEstablishedDate(InstantFilter establishedDate) {
        this.establishedDate = establishedDate;
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

    public StringFilter getLocation() {
        return location;
    }

    public Optional<StringFilter> optionalLocation() {
        return Optional.ofNullable(location);
    }

    public StringFilter location() {
        if (location == null) {
            setLocation(new StringFilter());
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
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
        final FacultiesCriteria that = (FacultiesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(facultyCode, that.facultyCode) &&
            Objects.equals(facultyName, that.facultyName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(establishedDate, that.establishedDate) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(location, that.location) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(teachersId, that.teachersId) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            facultyCode,
            facultyName,
            description,
            establishedDate,
            phoneNumber,
            location,
            notes,
            parentId,
            teachersId,
            courseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacultiesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFacultyCode().map(f -> "facultyCode=" + f + ", ").orElse("") +
            optionalFacultyName().map(f -> "facultyName=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalEstablishedDate().map(f -> "establishedDate=" + f + ", ").orElse("") +
            optionalPhoneNumber().map(f -> "phoneNumber=" + f + ", ").orElse("") +
            optionalLocation().map(f -> "location=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalParentId().map(f -> "parentId=" + f + ", ").orElse("") +
            optionalTeachersId().map(f -> "teachersId=" + f + ", ").orElse("") +
            optionalCourseId().map(f -> "courseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
