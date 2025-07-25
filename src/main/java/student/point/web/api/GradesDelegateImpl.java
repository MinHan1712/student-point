package student.point.web.api;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import student.point.domain.Grades;
import student.point.repository.GradesRepository;
import student.point.service.StudentService;
import student.point.service.api.dto.GradesCustomDTO;
import student.point.service.api.dto.SemesterGradeFullSummary;
import student.point.service.api.dto.SemesterSummary200Response;
import student.point.service.dto.StudentDTO;
import student.point.service.mapper.GradesCustomMapper;
import student.point.service.mapper.StudentCustomMapper;
import student.point.service.mapper.StudentMapper;

@Service
@Transactional
public class GradesDelegateImpl implements GradesApiDelegate {

    private final GradesRepository gradesRepository;
    private final GradesCustomMapper gradesCustomMapper;
    private final StudentService studentService;
    private final StudentCustomMapper studentCustomMapper;

    public GradesDelegateImpl(
        GradesRepository gradesRepository,
        GradesCustomMapper gradesCustomMapper,
        StudentService studentService,
        StudentCustomMapper studentCustomMapper
    ) {
        this.gradesRepository = gradesRepository;
        this.gradesCustomMapper = gradesCustomMapper;
        this.studentService = studentService;
        this.studentCustomMapper = studentCustomMapper;
    }

    @Override
    public ResponseEntity<Object> multiGradesScores(List<@Valid GradesCustomDTO> gradesCustomDTO) throws Exception {
        List<Grades> grades = new ArrayList<>();
        gradesCustomDTO.forEach(x -> grades.add(gradesCustomMapper.toEntity(x)));
        gradesRepository.saveAll(grades);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SemesterSummary200Response> semesterSummary(Long studentId) throws Exception {
        List<Object[]> rawResult = gradesRepository.getSemesterSummaryWithOverall(studentId);

        List<SemesterGradeFullSummary> result = rawResult
            .stream()
            .map(row -> {
                SemesterGradeFullSummary item = new SemesterGradeFullSummary();
                item.setStudentId(Objects.isNull(row[0]) ? null : row[0].toString());
                item.setFullName(Objects.isNull(row[1]) ? null : row[1].toString());
                item.setAcademicYear(Objects.isNull(row[2]) ? null : row[2].toString());
                item.setTotalCredits(Objects.isNull(row[3]) ? null : row[3].toString());
                item.setAvgScore10(Objects.isNull(row[4]) ? null : row[4].toString());
                item.setAvgScore4(Objects.isNull(row[5]) ? null : row[5].toString());
                item.setSemesterRanking(Objects.isNull(row[6]) ? null : row[6].toString());
                return item;
            })
            .collect(Collectors.toList());

        List<Grades> gradesList = gradesRepository.findAllByStudentId(studentId);
        List<GradesCustomDTO> gradesCustomDTOS = gradesCustomMapper.toDto(gradesList);

        StudentDTO studentDTO = studentService.findOne(studentId).orElse(null);
        SemesterSummary200Response response = new SemesterSummary200Response();
        response.setGrades(gradesCustomDTOS);
        response.setSummary(result);
        response.setStudent(Objects.isNull(studentDTO) ? null : studentCustomMapper.toDto(studentDTO));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
