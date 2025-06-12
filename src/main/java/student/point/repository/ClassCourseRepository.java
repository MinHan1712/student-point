package student.point.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import student.point.domain.ClassCourse;

/**
 * Spring Data JPA repository for the ClassCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassCourseRepository extends JpaRepository<ClassCourse, Long>, JpaSpecificationExecutor<ClassCourse> {
    List<ClassCourse> findAllByFaculties_Id(Long id);
}
