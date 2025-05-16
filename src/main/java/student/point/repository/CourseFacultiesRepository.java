package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.CourseFaculties;

/**
 * Spring Data JPA repository for the CourseFaculties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseFacultiesRepository extends JpaRepository<CourseFaculties, Long>, JpaSpecificationExecutor<CourseFaculties> {}
