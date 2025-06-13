package student.point.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student.point.domain.Grades;

/**
 * Spring Data JPA repository for the Grades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradesRepository extends JpaRepository<Grades, Long>, JpaSpecificationExecutor<Grades> {
    List<Grades> findByClasses_Id(Long classId);

    @Query("select g from Grades g where g.classes.id = :classId and g.student.id in :studentId")
    List<Grades> findAllByClassesIdAndStudentId(Long classId, List<Long> studentId);

    @Query(
        value = """
            (
                SELECT
                    s.id AS student_id,
                    s.full_name,
                    c.academic_year,
                    COALESCE(SUM(g.credit), 0) AS total_credits,
                    COALESCE(ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2), 0) AS avg_score_10,
                    COALESCE(ROUND(SUM(COALESCE(g.score_4, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2), 0) AS avg_score_4,
                    CASE
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 8.5 THEN 'Giỏi'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 7.0 THEN 'Khá'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 5.5 THEN 'Trung bình'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 4.0 THEN 'Trung bình yếu'
                        ELSE 'Yếu'
                    END AS semester_ranking,
                    0 AS sort_order
                FROM grades g
                JOIN student s ON g.student_id = s.id
                JOIN classes c ON g.classes_id = c.id
                WHERE g.status = TRUE AND s.id = :studentId
                GROUP BY s.id, s.full_name, c.academic_year
            )

            UNION ALL

            (
                SELECT
                    s.id AS student_id,
                    s.full_name,
                    'Tổng kết' AS academic_year,
                    COALESCE(SUM(g.credit), 0) AS total_credits,
                    COALESCE(ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2), 0) AS avg_score_10,
                    COALESCE(ROUND(SUM(COALESCE(g.score_4, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2), 0) AS avg_score_4,
                    CASE
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 8.5 THEN 'Giỏi'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 7.0 THEN 'Khá'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 5.5 THEN 'Trung bình'
                        WHEN ROUND(SUM(COALESCE(g.score_10, 0) * g.credit) / NULLIF(SUM(g.credit), 0), 2) >= 4.0 THEN 'Trung bình yếu'
                        ELSE 'Yếu'
                    END AS semester_ranking,
                    1 AS sort_order
                FROM grades g
                JOIN student s ON g.student_id = s.id
                WHERE g.status = TRUE AND s.id = :studentId
                GROUP BY s.id, s.full_name
            )

            ORDER BY sort_order DESC, academic_year ASC;

        """,
        nativeQuery = true
    )
    List<Object[]> getSemesterSummaryWithOverall(@Param("studentId") Long studentId);

    @Query("select g from Grades g where g.student.id = :studentId")
    List<Grades> findAllByStudentId(Long studentId);
}
