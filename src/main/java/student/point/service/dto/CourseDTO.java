package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.CourseType;

/**
 * A DTO for the {@link student.point.domain.Course} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseDTO implements Serializable {

    private Long id;

    private String courseCode;

    private String courseTitle;

    private Instant credits;

    private Integer lecture;

    private Integer tutorialDiscussion;

    private Integer practical;

    private Integer laboratory;

    private Integer selfStudy;

    private Integer numberOfSessions;

    private CourseType courseType;

    private String notes;

    private Boolean status;

    private String semester;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Instant getCredits() {
        return credits;
    }

    public void setCredits(Instant credits) {
        this.credits = credits;
    }

    public Integer getLecture() {
        return lecture;
    }

    public void setLecture(Integer lecture) {
        this.lecture = lecture;
    }

    public Integer getTutorialDiscussion() {
        return tutorialDiscussion;
    }

    public void setTutorialDiscussion(Integer tutorialDiscussion) {
        this.tutorialDiscussion = tutorialDiscussion;
    }

    public Integer getPractical() {
        return practical;
    }

    public void setPractical(Integer practical) {
        this.practical = practical;
    }

    public Integer getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Integer laboratory) {
        this.laboratory = laboratory;
    }

    public Integer getSelfStudy() {
        return selfStudy;
    }

    public void setSelfStudy(Integer selfStudy) {
        this.selfStudy = selfStudy;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", courseCode='" + getCourseCode() + "'" +
            ", courseTitle='" + getCourseTitle() + "'" +
            ", credits='" + getCredits() + "'" +
            ", lecture=" + getLecture() +
            ", tutorialDiscussion=" + getTutorialDiscussion() +
            ", practical=" + getPractical() +
            ", laboratory=" + getLaboratory() +
            ", selfStudy=" + getSelfStudy() +
            ", numberOfSessions=" + getNumberOfSessions() +
            ", courseType='" + getCourseType() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            ", semester='" + getSemester() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
