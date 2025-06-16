package student.point.web.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import student.point.domain.Statistics;
import student.point.domain.StatisticsDetails;
import student.point.domain.Student;
import student.point.domain.enumeration.StatisticsType;
import student.point.domain.enumeration.StudentStatus;
import student.point.repository.StatisticsDetailsRepository;
import student.point.repository.StatisticsRepository;
import student.point.repository.StudentRepository;
import student.point.service.api.dto.StatisticCreate;

@Service
@Transactional
public class StatisticDelegateImpl implements StatisticApiDelegate {

    private final StatisticsDetailsRepository statisticsDetailsRepository;
    private final StatisticsRepository statisticsRepository;
    private final StudentRepository studentRepository;

    public StatisticDelegateImpl(
        StatisticsDetailsRepository statisticsDetailsRepository,
        StatisticsRepository statisticsRepository,
        StudentRepository studentRepository
    ) {
        this.statisticsDetailsRepository = statisticsDetailsRepository;
        this.statisticsRepository = statisticsRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> statisticCreate(StatisticCreate body) throws Exception {
        Statistics statistics = new Statistics();
        statistics.setAcademicYear(body.getAcademicYear());
        statistics.setNotes(body.getNote());
        statistics.setStatus(true);
        if (StatisticsType.Scholarship.name().equals(body.getType())) {
            statistics.setType(StatisticsType.Scholarship);
            statistics = statisticsRepository.save(statistics);
            List<Object[]> results = statisticsDetailsRepository.findTopStudents(
                body.getAcademicYear(),
                body.getFacultyId(),
                body.getMinTotalCredits()
            );
            List<StatisticsDetails> detailsList = new ArrayList<>();
            int index = 0;
            int max = Math.max(body.getMinTotalCredits() - 10, body.getMinTotalCredits());
            BigDecimal money1 = BigDecimal.valueOf(5600000);
            BigDecimal money2 = BigDecimal.valueOf(5000000);
            for (Object[] row : results) {
                StatisticsDetails detail = new StatisticsDetails();

                // Set Student reference
                Student student = new Student();
                student.setId(Objects.isNull(row[0]) ? null : ((Number) row[0]).longValue());
                detail.setStudent(student);

                // GPA (score_4)
                detail.setScore(Objects.isNull(row[7]) ? null : new BigDecimal(row[7].toString())); // gpa_4

                // Học bổng: chia theo chỉ số index
                detail.setTotalScholarship(index < max ? money1 : money2);

                // Trạng thái và liên kết thống kê
                detail.setStatus(true);
                detail.setStatistics(statistics);

                // Ghi chú, ngày tốt nghiệp
                detail.setNotes(body.getNote());
                detail.setGraduationDate(Instant.now());

                detailsList.add(detail);
                index++;
            }

            statisticsDetailsRepository.saveAll(detailsList);
        }

        if (StatisticsType.Warning.name().equals(body.getType())) {
            statistics.setType(StatisticsType.Warning);
            statistics = statisticsRepository.save(statistics);
            List<Object[]> results = statisticsDetailsRepository.findWarningStudents(
                body.getAcademicYear(),
                body.getFacultyId(),
                body.getMinTotalCredits()
            );
            List<StatisticsDetails> detailsList = new ArrayList<>();
            for (Object[] row : results) {
                StatisticsDetails detail = new StatisticsDetails();

                // Set Student reference
                Student student = new Student();
                student.setId(Objects.isNull(row[0]) ? null : ((Number) row[0]).longValue());
                detail.setStudent(student);

                // GPA (score_4)
                detail.setScore(Objects.isNull(row[7]) ? null : new BigDecimal(row[7].toString())); // gpa_4

                // Trạng thái và liên kết thống kê
                detail.setStatus(true);
                detail.setStatistics(statistics);

                // Ghi chú, ngày tốt nghiệp
                detail.setNotes(body.getNote());
                detail.setGraduationDate(Instant.now());

                detailsList.add(detail);
            }
            statisticsDetailsRepository.saveAll(detailsList);
        }

        if (StatisticsType.Graduation.name().equals(body.getType())) {
            statistics.setType(StatisticsType.Graduation);
            statistics = statisticsRepository.save(statistics);
            List<Object[]> results = statisticsDetailsRepository.findGraduationStudentsWithLatestAttempt(
                body.getFacultyId(),
                body.getMinTotalCredits()
            );
            List<StatisticsDetails> detailsList = new ArrayList<>();
            List<Long> studentList = new ArrayList<>();
            for (Object[] row : results) {
                StatisticsDetails detail = new StatisticsDetails();

                // Set Student reference
                Student student = new Student();
                student.setId(Objects.isNull(row[0]) ? null : ((Number) row[0]).longValue());
                detail.setStudent(student);

                studentList.add(student.getId());

                // GPA (score_4)
                detail.setScore(Objects.isNull(row[7]) ? null : new BigDecimal(row[7].toString())); // gpa_4

                // Trạng thái và liên kết thống kê
                detail.setStatus(true);
                detail.setStatistics(statistics);

                // Ghi chú, ngày tốt nghiệp
                detail.setNotes(body.getNote());
                detail.setGraduationDate(Instant.now());

                detailsList.add(detail);
            }
            statisticsDetailsRepository.saveAll(detailsList);
            if (!CollectionUtils.isEmpty(studentList)) {
                List<Student> students = studentRepository.findAllByIdIn(studentList);
                List<Student> updateStudent = new ArrayList<>();
                for (Student s : students) {
                    s.setStatus(StudentStatus.Graduated);
                    updateStudent.add(s);
                }
                studentRepository.saveAll(updateStudent);
            }
        }
        if (StatisticsType.Retake.name().equals(body.getType())) {
            statistics.setType(StatisticsType.Retake);
            statistics = statisticsRepository.save(statistics);
            List<Object[]> results = statisticsDetailsRepository.findRetakeStudents(
                body.getAcademicYear(),
                body.getFacultyId(),
                body.getClassesId()
            );
            List<StatisticsDetails> detailsList = new ArrayList<>();
            for (Object[] row : results) {
                StatisticsDetails detail = new StatisticsDetails();

                // Set Student reference
                Student student = new Student();
                student.setId(Objects.isNull(row[0]) ? null : ((Number) row[0]).longValue());
                detail.setStudent(student);

                // GPA (score_4)
                detail.setScore(Objects.isNull(row[6]) ? null : new BigDecimal(row[6].toString())); // gpa_4

                // Trạng thái và liên kết thống kê
                detail.setStatus(true);
                detail.setStatistics(statistics);

                // Ghi chú, ngày tốt nghiệp
                detail.setNotes(body.getNote());
                detail.setGraduationDate(Instant.now());

                detailsList.add(detail);
            }
            statisticsDetailsRepository.saveAll(detailsList);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
