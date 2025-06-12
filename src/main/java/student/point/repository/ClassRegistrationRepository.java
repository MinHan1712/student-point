package student.point.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import student.point.domain.ClassRegistration;
import student.point.domain.enumeration.ClassRegistrationStatus;
import student.point.service.enity.StudentAttempt;

/**
 * Spring Data JPA repository for the ClassRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Long>, JpaSpecificationExecutor<ClassRegistration> {
    @Query(
        "select new student.point.service.enity.StudentAttempt(cr.student.id, count (cr.id)) " +
        "from ClassRegistration cr inner join Classes c on cr.classes.id = c.id " +
        "where cr.status not in :status and cr.student.id in :studentIds and (:courseId is null or c.course.id = :courseId) " +
        "group by cr.student.id"
    )
    List<StudentAttempt> findByStudentAttempt(List<ClassRegistrationStatus> status, List<Long> studentIds, Long courseId);

    List<ClassRegistration> findAllByClasses_Id(Long classId);

    @Query("select cr from ClassRegistration cr where cr.classes.id = :classId and cr.student.id in :studentIds")
    List<ClassRegistration> findAllByClassesIdAndStudentId(Long classId, List<Long> studentIds);
}
