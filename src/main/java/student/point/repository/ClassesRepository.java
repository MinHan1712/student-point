package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Classes;

/**
 * Spring Data JPA repository for the Classes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long>, JpaSpecificationExecutor<Classes>, ClassesCustomRepository {}
