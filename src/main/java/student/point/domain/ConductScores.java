package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import student.point.domain.enumeration.EvaluationConductScores;
import student.point.listener.ClassListener;
import student.point.listener.ConductScoreListener;

/**
 * A ConductScores.
 */
@Entity
@Table(name = "conduct_scores")
@EntityListeners(ConductScoreListener.class)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConductScores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "conduct_scores_code")
    private String conductScoresCode;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "score")
    private Integer score;

    @Column(name = "classification")
    private Integer classification;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation")
    private EvaluationConductScores evaluation;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConductScores id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConductScoresCode() {
        return this.conductScoresCode;
    }

    public ConductScores conductScoresCode(String conductScoresCode) {
        this.setConductScoresCode(conductScoresCode);
        return this;
    }

    public void setConductScoresCode(String conductScoresCode) {
        this.conductScoresCode = conductScoresCode;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public ConductScores academicYear(String academicYear) {
        this.setAcademicYear(academicYear);
        return this;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getScore() {
        return this.score;
    }

    public ConductScores score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getClassification() {
        return this.classification;
    }

    public ConductScores classification(Integer classification) {
        this.setClassification(classification);
        return this;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public EvaluationConductScores getEvaluation() {
        return this.evaluation;
    }

    public ConductScores evaluation(EvaluationConductScores evaluation) {
        this.setEvaluation(evaluation);
        return this;
    }

    public void setEvaluation(EvaluationConductScores evaluation) {
        this.evaluation = evaluation;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ConductScores createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ConductScores createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ConductScores lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ConductScores lastModifiedDate(Instant lastModifiedDate) {
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

    public ConductScores student(Student student) {
        this.setStudent(student);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConductScores)) {
            return false;
        }
        return getId() != null && getId().equals(((ConductScores) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConductScores{" +
            "id=" + getId() +
            ", conductScoresCode='" + getConductScoresCode() + "'" +
            ", academicYear='" + getAcademicYear() + "'" +
            ", score=" + getScore() +
            ", classification=" + getClassification() +
            ", evaluation='" + getEvaluation() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
