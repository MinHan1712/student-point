package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import student.point.domain.enumeration.TeacherPosition;
import student.point.domain.enumeration.TeacherQualification;

/**
 * A Teachers.
 */
@Entity
@Table(name = "teachers")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "teachers_code")
    private String teachersCode;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private TeacherPosition position;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private TeacherQualification qualification;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teachers")
    @JsonIgnoreProperties(value = { "classRegistrations", "grades", "course", "teachers" }, allowSetters = true)
    private Set<Classes> classes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "teachers", "course" }, allowSetters = true)
    private Faculties faculties;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Teachers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeachersCode() {
        return this.teachersCode;
    }

    public Teachers teachersCode(String teachersCode) {
        this.setTeachersCode(teachersCode);
        return this;
    }

    public void setTeachersCode(String teachersCode) {
        this.teachersCode = teachersCode;
    }

    public String getName() {
        return this.name;
    }

    public Teachers name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Teachers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Teachers phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Teachers startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Teachers endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public TeacherPosition getPosition() {
        return this.position;
    }

    public Teachers position(TeacherPosition position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    public TeacherQualification getQualification() {
        return this.qualification;
    }

    public Teachers qualification(TeacherQualification qualification) {
        this.setQualification(qualification);
        return this;
    }

    public void setQualification(TeacherQualification qualification) {
        this.qualification = qualification;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Teachers status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public Teachers notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Teachers createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Teachers createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Teachers lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Teachers lastModifiedDate(Instant lastModifiedDate) {
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
            this.classes.forEach(i -> i.setTeachers(null));
        }
        if (classes != null) {
            classes.forEach(i -> i.setTeachers(this));
        }
        this.classes = classes;
    }

    public Teachers classes(Set<Classes> classes) {
        this.setClasses(classes);
        return this;
    }

    public Teachers addClasses(Classes classes) {
        this.classes.add(classes);
        classes.setTeachers(this);
        return this;
    }

    public Teachers removeClasses(Classes classes) {
        this.classes.remove(classes);
        classes.setTeachers(null);
        return this;
    }

    public Faculties getFaculties() {
        return this.faculties;
    }

    public void setFaculties(Faculties faculties) {
        this.faculties = faculties;
    }

    public Teachers faculties(Faculties faculties) {
        this.setFaculties(faculties);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teachers)) {
            return false;
        }
        return getId() != null && getId().equals(((Teachers) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Teachers{" +
            "id=" + getId() +
            ", teachersCode='" + getTeachersCode() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", position='" + getPosition() + "'" +
            ", qualification='" + getQualification() + "'" +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
