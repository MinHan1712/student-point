package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import student.point.domain.enumeration.StatisticsType;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.Statistics} entity. This class is used
 * in {@link student.point.web.rest.StatisticsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /statistics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatisticsType
     */
    public static class StatisticsTypeFilter extends Filter<StatisticsType> {

        public StatisticsTypeFilter() {}

        public StatisticsTypeFilter(StatisticsTypeFilter filter) {
            super(filter);
        }

        @Override
        public StatisticsTypeFilter copy() {
            return new StatisticsTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statisticsCode;

    private StringFilter academicYear;

    private StatisticsTypeFilter type;

    private StringFilter notes;

    private BooleanFilter status;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter statisticsDetailsId;

    private Boolean distinct;

    public StatisticsCriteria() {}

    public StatisticsCriteria(StatisticsCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.statisticsCode = other.optionalStatisticsCode().map(StringFilter::copy).orElse(null);
        this.academicYear = other.optionalAcademicYear().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(StatisticsTypeFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(BooleanFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.statisticsDetailsId = other.optionalStatisticsDetailsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StatisticsCriteria copy() {
        return new StatisticsCriteria(this);
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

    public StringFilter getStatisticsCode() {
        return statisticsCode;
    }

    public Optional<StringFilter> optionalStatisticsCode() {
        return Optional.ofNullable(statisticsCode);
    }

    public StringFilter statisticsCode() {
        if (statisticsCode == null) {
            setStatisticsCode(new StringFilter());
        }
        return statisticsCode;
    }

    public void setStatisticsCode(StringFilter statisticsCode) {
        this.statisticsCode = statisticsCode;
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

    public StatisticsTypeFilter getType() {
        return type;
    }

    public Optional<StatisticsTypeFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public StatisticsTypeFilter type() {
        if (type == null) {
            setType(new StatisticsTypeFilter());
        }
        return type;
    }

    public void setType(StatisticsTypeFilter type) {
        this.type = type;
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

    public LongFilter getStatisticsDetailsId() {
        return statisticsDetailsId;
    }

    public Optional<LongFilter> optionalStatisticsDetailsId() {
        return Optional.ofNullable(statisticsDetailsId);
    }

    public LongFilter statisticsDetailsId() {
        if (statisticsDetailsId == null) {
            setStatisticsDetailsId(new LongFilter());
        }
        return statisticsDetailsId;
    }

    public void setStatisticsDetailsId(LongFilter statisticsDetailsId) {
        this.statisticsDetailsId = statisticsDetailsId;
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
        final StatisticsCriteria that = (StatisticsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statisticsCode, that.statisticsCode) &&
            Objects.equals(academicYear, that.academicYear) &&
            Objects.equals(type, that.type) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(statisticsDetailsId, that.statisticsDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            statisticsCode,
            academicYear,
            type,
            notes,
            status,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            statisticsDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStatisticsCode().map(f -> "statisticsCode=" + f + ", ").orElse("") +
            optionalAcademicYear().map(f -> "academicYear=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalStatisticsDetailsId().map(f -> "statisticsDetailsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
