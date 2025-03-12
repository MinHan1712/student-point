package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Grades;

/**
 * Spring Data JPA repository for the Grades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradesRepository extends JpaRepository<Grades, Long>, JpaSpecificationExecutor<Grades> {}
