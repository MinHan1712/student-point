package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link student.point.domain.Faculties} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacultiesDTO implements Serializable {

    private Long id;

    private String facultyCode;

    private String facultyName;

    private String description;

    private Instant establishedDate;

    private String phoneNumber;

    private String location;

    private String notes;

    private Long parentId;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Instant establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacultiesDTO)) {
            return false;
        }

        FacultiesDTO facultiesDTO = (FacultiesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, facultiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacultiesDTO{" +
            "id=" + getId() +
            ", facultyCode='" + getFacultyCode() + "'" +
            ", facultyName='" + getFacultyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", establishedDate='" + getEstablishedDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", notes='" + getNotes() + "'" +
            ", parentId=" + getParentId() +
            ", course=" + getCourse() +
            "}";
    }
}
