package student.point.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ModuleConfiguration.
 */
@Entity
@Table(name = "module_configuration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModuleConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "padding")
    private Long padding;

    @Column(name = "number_next_actual")
    private Long numberNextActual;

    @Column(name = "number_increment")
    private Long numberIncrement;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ModuleConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ModuleConfiguration name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public ModuleConfiguration prefix(String prefix) {
        this.setPrefix(prefix);
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getPadding() {
        return this.padding;
    }

    public ModuleConfiguration padding(Long padding) {
        this.setPadding(padding);
        return this;
    }

    public void setPadding(Long padding) {
        this.padding = padding;
    }

    public Long getNumberNextActual() {
        return this.numberNextActual;
    }

    public ModuleConfiguration numberNextActual(Long numberNextActual) {
        this.setNumberNextActual(numberNextActual);
        return this;
    }

    public void setNumberNextActual(Long numberNextActual) {
        this.numberNextActual = numberNextActual;
    }

    public Long getNumberIncrement() {
        return this.numberIncrement;
    }

    public ModuleConfiguration numberIncrement(Long numberIncrement) {
        this.setNumberIncrement(numberIncrement);
        return this;
    }

    public void setNumberIncrement(Long numberIncrement) {
        this.numberIncrement = numberIncrement;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ModuleConfiguration createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ModuleConfiguration createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ModuleConfiguration lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ModuleConfiguration lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleConfiguration)) {
            return false;
        }
        return getId() != null && getId().equals(((ModuleConfiguration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleConfiguration{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", padding=" + getPadding() +
            ", numberNextActual=" + getNumberNextActual() +
            ", numberIncrement=" + getNumberIncrement() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
