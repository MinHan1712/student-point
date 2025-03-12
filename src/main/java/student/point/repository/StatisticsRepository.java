package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.Statistics;

/**
 * Spring Data JPA repository for the Statistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics> {}
