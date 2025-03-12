package student.point.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link student.point.domain.ModuleConfiguration} entity. This class is used
 * in {@link student.point.web.rest.ModuleConfigurationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /module-configurations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModuleConfigurationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter prefix;

    private LongFilter padding;

    private LongFilter numberNextActual;

    private LongFilter numberIncrement;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private Boolean distinct;

    public ModuleConfigurationCriteria() {}

    public ModuleConfigurationCriteria(ModuleConfigurationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.prefix = other.optionalPrefix().map(StringFilter::copy).orElse(null);
        this.padding = other.optionalPadding().map(LongFilter::copy).orElse(null);
        this.numberNextActual = other.optionalNumberNextActual().map(LongFilter::copy).orElse(null);
        this.numberIncrement = other.optionalNumberIncrement().map(LongFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ModuleConfigurationCriteria copy() {
        return new ModuleConfigurationCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPrefix() {
        return prefix;
    }

    public Optional<StringFilter> optionalPrefix() {
        return Optional.ofNullable(prefix);
    }

    public StringFilter prefix() {
        if (prefix == null) {
            setPrefix(new StringFilter());
        }
        return prefix;
    }

    public void setPrefix(StringFilter prefix) {
        this.prefix = prefix;
    }

    public LongFilter getPadding() {
        return padding;
    }

    public Optional<LongFilter> optionalPadding() {
        return Optional.ofNullable(padding);
    }

    public LongFilter padding() {
        if (padding == null) {
            setPadding(new LongFilter());
        }
        return padding;
    }

    public void setPadding(LongFilter padding) {
        this.padding = padding;
    }

    public LongFilter getNumberNextActual() {
        return numberNextActual;
    }

    public Optional<LongFilter> optionalNumberNextActual() {
        return Optional.ofNullable(numberNextActual);
    }

    public LongFilter numberNextActual() {
        if (numberNextActual == null) {
            setNumberNextActual(new LongFilter());
        }
        return numberNextActual;
    }

    public void setNumberNextActual(LongFilter numberNextActual) {
        this.numberNextActual = numberNextActual;
    }

    public LongFilter getNumberIncrement() {
        return numberIncrement;
    }

    public Optional<LongFilter> optionalNumberIncrement() {
        return Optional.ofNullable(numberIncrement);
    }

    public LongFilter numberIncrement() {
        if (numberIncrement == null) {
            setNumberIncrement(new LongFilter());
        }
        return numberIncrement;
    }

    public void setNumberIncrement(LongFilter numberIncrement) {
        this.numberIncrement = numberIncrement;
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
        final ModuleConfigurationCriteria that = (ModuleConfigurationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(prefix, that.prefix) &&
            Objects.equals(padding, that.padding) &&
            Objects.equals(numberNextActual, that.numberNextActual) &&
            Objects.equals(numberIncrement, that.numberIncrement) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            prefix,
            padding,
            numberNextActual,
            numberIncrement,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleConfigurationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalPrefix().map(f -> "prefix=" + f + ", ").orElse("") +
            optionalPadding().map(f -> "padding=" + f + ", ").orElse("") +
            optionalNumberNextActual().map(f -> "numberNextActual=" + f + ", ").orElse("") +
            optionalNumberIncrement().map(f -> "numberIncrement=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
