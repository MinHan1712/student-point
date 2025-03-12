package student.point.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link student.point.domain.StatisticsDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticsDetailsDTO implements Serializable {

    private Long id;

    private String code;

    private BigDecimal totalScholarship;

    private Instant graduationDate;

    private String notes;

    private Boolean status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private StudentDTO student;

    private StatisticsDTO statistics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotalScholarship() {
        return totalScholarship;
    }

    public void setTotalScholarship(BigDecimal totalScholarship) {
        this.totalScholarship = totalScholarship;
    }

    public Instant getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Instant graduationDate) {
        this.graduationDate = graduationDate;
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

    public StatisticsDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsDTO statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticsDetailsDTO)) {
            return false;
        }

        StatisticsDetailsDTO statisticsDetailsDTO = (StatisticsDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statisticsDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsDetailsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", totalScholarship=" + getTotalScholarship() +
            ", graduationDate='" + getGraduationDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", student=" + getStudent() +
            ", statistics=" + getStatistics() +
            "}";
    }
}
