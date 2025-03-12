package student.point.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.EvaluationScores;
import student.point.domain.enumeration.LetterGrade;

/**
 * A DTO for the {@link student.point.domain.Grades} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GradesDTO implements Serializable {

    private Long id;

    private String gradesCode;

    private Integer credit;

    private Integer studyAttempt;

    private Integer examAttempt;

    private BigDecimal processScore;

    private BigDecimal examScore;

    private BigDecimal score10;

    private BigDecimal score4;

    private LetterGrade letterGrade;

    private EvaluationScores evaluation;

    private String notes;

    private Boolean status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private StudentDTO student;

    private ClassesDTO classes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradesCode() {
        return gradesCode;
    }

    public void setGradesCode(String gradesCode) {
        this.gradesCode = gradesCode;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStudyAttempt() {
        return studyAttempt;
    }

    public void setStudyAttempt(Integer studyAttempt) {
        this.studyAttempt = studyAttempt;
    }

    public Integer getExamAttempt() {
        return examAttempt;
    }

    public void setExamAttempt(Integer examAttempt) {
        this.examAttempt = examAttempt;
    }

    public BigDecimal getProcessScore() {
        return processScore;
    }

    public void setProcessScore(BigDecimal processScore) {
        this.processScore = processScore;
    }

    public BigDecimal getExamScore() {
        return examScore;
    }

    public void setExamScore(BigDecimal examScore) {
        this.examScore = examScore;
    }

    public BigDecimal getScore10() {
        return score10;
    }

    public void setScore10(BigDecimal score10) {
        this.score10 = score10;
    }

    public BigDecimal getScore4() {
        return score4;
    }

    public void setScore4(BigDecimal score4) {
        this.score4 = score4;
    }

    public LetterGrade getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(LetterGrade letterGrade) {
        this.letterGrade = letterGrade;
    }

    public EvaluationScores getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationScores evaluation) {
        this.evaluation = evaluation;
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

    public ClassesDTO getClasses() {
        return classes;
    }

    public void setClasses(ClassesDTO classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradesDTO)) {
            return false;
        }

        GradesDTO gradesDTO = (GradesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gradesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradesDTO{" +
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
            ", student=" + getStudent() +
            ", classes=" + getClasses() +
            "}";
    }
}
