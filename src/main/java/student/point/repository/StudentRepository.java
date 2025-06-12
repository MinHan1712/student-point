package student.point.repository;

import ch.qos.logback.core.status.Status;
import java.util.List;
import org.springframework.boot.loader.tools.LibraryScope;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Student;
import student.point.domain.enumeration.StudentStatus;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    List<Student> findAllByIdInAndStatus(List<Long> ids, StudentStatus status);
    List<Student> findAllByClasName(String className);
    List<Student> findAllByIdIn(List<Long> ids);
}
