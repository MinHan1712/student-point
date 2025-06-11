package student.point.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student.point.domain.ConductScores;
import student.point.service.api.dto.ClassesCustomDTO;

/**
 * Spring Data JPA repository for the ConductScores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConductScoresRepository extends JpaRepository<ConductScores, Long>, JpaSpecificationExecutor<ConductScores> {
    @Query(
        value = "SELECT cs.* FROM conduct_scores cs " +
        "INNER JOIN student s ON cs.student_id = s.id " +
        "WHERE s.clas_name = :className and cs.academic_year = :academicYear",
        nativeQuery = true
    )
    List<ConductScores> findAllConduct(@Param("className") String className, @Param("academicYear") String academicYear);

    @Query(
        value = "SELECT * FROM conduct_scores cs " + "WHERE cs.student_id in :student and cs.academic_year = :academicYear",
        nativeQuery = true
    )
    List<ConductScores> findAllStudent(@Param("student") List<Long> student, @Param("academicYear") String academicYear);
}
