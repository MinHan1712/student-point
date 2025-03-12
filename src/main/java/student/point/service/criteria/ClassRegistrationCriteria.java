package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.ClassRegistrationStatus;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.ClassRegistration} entity. This class is used
 * in {@link student.point.web.rest.ClassRegistrationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /class-registrations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassRegistrationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ClassRegistrationStatus
     */
    public static class ClassRegistrationStatusFilter extends Filter<ClassRegistrationStatus> {

        public ClassRegistrationStatusFilter() {}

        public ClassRegistrationStatusFilter(ClassRegistrationStatusFilter filter) {
            super(filter);
        }

        @Override
        public ClassRegistrationStatusFilter copy() {
            return new ClassRegistrationStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter registerDate;

    private ClassRegistrationStatusFilter status;

    private StringFilter remarks;

    private LongFilter studentId;

    private LongFilter classesId;

    private Boolean distinct;

    public ClassRegistrationCriteria() {}

    public ClassRegistrationCriteria(ClassRegistrationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.registerDate = other.optionalRegisterDate().map(InstantFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(ClassRegistrationStatusFilter::copy).orElse(null);
        this.remarks = other.optionalRemarks().map(StringFilter::copy).orElse(null);
        this.studentId = other.optionalStudentId().map(LongFilter::copy).orElse(null);
        this.classesId = other.optionalClassesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ClassRegistrationCriteria copy() {
        return new ClassRegistrationCriteria(this);
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

    public InstantFilter getRegisterDate() {
        return registerDate;
    }

    public Optional<InstantFilter> optionalRegisterDate() {
        return Optional.ofNullable(registerDate);
    }

    public InstantFilter registerDate() {
        if (registerDate == null) {
            setRegisterDate(new InstantFilter());
        }
        return registerDate;
    }

    public void setRegisterDate(InstantFilter registerDate) {
        this.registerDate = registerDate;
    }

    public ClassRegistrationStatusFilter getStatus() {
        return status;
    }

    public Optional<ClassRegistrationStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public ClassRegistrationStatusFilter status() {
        if (status == null) {
            setStatus(new ClassRegistrationStatusFilter());
        }
        return status;
    }

    public void setStatus(ClassRegistrationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public Optional<StringFilter> optionalRemarks() {
        return Optional.ofNullable(remarks);
    }

    public StringFilter remarks() {
        if (remarks == null) {
            setRemarks(new StringFilter());
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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
        final ClassRegistrationCriteria that = (ClassRegistrationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(registerDate, that.registerDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(classesId, that.classesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerDate, status, remarks, studentId, classesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRegistrationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRegisterDate().map(f -> "registerDate=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalRemarks().map(f -> "remarks=" + f + ", ").orElse("") +
            optionalStudentId().map(f -> "studentId=" + f + ", ").orElse("") +
            optionalClassesId().map(f -> "classesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
