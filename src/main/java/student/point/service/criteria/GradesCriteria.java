package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.EvaluationScores;
import student.point.domain.enumeration.LetterGrade;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Grades} entity. This class is used
 * in {@link student.point.web.rest.GradesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /grades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GradesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LetterGrade
     */
    public static class LetterGradeFilter extends Filter<LetterGrade> {

        public LetterGradeFilter() {}

        public LetterGradeFilter(LetterGradeFilter filter) {
            super(filter);
        }

        @Override
        public LetterGradeFilter copy() {
            return new LetterGradeFilter(this);
        }
    }

    /**
     * Class for filtering EvaluationScores
     */
    public static class EvaluationScoresFilter extends Filter<EvaluationScores> {

        public EvaluationScoresFilter() {}

        public EvaluationScoresFilter(EvaluationScoresFilter filter) {
            super(filter);
        }

        @Override
        public EvaluationScoresFilter copy() {
            return new EvaluationScoresFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter gradesCode;

    private IntegerFilter credit;

    private IntegerFilter studyAttempt;

    private IntegerFilter examAttempt;

    private BigDecimalFilter processScore;

    private BigDecimalFilter examScore;

    private BigDecimalFilter score10;

    private BigDecimalFilter score4;

    private LetterGradeFilter letterGrade;

    private EvaluationScoresFilter evaluation;

    private StringFilter notes;

    private BooleanFilter status;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter studentId;

    private LongFilter classesId;

    private Boolean distinct;

    public GradesCriteria() {}

    public GradesCriteria(GradesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.gradesCode = other.optionalGradesCode().map(StringFilter::copy).orElse(null);
        this.credit = other.optionalCredit().map(IntegerFilter::copy).orElse(null);
        this.studyAttempt = other.optionalStudyAttempt().map(IntegerFilter::copy).orElse(null);
        this.examAttempt = other.optionalExamAttempt().map(IntegerFilter::copy).orElse(null);
        this.processScore = other.optionalProcessScore().map(BigDecimalFilter::copy).orElse(null);
        this.examScore = other.optionalExamScore().map(BigDecimalFilter::copy).orElse(null);
        this.score10 = other.optionalScore10().map(BigDecimalFilter::copy).orElse(null);
        this.score4 = other.optionalScore4().map(BigDecimalFilter::copy).orElse(null);
        this.letterGrade = other.optionalLetterGrade().map(LetterGradeFilter::copy).orElse(null);
        this.evaluation = other.optionalEvaluation().map(EvaluationScoresFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.studentId = other.optionalStudentId().map(LongFilter::copy).orElse(null);
        this.classesId = other.optionalClassesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public GradesCriteria copy() {
        return new GradesCriteria(this);
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

    public StringFilter getGradesCode() {
        return gradesCode;
    }

    public Optional<StringFilter> optionalGradesCode() {
        return Optional.ofNullable(gradesCode);
    }

    public StringFilter gradesCode() {
        if (gradesCode == null) {
            setGradesCode(new StringFilter());
        }
        return gradesCode;
    }

    public void setGradesCode(StringFilter gradesCode) {
        this.gradesCode = gradesCode;
    }

    public IntegerFilter getCredit() {
        return credit;
    }

    public Optional<IntegerFilter> optionalCredit() {
        return Optional.ofNullable(credit);
    }

    public IntegerFilter credit() {
        if (credit == null) {
            setCredit(new IntegerFilter());
        }
        return credit;
    }

    public void setCredit(IntegerFilter credit) {
        this.credit = credit;
    }

    public IntegerFilter getStudyAttempt() {
        return studyAttempt;
    }

    public Optional<IntegerFilter> optionalStudyAttempt() {
        return Optional.ofNullable(studyAttempt);
    }

    public IntegerFilter studyAttempt() {
        if (studyAttempt == null) {
            setStudyAttempt(new IntegerFilter());
        }
        return studyAttempt;
    }

    public void setStudyAttempt(IntegerFilter studyAttempt) {
        this.studyAttempt = studyAttempt;
    }

    public IntegerFilter getExamAttempt() {
        return examAttempt;
    }

    public Optional<IntegerFilter> optionalExamAttempt() {
        return Optional.ofNullable(examAttempt);
    }

    public IntegerFilter examAttempt() {
        if (examAttempt == null) {
            setExamAttempt(new IntegerFilter());
        }
        return examAttempt;
    }

    public void setExamAttempt(IntegerFilter examAttempt) {
        this.examAttempt = examAttempt;
    }

    public BigDecimalFilter getProcessScore() {
        return processScore;
    }

    public Optional<BigDecimalFilter> optionalProcessScore() {
        return Optional.ofNullable(processScore);
    }

    public BigDecimalFilter processScore() {
        if (processScore == null) {
            setProcessScore(new BigDecimalFilter());
        }
        return processScore;
    }

    public void setProcessScore(BigDecimalFilter processScore) {
        this.processScore = processScore;
    }

    public BigDecimalFilter getExamScore() {
        return examScore;
    }

    public Optional<BigDecimalFilter> optionalExamScore() {
        return Optional.ofNullable(examScore);
    }

    public BigDecimalFilter examScore() {
        if (examScore == null) {
            setExamScore(new BigDecimalFilter());
        }
        return examScore;
    }

    public void setExamScore(BigDecimalFilter examScore) {
        this.examScore = examScore;
    }

    public BigDecimalFilter getScore10() {
        return score10;
    }

    public Optional<BigDecimalFilter> optionalScore10() {
        return Optional.ofNullable(score10);
    }

    public BigDecimalFilter score10() {
        if (score10 == null) {
            setScore10(new BigDecimalFilter());
        }
        return score10;
    }

    public void setScore10(BigDecimalFilter score10) {
        this.score10 = score10;
    }

    public BigDecimalFilter getScore4() {
        return score4;
    }

    public Optional<BigDecimalFilter> optionalScore4() {
        return Optional.ofNullable(score4);
    }

    public BigDecimalFilter score4() {
        if (score4 == null) {
            setScore4(new BigDecimalFilter());
        }
        return score4;
    }

    public void setScore4(BigDecimalFilter score4) {
        this.score4 = score4;
    }

    public LetterGradeFilter getLetterGrade() {
        return letterGrade;
    }

    public Optional<LetterGradeFilter> optionalLetterGrade() {
        return Optional.ofNullable(letterGrade);
    }

    public LetterGradeFilter letterGrade() {
        if (letterGrade == null) {
            setLetterGrade(new LetterGradeFilter());
        }
        return letterGrade;
    }

    public void setLetterGrade(LetterGradeFilter letterGrade) {
        this.letterGrade = letterGrade;
    }

    public EvaluationScoresFilter getEvaluation() {
        return evaluation;
    }

    public Optional<EvaluationScoresFilter> optionalEvaluation() {
        return Optional.ofNullable(evaluation);
    }

    public EvaluationScoresFilter evaluation() {
        if (evaluation == null) {
            setEvaluation(new EvaluationScoresFilter());
        }
        return evaluation;
    }

    public void setEvaluation(EvaluationScoresFilter evaluation) {
        this.evaluation = evaluation;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public Optional<BooleanFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public BooleanFilter status() {
        if (status == null) {
            setStatus(new BooleanFilter());
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
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

    public LongFilter getClassesId() {
        return classesId;
    }

    public Optional<LongFilter> optionalClassesId() {
        return Optional.ofNullable(classesId);
    }

    public LongFilter classesId() {
        if (classesId == null) {
            setClassesId(new LongFilter());
        }
        return classesId;
    }

    public void setClassesId(LongFilter classesId) {
        this.classesId = classesId;
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
        final GradesCriteria that = (GradesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(gradesCode, that.gradesCode) &&
            Objects.equals(credit, that.credit) &&
            Objects.equals(studyAttempt, that.studyAttempt) &&
            Objects.equals(examAttempt, that.examAttempt) &&
            Objects.equals(processScore, that.processScore) &&
            Objects.equals(examScore, that.examScore) &&
            Objects.equals(score10, that.score10) &&
            Objects.equals(score4, that.score4) &&
            Objects.equals(letterGrade, that.letterGrade) &&
            Objects.equals(evaluation, that.evaluation) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(classesId, that.classesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            gradesCode,
            credit,
            studyAttempt,
            examAttempt,
            processScore,
            examScore,
            score10,
            score4,
            letterGrade,
            evaluation,
            notes,
            status,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            studentId,
            classesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalGradesCode().map(f -> "gradesCode=" + f + ", ").orElse("") +
            optionalCredit().map(f -> "credit=" + f + ", ").orElse("") +
            optionalStudyAttempt().map(f -> "studyAttempt=" + f + ", ").orElse("") +
            optionalExamAttempt().map(f -> "examAttempt=" + f + ", ").orElse("") +
            optionalProcessScore().map(f -> "processScore=" + f + ", ").orElse("") +
            optionalExamScore().map(f -> "examScore=" + f + ", ").orElse("") +
            optionalScore10().map(f -> "score10=" + f + ", ").orElse("") +
            optionalScore4().map(f -> "score4=" + f + ", ").orElse("") +
            optionalLetterGrade().map(f -> "letterGrade=" + f + ", ").orElse("") +
            optionalEvaluation().map(f -> "evaluation=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalStudentId().map(f -> "studentId=" + f + ", ").orElse("") +
            optionalClassesId().map(f -> "classesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
