package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import student.point.domain.enumeration.StatisticsType;
import student.point.listener.CourseListener;
import student.point.listener.StatisticsListener;

/**
 * A Statistics.
 */
@Entity
@Table(name = "statistics")
@EntityListeners(StatisticsListener.class)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "statistics_code")
    private String statisticsCode;

    @Column(name = "academic_year")
    private String academicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private StatisticsType type;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "statistics")
    @JsonIgnoreProperties(value = { "student", "statistics" }, allowSetters = true)
    private Set<StatisticsDetails> statisticsDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Statistics id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticsCode() {
        return this.statisticsCode;
    }

    public Statistics statisticsCode(String statisticsCode) {
        this.setStatisticsCode(statisticsCode);
        return this;
    }

    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public Statistics academicYear(String academicYear) {
        this.setAcademicYear(academicYear);
        return this;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public StatisticsType getType() {
        return this.type;
    }

    public Statistics type(StatisticsType type) {
        this.setType(type);
        return this;
    }

    public void setType(StatisticsType type) {
        this.type = type;
    }

    public String getNotes() {
        return this.notes;
    }

    public Statistics notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Statistics status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Statistics createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Statistics createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Statistics lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Statistics lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<StatisticsDetails> getStatisticsDetails() {
        return this.statisticsDetails;
    }

    public void setStatisticsDetails(Set<StatisticsDetails> statisticsDetails) {
        if (this.statisticsDetails != null) {
            this.statisticsDetails.forEach(i -> i.setStatistics(null));
        }
        if (statisticsDetails != null) {
            statisticsDetails.forEach(i -> i.setStatistics(this));
        }
        this.statisticsDetails = statisticsDetails;
    }

    public Statistics statisticsDetails(Set<StatisticsDetails> statisticsDetails) {
        this.setStatisticsDetails(statisticsDetails);
        return this;
    }

    public Statistics addStatisticsDetails(StatisticsDetails statisticsDetails) {
        this.statisticsDetails.add(statisticsDetails);
        statisticsDetails.setStatistics(this);
        return this;
    }

    public Statistics removeStatisticsDetails(StatisticsDetails statisticsDetails) {
        this.statisticsDetails.remove(statisticsDetails);
        statisticsDetails.setStatistics(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statistics)) {
            return false;
        }
        return getId() != null && getId().equals(((Statistics) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statistics{" +
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
