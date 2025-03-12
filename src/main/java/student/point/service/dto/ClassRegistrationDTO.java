package student.point.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import student.point.domain.enumeration.ClassRegistrationStatus;

/**
 * A DTO for the {@link student.point.domain.ClassRegistration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassRegistrationDTO implements Serializable {

    private Long id;

    private Instant registerDate;

    private ClassRegistrationStatus status;

    private String remarks;

    private StudentDTO student;

    private ClassesDTO classes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public ClassRegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(ClassRegistrationStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public ClassesDTO getClasses() {
        return classes;
    }

    public void setClasses(ClassesDTO classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRegistrationDTO)) {
            return false;
        }

        ClassRegistrationDTO classRegistrationDTO = (ClassRegistrationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classRegistrationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRegistrationDTO{" +
            "id=" + getId() +
            ", registerDate='" + getRegisterDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", student=" + getStudent() +
            ", classes=" + getClasses() +
            "}";
    }
}
