package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.ConductScores;

/**
 * Spring Data JPA repository for the ConductScores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConductScoresRepository extends JpaRepository<ConductScores, Long>, JpaSpecificationExecutor<ConductScores> {}
