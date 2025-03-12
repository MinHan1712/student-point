package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import student.point.domain.enumeration.ClassesType;
import student.point.domain.enumeration.DeliveryMode;

/**
 * A Classes.
 */
@Entity
@Table(name = "classes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "class_code")
    private String classCode;

    @Column(name = "class_name")
    private String className;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "number_of_sessions")
    private Integer numberOfSessions;

    @Column(name = "total_number_of_students")
    private Integer totalNumberOfStudents;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_type")
    private ClassesType classType;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_mode")
    private DeliveryMode deliveryMode;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "notes")
    private String notes;

    @Column(name = "description")
    private String description;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classes")
    @JsonIgnoreProperties(value = { "student", "classes" }, allowSetters = true)
    private Set<ClassRegistration> classRegistrations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classes")
    @JsonIgnoreProperties(value = { "student", "classes" }, allowSetters = true)
    private Set<Grades> grades = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classes", "faculties" }, allowSetters = true)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classes", "faculties" }, allowSetters = true)
    private Teachers teachers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassCode() {
        return this.classCode;
    }

    public Classes classCode(String classCode) {
        this.setClassCode(classCode);
        return this;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return this.className;
    }

    public Classes className(String className) {
        this.setClassName(className);
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassroom() {
        return this.classroom;
    }

    public Classes classroom(String classroom) {
        this.setClassroom(classroom);
        return this;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getCredits() {
        return this.credits;
    }

    public Classes credits(Integer credits) {
        this.setCredits(credits);
        return this;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getNumberOfSessions() {
        return this.numberOfSessions;
    }

    public Classes numberOfSessions(Integer numberOfSessions) {
        this.setNumberOfSessions(numberOfSessions);
        return this;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public Integer getTotalNumberOfStudents() {
        return this.totalNumberOfStudents;
    }

    public Classes totalNumberOfStudents(Integer totalNumberOfStudents) {
        this.setTotalNumberOfStudents(totalNumberOfStudents);
        return this;
    }

    public void setTotalNumberOfStudents(Integer totalNumberOfStudents) {
        this.totalNumberOfStudents = totalNumberOfStudents;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Classes startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Classes endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public ClassesType getClassType() {
        return this.classType;
    }

    public Classes classType(ClassesType classType) {
        this.setClassType(classType);
        return this;
    }

    public void setClassType(ClassesType classType) {
        this.classType = classType;
    }

    public DeliveryMode getDeliveryMode() {
        return this.deliveryMode;
    }

    public Classes deliveryMode(DeliveryMode deliveryMode) {
        this.setDeliveryMode(deliveryMode);
        return this;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Classes status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public Classes notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return this.description;
    }

    public Classes description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public Classes academicYear(String academicYear) {
        this.setAcademicYear(academicYear);
        return this;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Classes parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Classes createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Classes createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Classes lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Classes lastModifiedDate(Instant lastModifiedDate) {
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
            this.classRegistrations.forEach(i -> i.setClasses(null));
        }
        if (classRegistrations != null) {
            classRegistrations.forEach(i -> i.setClasses(this));
        }
        this.classRegistrations = classRegistrations;
    }

    public Classes classRegistrations(Set<ClassRegistration> classRegistrations) {
        this.setClassRegistrations(classRegistrations);
        return this;
    }

    public Classes addClassRegistration(ClassRegistration classRegistration) {
        this.classRegistrations.add(classRegistration);
        classRegistration.setClasses(this);
        return this;
    }

    public Classes removeClassRegistration(ClassRegistration classRegistration) {
        this.classRegistrations.remove(classRegistration);
        classRegistration.setClasses(null);
        return this;
    }

    public Set<Grades> getGrades() {
        return this.grades;
    }

    public void setGrades(Set<Grades> grades) {
        if (this.grades != null) {
            this.grades.forEach(i -> i.setClasses(null));
        }
        if (grades != null) {
            grades.forEach(i -> i.setClasses(this));
        }
        this.grades = grades;
    }

    public Classes grades(Set<Grades> grades) {
        this.setGrades(grades);
        return this;
    }

    public Classes addGrades(Grades grades) {
        this.grades.add(grades);
        grades.setClasses(this);
        return this;
    }

    public Classes removeGrades(Grades grades) {
        this.grades.remove(grades);
        grades.setClasses(null);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classes course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Teachers getTeachers() {
        return this.teachers;
    }

    public void setTeachers(Teachers teachers) {
        this.teachers = teachers;
    }

    public Classes teachers(Teachers teachers) {
        this.setTeachers(teachers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classes)) {
            return false;
        }
        return getId() != null && getId().equals(((Classes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classes{" +
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
            "}";
    }
}
