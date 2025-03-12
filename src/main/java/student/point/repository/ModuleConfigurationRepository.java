package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.ModuleConfiguration;

/**
 * Spring Data JPA repository for the ModuleConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleConfigurationRepository
    extends JpaRepository<ModuleConfiguration, Long>, JpaSpecificationExecutor<ModuleConfiguration> {}
