package student.point.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student.point.domain.StatisticsDetails;

/**
 * Spring Data JPA repository for the StatisticsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticsDetailsRepository extends JpaRepository<StatisticsDetails, Long>, JpaSpecificationExecutor<StatisticsDetails> {
    @Query(
        value = """
            SELECT
                s.id AS student_id,
                s.student_code,
                s.full_name,
                s.course_year AS academic_year,
                f.id AS faculty_id,
                f.faculty_name,
                SUM(g.credit) AS total_credits,
                ROUND(SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0), 2) AS gpa_4,
                RANK() OVER (ORDER BY SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0) DESC) AS rank
            FROM
                student s
            JOIN faculties f ON s.faculties_id = f.id
            JOIN grades g ON g.student_id = s.id
            JOIN classes c ON g.classes_id = c.id
            WHERE
                c.academic_year = :academicYear
                AND f.id = :facultyId
                AND g.status = true
            GROUP BY
                s.id, s.student_code, s.full_name, s.course_year, f.id, f.faculty_name
            HAVING
                SUM(g.credit) >= :minTotalCredits
            ORDER BY
                gpa_4 DESC
            LIMIT 20
        """,
        nativeQuery = true
    )
    List<Object[]> findTopStudents(
        @Param("academicYear") String academicYear,
        @Param("facultyId") Long facultyId,
        @Param("minTotalCredits") Integer minTotalCredits
    );

    @Query(
        value = """
            SELECT
                s.id AS student_id,
                s.student_code,
                s.full_name,
                s.course_year AS academic_year,
                f.id AS faculty_id,
                f.faculty_name,
                SUM(g.credit) AS total_credits,
                ROUND(SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0), 2) AS gpa_4
            FROM
                student s
            JOIN faculties f ON s.faculties_id = f.id
            JOIN grades g ON g.student_id = s.id
            JOIN classes c ON g.classes_id = c.id
            WHERE
                c.academic_year = :academicYear
                AND f.id = :facultyId
                AND g.status = true
            GROUP BY
                s.id, s.student_code, s.full_name, s.course_year, f.id, f.faculty_name
            HAVING
                SUM(g.credit) >= :minTotalCredits
                AND ROUND(SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0), 2) <= 1.5
            ORDER BY gpa_4 ASC
        """,
        nativeQuery = true
    )
    List<Object[]> findWarningStudents(
        @Param("academicYear") String academicYear,
        @Param("facultyId") Long facultyId,
        @Param("minTotalCredits") Integer minTotalCredits
    );

    @Query(
        value = """
            WITH latest_attempts AS (
                SELECT
                    g.*,
                    ROW_NUMBER() OVER (
                        PARTITION BY g.student_id, c.course_id
                        ORDER BY g.study_attempt DESC
                    ) AS rn
                FROM grades g inner join classes c ON g.classes_id = c.id
                WHERE g.status = true
            )
            SELECT
                s.id AS student_id,
                s.student_code,
                s.full_name,
                s.course_year AS academic_year,
                f.id AS faculty_id,
                f.faculty_name,
                SUM(g.credit) AS total_credits,
                ROUND(SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0), 2) AS gpa_4
            FROM
                student s
            JOIN faculties f ON s.faculties_id = f.id
            JOIN latest_attempts g ON g.student_id = s.id AND g.rn = 1
            JOIN classes c ON g.classes_id = c.id
            WHERE
                f.id = :facultyId
                AND s.status != 'Graduated'
            GROUP BY
                s.id, s.student_code, s.full_name, s.course_year, f.id, f.faculty_name
            HAVING
                SUM(g.credit) >= :minTotalCredits
                AND ROUND(SUM(g.credit * g.score_4) / NULLIF(SUM(g.credit), 0), 2) >= 2.0
            ORDER BY gpa_4 DESC;
        """,
        nativeQuery = true
    )
    List<Object[]> findGraduationStudentsWithLatestAttempt(
        @Param("facultyId") Long facultyId,
        @Param("minTotalCredits") Integer minTotalCredits
    );

    @Query(
        value = """
           SELECT
              s.id AS student_id,
              s.student_code,
              s.full_name,
              s.course_year AS academic_year,
              f.id AS faculty_id,
              f.faculty_name, g.score_4
          FROM
              student s
          JOIN faculties f ON s.faculties_id = f.id
          JOIN grades g ON g.student_id = s.id
          JOIN classes c ON g.classes_id = c.id
          WHERE
              f.id = :facultyId
              AND c.academic_year = :academicYear
              AND g.status = true
              AND g.score_4 <= 0.5 and g.score_4 is not null
              and (:classesId is null or g.classes_id = :classesId);
        """,
        nativeQuery = true
    )
    List<Object[]> findRetakeStudents(
        @Param("academicYear") String academicYear,
        @Param("facultyId") Long facultyId,
        @Param("classesId") Long classesId
    );
}
