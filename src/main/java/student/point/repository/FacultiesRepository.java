package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Faculties;

/**
 * Spring Data JPA repository for the Faculties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultiesRepository extends JpaRepository<Faculties, Long>, JpaSpecificationExecutor<Faculties> {}
