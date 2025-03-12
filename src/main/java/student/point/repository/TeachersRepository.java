package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Teachers;

/**
 * Spring Data JPA repository for the Teachers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachersRepository extends JpaRepository<Teachers, Long>, JpaSpecificationExecutor<Teachers> {}
