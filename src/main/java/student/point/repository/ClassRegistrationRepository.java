package student.point.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.ClassRegistration;

/**
 * Spring Data JPA repository for the ClassRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Long>, JpaSpecificationExecutor<ClassRegistration> {}
