package student.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A StatisticsDetails.
 */
@Entity
@Table(name = "statistics_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "total_scholarship", precision = 21, scale = 2)
    private BigDecimal totalScholarship;

    @Column(name = "graduation_date")
    private Instant graduationDate;

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
    @JsonIgnoreProperties(value = { "classRegistrations", "conductScores", "statisticsDetails", "grades" }, allowSetters = true)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "statisticsDetails" }, allowSetters = true)
    private Statistics statistics;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StatisticsDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public StatisticsDetails code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotalScholarship() {
        return this.totalScholarship;
    }

    public StatisticsDetails totalScholarship(BigDecimal totalScholarship) {
        this.setTotalScholarship(totalScholarship);
        return this;
    }

    public void setTotalScholarship(BigDecimal totalScholarship) {
        this.totalScholarship = totalScholarship;
    }

    public Instant getGraduationDate() {
        return this.graduationDate;
    }

    public StatisticsDetails graduationDate(Instant graduationDate) {
        this.setGraduationDate(graduationDate);
        return this;
    }

    public void setGraduationDate(Instant graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public StatisticsDetails notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public StatisticsDetails status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public StatisticsDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public StatisticsDetails createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public StatisticsDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public StatisticsDetails lastModifiedDate(Instant lastModifiedDate) {
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

    public StatisticsDetails student(Student student) {
        this.setStudent(student);
        return this;
    }

    public Statistics getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public StatisticsDetails statistics(Statistics statistics) {
        this.setStatistics(statistics);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticsDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((StatisticsDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsDetails{" +
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
            "}";
    }
}
