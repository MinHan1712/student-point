package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.StudentStatus;
import student.point.domain.enumeration.gender;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Student} entity. This class is used
 * in {@link student.point.web.rest.StudentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /students?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering gender
     */
    public static class genderFilter extends Filter<gender> {

        public genderFilter() {}

        public genderFilter(genderFilter filter) {
            super(filter);
        }

        @Override
        public genderFilter copy() {
            return new genderFilter(this);
        }
    }

    /**
     * Class for filtering StudentStatus
     */
    public static class StudentStatusFilter extends Filter<StudentStatus> {

        public StudentStatusFilter() {}

        public StudentStatusFilter(StudentStatusFilter filter) {
            super(filter);
        }

        @Override
        public StudentStatusFilter copy() {
            return new StudentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentCode;

    private StringFilter fullName;

    private InstantFilter dateOfBirth;

    private genderFilter gender;

    private StringFilter address;

    private StringFilter phoneNumber;

    private StringFilter email;

    private StringFilter notes;

    private StudentStatusFilter status;

    private InstantFilter dateEnrollment;

    private StringFilter clasName;

    private StringFilter courseYear;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter classRegistrationId;

    private LongFilter conductScoresId;

    private LongFilter statisticsDetailsId;

    private LongFilter gradesId;

    private LongFilter facultiesId;

    private Boolean distinct;

    public StudentCriteria() {}

    public StudentCriteria(StudentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.studentCode = other.optionalStudentCode().map(StringFilter::copy).orElse(null);
        this.fullName = other.optionalFullName().map(StringFilter::copy).orElse(null);
        this.dateOfBirth = other.optionalDateOfBirth().map(InstantFilter::copy).orElse(null);
        this.gender = other.optionalGender().map(genderFilter::copy).orElse(null);
        this.address = other.optionalAddress().map(StringFilter::copy).orElse(null);
        this.phoneNumber = other.optionalPhoneNumber().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StudentStatusFilter::copy).orElse(null);
        this.dateEnrollment = other.optionalDateEnrollment().map(InstantFilter::copy).orElse(null);
        this.clasName = other.optionalClasName().map(StringFilter::copy).orElse(null);
        this.courseYear = other.optionalCourseYear().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.classRegistrationId = other.optionalClassRegistrationId().map(LongFilter::copy).orElse(null);
        this.conductScoresId = other.optionalConductScoresId().map(LongFilter::copy).orElse(null);
        this.statisticsDetailsId = other.optionalStatisticsDetailsId().map(LongFilter::copy).orElse(null);
        this.gradesId = other.optionalGradesId().map(LongFilter::copy).orElse(null);
        this.facultiesId = other.optionalFacultiesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StudentCriteria copy() {
        return new StudentCriteria(this);
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

    public StringFilter getStudentCode() {
        return studentCode;
    }

    public Optional<StringFilter> optionalStudentCode() {
        return Optional.ofNullable(studentCode);
    }

    public StringFilter studentCode() {
        if (studentCode == null) {
            setStudentCode(new StringFilter());
        }
        return studentCode;
    }

    public void setStudentCode(StringFilter studentCode) {
        this.studentCode = studentCode;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public Optional<StringFilter> optionalFullName() {
        return Optional.ofNullable(fullName);
    }

    public StringFilter fullName() {
        if (fullName == null) {
            setFullName(new StringFilter());
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public InstantFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public Optional<InstantFilter> optionalDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

    public InstantFilter dateOfBirth() {
        if (dateOfBirth == null) {
            setDateOfBirth(new InstantFilter());
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(InstantFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public genderFilter getGender() {
        return gender;
    }

    public Optional<genderFilter> optionalGender() {
        return Optional.ofNullable(gender);
    }

    public genderFilter gender() {
        if (gender == null) {
            setGender(new genderFilter());
        }
        return gender;
    }

    public void setGender(genderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getAddress() {
        return address;
    }

    public Optional<StringFilter> optionalAddress() {
        return Optional.ofNullable(address);
    }

    public StringFilter address() {
        if (address == null) {
            setAddress(new StringFilter());
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    public StudentStatusFilter getStatus() {
        return status;
    }

    public Optional<StudentStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StudentStatusFilter status() {
        if (status == null) {
            setStatus(new StudentStatusFilter());
        }
        return status;
    }

    public void setStatus(StudentStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getDateEnrollment() {
        return dateEnrollment;
    }

    public Optional<InstantFilter> optionalDateEnrollment() {
        return Optional.ofNullable(dateEnrollment);
    }

    public InstantFilter dateEnrollment() {
        if (dateEnrollment == null) {
            setDateEnrollment(new InstantFilter());
        }
        return dateEnrollment;
    }

    public void setDateEnrollment(InstantFilter dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public StringFilter getClasName() {
        return clasName;
    }

    public Optional<StringFilter> optionalClasName() {
        return Optional.ofNullable(clasName);
    }

    public StringFilter clasName() {
        if (clasName == null) {
            setClasName(new StringFilter());
        }
        return clasName;
    }

    public void setClasName(StringFilter clasName) {
        this.clasName = clasName;
    }

    public StringFilter getCourseYear() {
        return courseYear;
    }

    public Optional<StringFilter> optionalCourseYear() {
        return Optional.ofNullable(courseYear);
    }

    public StringFilter courseYear() {
        if (courseYear == null) {
            setCourseYear(new StringFilter());
        }
        return courseYear;
    }

    public void setCourseYear(StringFilter courseYear) {
        this.courseYear = courseYear;
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

    public LongFilter getConductScoresId() {
        return conductScoresId;
    }

    public Optional<LongFilter> optionalConductScoresId() {
        return Optional.ofNullable(conductScoresId);
    }

    public LongFilter conductScoresId() {
        if (conductScoresId == null) {
            setConductScoresId(new LongFilter());
        }
        return conductScoresId;
    }

    public void setConductScoresId(LongFilter conductScoresId) {
        this.conductScoresId = conductScoresId;
    }

    public LongFilter getStatisticsDetailsId() {
        return statisticsDetailsId;
    }

    public Optional<LongFilter> optionalStatisticsDetailsId() {
        return Optional.ofNullable(statisticsDetailsId);
    }

    public LongFilter statisticsDetailsId() {
        if (statisticsDetailsId == null) {
            setStatisticsDetailsId(new LongFilter());
        }
        return statisticsDetailsId;
    }

    public void setStatisticsDetailsId(LongFilter statisticsDetailsId) {
        this.statisticsDetailsId = statisticsDetailsId;
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
        final StudentCriteria that = (StudentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(studentCode, that.studentCode) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(email, that.email) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dateEnrollment, that.dateEnrollment) &&
            Objects.equals(clasName, that.clasName) &&
            Objects.equals(courseYear, that.courseYear) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(classRegistrationId, that.classRegistrationId) &&
            Objects.equals(conductScoresId, that.conductScoresId) &&
            Objects.equals(statisticsDetailsId, that.statisticsDetailsId) &&
            Objects.equals(gradesId, that.gradesId) &&
            Objects.equals(facultiesId, that.facultiesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            studentCode,
            fullName,
            dateOfBirth,
            gender,
            address,
            phoneNumber,
            email,
            notes,
            status,
            dateEnrollment,
            clasName,
            courseYear,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            classRegistrationId,
            conductScoresId,
            statisticsDetailsId,
            gradesId,
            facultiesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStudentCode().map(f -> "studentCode=" + f + ", ").orElse("") +
            optionalFullName().map(f -> "fullName=" + f + ", ").orElse("") +
            optionalDateOfBirth().map(f -> "dateOfBirth=" + f + ", ").orElse("") +
            optionalGender().map(f -> "gender=" + f + ", ").orElse("") +
            optionalAddress().map(f -> "address=" + f + ", ").orElse("") +
            optionalPhoneNumber().map(f -> "phoneNumber=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalDateEnrollment().map(f -> "dateEnrollment=" + f + ", ").orElse("") +
            optionalClasName().map(f -> "clasName=" + f + ", ").orElse("") +
            optionalCourseYear().map(f -> "courseYear=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalClassRegistrationId().map(f -> "classRegistrationId=" + f + ", ").orElse("") +
            optionalConductScoresId().map(f -> "conductScoresId=" + f + ", ").orElse("") +
            optionalStatisticsDetailsId().map(f -> "statisticsDetailsId=" + f + ", ").orElse("") +
            optionalGradesId().map(f -> "gradesId=" + f + ", ").orElse("") +
            optionalFacultiesId().map(f -> "facultiesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
