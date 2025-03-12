package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.ClassesType;
import student.point.domain.enumeration.DeliveryMode;

/**
 * A DTO for the {@link student.point.domain.Classes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassesDTO implements Serializable {

    private Long id;

    private String classCode;

    private String className;

    private String classroom;

    private Integer credits;

    private Integer numberOfSessions;

    private Integer totalNumberOfStudents;

    private Instant startDate;

    private Instant endDate;

    private ClassesType classType;

    private DeliveryMode deliveryMode;

    private Boolean status;

    private String notes;

    private String description;

    private String academicYear;

    private Long parentId;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private CourseDTO course;

    private TeachersDTO teachers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public Integer getTotalNumberOfStudents() {
        return totalNumberOfStudents;
    }

    public void setTotalNumberOfStudents(Integer totalNumberOfStudents) {
        this.totalNumberOfStudents = totalNumberOfStudents;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public ClassesType getClassType() {
        return classType;
    }

    public void setClassType(ClassesType classType) {
        this.classType = classType;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public TeachersDTO getTeachers() {
        return teachers;
    }

    public void setTeachers(TeachersDTO teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassesDTO)) {
            return false;
        }

        ClassesDTO classesDTO = (ClassesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassesDTO{" +
            "id=" + getId() +
            ", classCode='" + getClassCode() + "'" +
            ", className='" + getClassName() + "'" +
            ", classroom='" + getClassroom() + "'" +
            ", credits=" + getCredits() +
            ", numberOfSessions=" + getNumberOfSessions() +
            ", totalNumberOfStudents=" + getTotalNumberOfStudents() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", classType='" + getClassType() + "'" +
            ", deliveryMode='" + getDeliveryMode() + "'" +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            ", description='" + getDescription() + "'" +
            ", academicYear='" + getAcademicYear() + "'" +
            ", parentId=" + getParentId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", course=" + getCourse() +
            ", teachers=" + getTeachers() +
            "}";
    }
}
