package student.point.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import student.point.domain.Classes;

public interface ClassesCustomRepository extends JpaRepository<Classes, Long> {
    //    @Query("select c from Classes c " +
    //        "where cast(c.id as string) in (select c2.parentId from Classes c2 where c2.parentId is not null group by c2.parentId) " +
    //        "and (:classCode = '' or c.classCode = :classCode) " +
    //        "and (:startDate = '' or c.startDate >= :startDate) and (:endDate = '' or c.endDate <= :endDate) " +
    //        "order by c.lastModifiedDate desc")
    //    List<Classes> findByClasses(String classCode, String startDate, String endDate, Boolean status);
}
