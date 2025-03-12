package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import student.point.domain.enumeration.StudentStatus;
import student.point.domain.enumeration.gender;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private gender gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentStatus status;

    @Column(name = "date_enrollment")
    private Instant dateEnrollment;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "student", "classes" }, allowSetters = true)
    private Set<ClassRegistration> classRegistrations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    private Set<ConductScores> conductScores = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "student", "statistics" }, allowSetters = true)
    private Set<StatisticsDetails> statisticsDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "student", "classes" }, allowSetters = true)
    private Set<Grades> grades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentCode() {
        return this.studentCode;
    }

    public Student studentCode(String studentCode) {
        this.setStudentCode(studentCode);
        return this;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Student fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Student dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public gender getGender() {
        return this.gender;
    }

    public Student gender(gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return this.address;
    }

    public Student address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Student phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Student email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return this.notes;
    }

    public Student notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public StudentStatus getStatus() {
        return this.status;
    }

    public Student status(StudentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public Instant getDateEnrollment() {
        return this.dateEnrollment;
    }

    public Student dateEnrollment(Instant dateEnrollment) {
        this.setDateEnrollment(dateEnrollment);
        return this;
    }

    public void setDateEnrollment(Instant dateEnrollment) {
        this.dateEnrollment = dateEnrollment;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Student createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Student createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Student lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Student lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<ClassRegistration> getClassRegistrations() {
        return this.classRegistrations;
    }

    public void setClassRegistrations(Set<ClassRegistration> classRegistrations) {
        if (this.classRegistrations != null) {
            this.classRegistrations.forEach(i -> i.setStudent(null));
        }
        if (classRegistrations != null) {
            classRegistrations.forEach(i -> i.setStudent(this));
        }
        this.classRegistrations = classRegistrations;
    }

    public Student classRegistrations(Set<ClassRegistration> classRegistrations) {
        this.setClassRegistrations(classRegistrations);
        return this;
    }

    public Student addClassRegistration(ClassRegistration classRegistration) {
        this.classRegistrations.add(classRegistration);
        classRegistration.setStudent(this);
        return this;
    }

    public Student removeClassRegistration(ClassRegistration classRegistration) {
        this.classRegistrations.remove(classRegistration);
        classRegistration.setStudent(null);
        return this;
    }

    public Set<ConductScores> getConductScores() {
        return this.conductScores;
    }

    public void setConductScores(Set<ConductScores> conductScores) {
        if (this.conductScores != null) {
            this.conductScores.forEach(i -> i.setStudent(null));
        }
        if (conductScores != null) {
            conductScores.forEach(i -> i.setStudent(this));
        }
        this.conductScores = conductScores;
    }

    public Student conductScores(Set<ConductScores> conductScores) {
        this.setConductScores(conductScores);
        return this;
    }

    public Student addConductScores(ConductScores conductScores) {
        this.conductScores.add(conductScores);
        conductScores.setStudent(this);
        return this;
    }

    public Student removeConductScores(ConductScores conductScores) {
        this.conductScores.remove(conductScores);
        conductScores.setStudent(null);
        return this;
    }

    public Set<StatisticsDetails> getStatisticsDetails() {
        return this.statisticsDetails;
    }

    public void setStatisticsDetails(Set<StatisticsDetails> statisticsDetails) {
        if (this.statisticsDetails != null) {
            this.statisticsDetails.forEach(i -> i.setStudent(null));
        }
        if (statisticsDetails != null) {
            statisticsDetails.forEach(i -> i.setStudent(this));
        }
        this.statisticsDetails = statisticsDetails;
    }

    public Student statisticsDetails(Set<StatisticsDetails> statisticsDetails) {
        this.setStatisticsDetails(statisticsDetails);
        return this;
    }

    public Student addStatisticsDetails(StatisticsDetails statisticsDetails) {
        this.statisticsDetails.add(statisticsDetails);
        statisticsDetails.setStudent(this);
        return this;
    }

    public Student removeStatisticsDetails(StatisticsDetails statisticsDetails) {
        this.statisticsDetails.remove(statisticsDetails);
        statisticsDetails.setStudent(null);
        return this;
    }

    public Set<Grades> getGrades() {
        return this.grades;
    }

    public void setGrades(Set<Grades> grades) {
        if (this.grades != null) {
            this.grades.forEach(i -> i.setStudent(null));
        }
        if (grades != null) {
            grades.forEach(i -> i.setStudent(this));
        }
        this.grades = grades;
    }

    public Student grades(Set<Grades> grades) {
        this.setGrades(grades);
        return this;
    }

    public Student addGrades(Grades grades) {
        this.grades.add(grades);
        grades.setStudent(this);
        return this;
    }

    public Student removeGrades(Grades grades) {
        this.grades.remove(grades);
        grades.setStudent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return getId() != null && getId().equals(((Student) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentCode='" + getStudentCode() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            ", dateEnrollment='" + getDateEnrollment() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
