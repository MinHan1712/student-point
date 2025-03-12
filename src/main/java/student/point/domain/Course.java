package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import student.point.domain.enumeration.CourseType;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "credits")
    private Instant credits;

    @Column(name = "lecture")
    private Integer lecture;

    @Column(name = "tutorial_discussion")
    private Integer tutorialDiscussion;

    @Column(name = "practical")
    private Integer practical;

    @Column(name = "laboratory")
    private Integer laboratory;

    @Column(name = "self_study")
    private Integer selfStudy;

    @Column(name = "number_of_sessions")
    private Integer numberOfSessions;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type")
    private CourseType courseType;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "semester")
    private String semester;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @JsonIgnoreProperties(value = { "classRegistrations", "grades", "course", "teachers" }, allowSetters = true)
    private Set<Classes> classes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @JsonIgnoreProperties(value = { "teachers", "course" }, allowSetters = true)
    private Set<Faculties> faculties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public Course courseCode(String courseCode) {
        this.setCourseCode(courseCode);
        return this;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public Course courseTitle(String courseTitle) {
        this.setCourseTitle(courseTitle);
        return this;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Instant getCredits() {
        return this.credits;
    }

    public Course credits(Instant credits) {
        this.setCredits(credits);
        return this;
    }

    public void setCredits(Instant credits) {
        this.credits = credits;
    }

    public Integer getLecture() {
        return this.lecture;
    }

    public Course lecture(Integer lecture) {
        this.setLecture(lecture);
        return this;
    }

    public void setLecture(Integer lecture) {
        this.lecture = lecture;
    }

    public Integer getTutorialDiscussion() {
        return this.tutorialDiscussion;
    }

    public Course tutorialDiscussion(Integer tutorialDiscussion) {
        this.setTutorialDiscussion(tutorialDiscussion);
        return this;
    }

    public void setTutorialDiscussion(Integer tutorialDiscussion) {
        this.tutorialDiscussion = tutorialDiscussion;
    }

    public Integer getPractical() {
        return this.practical;
    }

    public Course practical(Integer practical) {
        this.setPractical(practical);
        return this;
    }

    public void setPractical(Integer practical) {
        this.practical = practical;
    }

    public Integer getLaboratory() {
        return this.laboratory;
    }

    public Course laboratory(Integer laboratory) {
        this.setLaboratory(laboratory);
        return this;
    }

    public void setLaboratory(Integer laboratory) {
        this.laboratory = laboratory;
    }

    public Integer getSelfStudy() {
        return this.selfStudy;
    }

    public Course selfStudy(Integer selfStudy) {
        this.setSelfStudy(selfStudy);
        return this;
    }

    public void setSelfStudy(Integer selfStudy) {
        this.selfStudy = selfStudy;
    }

    public Integer getNumberOfSessions() {
        return this.numberOfSessions;
    }

    public Course numberOfSessions(Integer numberOfSessions) {
        this.setNumberOfSessions(numberOfSessions);
        return this;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public CourseType getCourseType() {
        return this.courseType;
    }

    public Course courseType(CourseType courseType) {
        this.setCourseType(courseType);
        return this;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public String getNotes() {
        return this.notes;
    }

    public Course notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Course status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSemester() {
        return this.semester;
    }

    public Course semester(String semester) {
        this.setSemester(semester);
        return this;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Course createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Course createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Course lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Course lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Classes> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classes> classes) {
        if (this.classes != null) {
            this.classes.forEach(i -> i.setCourse(null));
        }
        if (classes != null) {
            classes.forEach(i -> i.setCourse(this));
        }
        this.classes = classes;
    }

    public Course classes(Set<Classes> classes) {
        this.setClasses(classes);
        return this;
    }

    public Course addClasses(Classes classes) {
        this.classes.add(classes);
        classes.setCourse(this);
        return this;
    }

    public Course removeClasses(Classes classes) {
        this.classes.remove(classes);
        classes.setCourse(null);
        return this;
    }

    public Set<Faculties> getFaculties() {
        return this.faculties;
    }

    public void setFaculties(Set<Faculties> faculties) {
        if (this.faculties != null) {
            this.faculties.forEach(i -> i.setCourse(null));
        }
        if (faculties != null) {
            faculties.forEach(i -> i.setCourse(this));
        }
        this.faculties = faculties;
    }

    public Course faculties(Set<Faculties> faculties) {
        this.setFaculties(faculties);
        return this;
    }

    public Course addFaculties(Faculties faculties) {
        this.faculties.add(faculties);
        faculties.setCourse(this);
        return this;
    }

    public Course removeFaculties(Faculties faculties) {
        this.faculties.remove(faculties);
        faculties.setCourse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return getId() != null && getId().equals(((Course) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
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
