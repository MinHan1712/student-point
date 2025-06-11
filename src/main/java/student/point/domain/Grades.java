package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import student.point.domain.enumeration.EvaluationScores;
import student.point.domain.enumeration.LetterGrade;

/**
 * A Grades.
 */
@Entity
@Table(name = "grades")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Grades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "grades_code")
    private String gradesCode;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "study_attempt")
    private Integer studyAttempt;

    @Column(name = "exam_attempt")
    private Integer examAttempt;

    @Column(name = "process_score", precision = 21, scale = 2)
    private BigDecimal processScore;

    @Column(name = "exam_score", precision = 21, scale = 2)
    private BigDecimal examScore;

    @Column(name = "score_10", precision = 21, scale = 2)
    private BigDecimal score10;

    @Column(name = "score_4", precision = 21, scale = 2)
    private BigDecimal score4;

    @Enumerated(EnumType.STRING)
    @Column(name = "letter_grade")
    private LetterGrade letterGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation")
    private EvaluationScores evaluation;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "classRegistrations", "conductScores", "statisticsDetails", "grades", "faculties" },
        allowSetters = true
    )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "classRegistrations", "grades", "course", "teachers" }, allowSetters = true)
    private Classes classes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Grades id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradesCode() {
        return this.gradesCode;
    }

    public Grades gradesCode(String gradesCode) {
        this.setGradesCode(gradesCode);
        return this;
    }

    public void setGradesCode(String gradesCode) {
        this.gradesCode = gradesCode;
    }

    public Integer getCredit() {
        return this.credit;
    }

    public Grades credit(Integer credit) {
        this.setCredit(credit);
        return this;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStudyAttempt() {
        return this.studyAttempt;
    }

    public Grades studyAttempt(Integer studyAttempt) {
        this.setStudyAttempt(studyAttempt);
        return this;
    }

    public void setStudyAttempt(Integer studyAttempt) {
        this.studyAttempt = studyAttempt;
    }

    public Integer getExamAttempt() {
        return this.examAttempt;
    }

    public Grades examAttempt(Integer examAttempt) {
        this.setExamAttempt(examAttempt);
        return this;
    }

    public void setExamAttempt(Integer examAttempt) {
        this.examAttempt = examAttempt;
    }

    public BigDecimal getProcessScore() {
        return this.processScore;
    }

    public Grades processScore(BigDecimal processScore) {
        this.setProcessScore(processScore);
        return this;
    }

    public void setProcessScore(BigDecimal processScore) {
        this.processScore = processScore;
    }

    public BigDecimal getExamScore() {
        return this.examScore;
    }

    public Grades examScore(BigDecimal examScore) {
        this.setExamScore(examScore);
        return this;
    }

    public void setExamScore(BigDecimal examScore) {
        this.examScore = examScore;
    }

    public BigDecimal getScore10() {
        return this.score10;
    }

    public Grades score10(BigDecimal score10) {
        this.setScore10(score10);
        return this;
    }

    public void setScore10(BigDecimal score10) {
        this.score10 = score10;
    }

    public BigDecimal getScore4() {
        return this.score4;
    }

    public Grades score4(BigDecimal score4) {
        this.setScore4(score4);
        return this;
    }

    public void setScore4(BigDecimal score4) {
        this.score4 = score4;
    }

    public LetterGrade getLetterGrade() {
        return this.letterGrade;
    }

    public Grades letterGrade(LetterGrade letterGrade) {
        this.setLetterGrade(letterGrade);
        return this;
    }

    public void setLetterGrade(LetterGrade letterGrade) {
        this.letterGrade = letterGrade;
    }

    public EvaluationScores getEvaluation() {
        return this.evaluation;
    }

    public Grades evaluation(EvaluationScores evaluation) {
        this.setEvaluation(evaluation);
        return this;
    }

    public void setEvaluation(EvaluationScores evaluation) {
        this.evaluation = evaluation;
    }

    public String getNotes() {
        return this.notes;
    }

    public Grades notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Grades status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Grades createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Grades createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Grades lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Grades lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Grades student(Student student) {
        this.setStudent(student);
        return this;
    }

    public Classes getClasses() {
        return this.classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Grades classes(Classes classes) {
        this.setClasses(classes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grades)) {
            return false;
        }
        return getId() != null && getId().equals(((Grades) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grades{" +
            "id=" + getId() +
            ", gradesCode='" + getGradesCode() + "'" +
            ", credit=" + getCredit() +
            ", studyAttempt=" + getStudyAttempt() +
            ", examAttempt=" + getExamAttempt() +
            ", processScore=" + getProcessScore() +
            ", examScore=" + getExamScore() +
            ", score10=" + getScore10() +
            ", score4=" + getScore4() +
            ", letterGrade='" + getLetterGrade() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
