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

    private StringFilter parentId;

    private BooleanFilter status;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter teachersId;

    private LongFilter courseFacultiesId;

    private LongFilter studentId;

    private LongFilter classCourseId;

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
        this.parentId = other.optionalParentId().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.teachersId = other.optionalTeachersId().map(LongFilter::copy).orElse(null);
        this.courseFacultiesId = other.optionalCourseFacultiesId().map(LongFilter::copy).orElse(null);
        this.studentId = other.optionalStudentId().map(LongFilter::copy).orElse(null);
        this.classCourseId = other.optionalClassCourseId().map(LongFilter::copy).orElse(null);
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

    public StringFilter getParentId() {
        return parentId;
    }

    public Optional<StringFilter> optionalParentId() {
        return Optional.ofNullable(parentId);
    }

    public StringFilter parentId() {
        if (parentId == null) {
            setParentId(new StringFilter());
        }
        return parentId;
    }

    public void setParentId(StringFilter parentId) {
        this.parentId = parentId;
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

    public LongFilter getCourseFacultiesId() {
        return courseFacultiesId;
    }

    public Optional<LongFilter> optionalCourseFacultiesId() {
        return Optional.ofNullable(courseFacultiesId);
    }

    public LongFilter courseFacultiesId() {
        if (courseFacultiesId == null) {
            setCourseFacultiesId(new LongFilter());
        }
        return courseFacultiesId;
    }

    public void setCourseFacultiesId(LongFilter courseFacultiesId) {
        this.courseFacultiesId = courseFacultiesId;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public Optional<LongFilter> optionalStudentId() {
        return Optional.ofNullable(studentId);
    }

    public LongFilter studentId() {
        if (studentId == null) {
            setStudentId(new LongFilter());
        }
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getClassCourseId() {
        return classCourseId;
    }

    public Optional<LongFilter> optionalClassCourseId() {
        return Optional.ofNullable(classCourseId);
    }

    public LongFilter classCourseId() {
        if (classCourseId == null) {
            setClassCourseId(new LongFilter());
        }
        return classCourseId;
    }

    public void setClassCourseId(LongFilter classCourseId) {
        this.classCourseId = classCourseId;
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
            Objects.equals(status, that.status) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(teachersId, that.teachersId) &&
            Objects.equals(courseFacultiesId, that.courseFacultiesId) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(classCourseId, that.classCourseId) &&
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
            status,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            teachersId,
            courseFacultiesId,
            studentId,
            classCourseId,
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
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalTeachersId().map(f -> "teachersId=" + f + ", ").orElse("") +
            optionalCourseFacultiesId().map(f -> "courseFacultiesId=" + f + ", ").orElse("") +
            optionalStudentId().map(f -> "studentId=" + f + ", ").orElse("") +
            optionalClassCourseId().map(f -> "classCourseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
