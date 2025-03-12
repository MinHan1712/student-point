package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.EvaluationConductScores;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.ConductScores} entity. This class is used
 * in {@link student.point.web.rest.ConductScoresResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conduct-scores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConductScoresCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EvaluationConductScores
     */
    public static class EvaluationConductScoresFilter extends Filter<EvaluationConductScores> {

        public EvaluationConductScoresFilter() {}

        public EvaluationConductScoresFilter(EvaluationConductScoresFilter filter) {
            super(filter);
        }

        @Override
        public EvaluationConductScoresFilter copy() {
            return new EvaluationConductScoresFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter conductScoresCode;

    private StringFilter academicYear;

    private IntegerFilter score;

    private IntegerFilter classification;

    private EvaluationConductScoresFilter evaluation;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter studentId;

    private Boolean distinct;

    public ConductScoresCriteria() {}

    public ConductScoresCriteria(ConductScoresCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.conductScoresCode = other.optionalConductScoresCode().map(StringFilter::copy).orElse(null);
        this.academicYear = other.optionalAcademicYear().map(StringFilter::copy).orElse(null);
        this.score = other.optionalScore().map(IntegerFilter::copy).orElse(null);
        this.classification = other.optionalClassification().map(IntegerFilter::copy).orElse(null);
        this.evaluation = other.optionalEvaluation().map(EvaluationConductScoresFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.studentId = other.optionalStudentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ConductScoresCriteria copy() {
        return new ConductScoresCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConductScoresCode() {
        return conductScoresCode;
    }

    public Optional<StringFilter> optionalConductScoresCode() {
        return Optional.ofNullable(conductScoresCode);
    }

    public StringFilter conductScoresCode() {
        if (conductScoresCode == null) {
            setConductScoresCode(new StringFilter());
        }
        return conductScoresCode;
    }

    public void setConductScoresCode(StringFilter conductScoresCode) {
        this.conductScoresCode = conductScoresCode;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public Optional<StringFilter> optionalAcademicYear() {
        return Optional.ofNullable(academicYear);
    }

    public StringFilter academicYear() {
        if (academicYear == null) {
            setAcademicYear(new StringFilter());
        }
        return academicYear;
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public Optional<IntegerFilter> optionalScore() {
        return Optional.ofNullable(score);
    }

    public IntegerFilter score() {
        if (score == null) {
            setScore(new IntegerFilter());
        }
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public IntegerFilter getClassification() {
        return classification;
    }

    public Optional<IntegerFilter> optionalClassification() {
        return Optional.ofNullable(classification);
    }

    public IntegerFilter classification() {
        if (classification == null) {
            setClassification(new IntegerFilter());
        }
        return classification;
    }

    public void setClassification(IntegerFilter classification) {
        this.classification = classification;
    }

    public EvaluationConductScoresFilter getEvaluation() {
        return evaluation;
    }

    public Optional<EvaluationConductScoresFilter> optionalEvaluation() {
        return Optional.ofNullable(evaluation);
    }

    public EvaluationConductScoresFilter evaluation() {
        if (evaluation == null) {
            setEvaluation(new EvaluationConductScoresFilter());
        }
        return evaluation;
    }

    public void setEvaluation(EvaluationConductScoresFilter evaluation) {
        this.evaluation = evaluation;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<StringFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new StringFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Optional<StringFilter> optionalLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            setLastModifiedBy(new StringFilter());
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Optional<InstantFilter> optionalLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            setLastModifiedDate(new InstantFilter());
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public Optional<LongFilter> optionalStudentId() {
        return Optional.ofNullable(studentId);
    }

    public LongFilter studentId() {
        if (studentId == null) {
            setStudentId(new LongFilter());
        }
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConductScoresCriteria that = (ConductScoresCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(conductScoresCode, that.conductScoresCode) &&
            Objects.equals(academicYear, that.academicYear) &&
            Objects.equals(score, that.score) &&
            Objects.equals(classification, that.classification) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            conductScoresCode,
            academicYear,
            score,
            classification,
            evaluation,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            studentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConductScoresCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalConductScoresCode().map(f -> "conductScoresCode=" + f + ", ").orElse("") +
            optionalAcademicYear().map(f -> "academicYear=" + f + ", ").orElse("") +
            optionalScore().map(f -> "score=" + f + ", ").orElse("") +
            optionalClassification().map(f -> "classification=" + f + ", ").orElse("") +
            optionalEvaluation().map(f -> "evaluation=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalStudentId().map(f -> "studentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
