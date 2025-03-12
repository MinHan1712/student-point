package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.MasterData;

/**
 * Spring Data JPA repository for the MasterData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Long>, JpaSpecificationExecutor<MasterData> {}
