package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Faculties.
 */
@Entity
@Table(name = "faculties")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Faculties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "faculty_code")
    private String facultyCode;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "description")
    private String description;

    @Column(name = "established_date")
    private Instant establishedDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "notes")
    private String notes;

    @Column(name = "parent_id")
    private Long parentId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculties")
    @JsonIgnoreProperties(value = { "classes", "faculties" }, allowSetters = true)
    private Set<Teachers> teachers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classes", "faculties" }, allowSetters = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Faculties id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacultyCode() {
        return this.facultyCode;
    }

    public Faculties facultyCode(String facultyCode) {
        this.setFacultyCode(facultyCode);
        return this;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return this.facultyName;
    }

    public Faculties facultyName(String facultyName) {
        this.setFacultyName(facultyName);
        return this;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDescription() {
        return this.description;
    }

    public Faculties description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEstablishedDate() {
        return this.establishedDate;
    }

    public Faculties establishedDate(Instant establishedDate) {
        this.setEstablishedDate(establishedDate);
        return this;
    }

    public void setEstablishedDate(Instant establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Faculties phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return this.location;
    }

    public Faculties location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return this.notes;
    }

    public Faculties notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Faculties parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Teachers> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(Set<Teachers> teachers) {
        if (this.teachers != null) {
            this.teachers.forEach(i -> i.setFaculties(null));
        }
        if (teachers != null) {
            teachers.forEach(i -> i.setFaculties(this));
        }
        this.teachers = teachers;
    }

    public Faculties teachers(Set<Teachers> teachers) {
        this.setTeachers(teachers);
        return this;
    }

    public Faculties addTeachers(Teachers teachers) {
        this.teachers.add(teachers);
        teachers.setFaculties(this);
        return this;
    }

    public Faculties removeTeachers(Teachers teachers) {
        this.teachers.remove(teachers);
        teachers.setFaculties(null);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Faculties course(Course course) {
        this.setCourse(course);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Faculties)) {
            return false;
        }
        return getId() != null && getId().equals(((Faculties) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Faculties{" +
            "id=" + getId() +
            ", facultyCode='" + getFacultyCode() + "'" +
            ", facultyName='" + getFacultyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", establishedDate='" + getEstablishedDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", notes='" + getNotes() + "'" +
            ", parentId=" + getParentId() +
            "}";
    }
}
