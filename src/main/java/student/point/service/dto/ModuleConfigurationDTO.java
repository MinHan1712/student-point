package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link student.point.domain.ModuleConfiguration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModuleConfigurationDTO implements Serializable {

    private Long id;

    private String name;

    private String prefix;

    private Long padding;

    private Long numberNextActual;

    private Long numberIncrement;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getPadding() {
        return padding;
    }

    public void setPadding(Long padding) {
        this.padding = padding;
    }

    public Long getNumberNextActual() {
        return numberNextActual;
    }

    public void setNumberNextActual(Long numberNextActual) {
        this.numberNextActual = numberNextActual;
    }

    public Long getNumberIncrement() {
        return numberIncrement;
    }

    public void setNumberIncrement(Long numberIncrement) {
        this.numberIncrement = numberIncrement;
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
        if (!(o instanceof ModuleConfigurationDTO)) {
            return false;
        }

        ModuleConfigurationDTO moduleConfigurationDTO = (ModuleConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moduleConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleConfigurationDTO{" +
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
