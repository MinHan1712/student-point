package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import student.point.domain.enumeration.ClassRegistrationStatus;

/**
 * A ClassRegistration.
 */
@Entity
@Table(name = "class_registration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "register_date")
    private Instant registerDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ClassRegistrationStatus status;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classRegistrations", "conductScores", "statisticsDetails", "grades" }, allowSetters = true)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classRegistrations", "grades", "course", "teachers" }, allowSetters = true)
    private Classes classes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClassRegistration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public ClassRegistration registerDate(Instant registerDate) {
        this.setRegisterDate(registerDate);
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public ClassRegistrationStatus getStatus() {
        return this.status;
    }

    public ClassRegistration status(ClassRegistrationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ClassRegistrationStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ClassRegistration remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassRegistration student(Student student) {
        this.setStudent(student);
        return this;
    }

    public Classes getClasses() {
        return this.classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public ClassRegistration classes(Classes classes) {
        this.setClasses(classes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRegistration)) {
            return false;
        }
        return getId() != null && getId().equals(((ClassRegistration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRegistration{" +
            "id=" + getId() +
            ", registerDate='" + getRegisterDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
