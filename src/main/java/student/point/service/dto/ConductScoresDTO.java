package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.EvaluationConductScores;

/**
 * A DTO for the {@link student.point.domain.ConductScores} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConductScoresDTO implements Serializable {

    private Long id;

    private String conductScoresCode;

    private String academicYear;

    private Integer score;

    private Integer classification;

    private EvaluationConductScores evaluation;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private StudentDTO student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConductScoresCode() {
        return conductScoresCode;
    }

    public void setConductScoresCode(String conductScoresCode) {
        this.conductScoresCode = conductScoresCode;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public EvaluationConductScores getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationConductScores evaluation) {
        this.evaluation = evaluation;
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

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConductScoresDTO)) {
            return false;
        }

        ConductScoresDTO conductScoresDTO = (ConductScoresDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conductScoresDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConductScoresDTO{" +
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
            ", student=" + getStudent() +
            "}";
    }
}
