package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.StatisticsType;

/**
 * A DTO for the {@link student.point.domain.Statistics} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticsDTO implements Serializable {

    private Long id;

    private String statisticsCode;

    private String academicYear;

    private StatisticsType type;

    private String notes;

    private Boolean status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticsCode() {
        return statisticsCode;
    }

    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public StatisticsType getType() {
        return type;
    }

    public void setType(StatisticsType type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticsDTO)) {
            return false;
        }

        StatisticsDTO statisticsDTO = (StatisticsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statisticsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsDTO{" +
            "id=" + getId() +
            ", statisticsCode='" + getStatisticsCode() + "'" +
            ", academicYear='" + getAcademicYear() + "'" +
            ", type='" + getType() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
