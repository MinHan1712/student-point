package student.point.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import student.point.domain.ModuleConfiguration;

/**
 * Spring Data JPA repository for the ModuleConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleConfigurationRepository
    extends JpaRepository<ModuleConfiguration, Long>, JpaSpecificationExecutor<ModuleConfiguration> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from ModuleConfiguration m where m.name = :name")
    Optional<ModuleConfiguration> findOneForUpdateByName(String name);
}
