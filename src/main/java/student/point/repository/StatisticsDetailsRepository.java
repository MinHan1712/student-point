package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.StatisticsDetails;

/**
 * Spring Data JPA repository for the StatisticsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticsDetailsRepository extends JpaRepository<StatisticsDetails, Long>, JpaSpecificationExecutor<StatisticsDetails> {}
