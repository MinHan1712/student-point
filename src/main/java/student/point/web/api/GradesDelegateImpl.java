package student.point.web.api;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import student.point.domain.Grades;
import student.point.repository.GradesRepository;
import student.point.service.api.dto.GradesCustomDTO;
import student.point.service.api.dto.SemesterGradeFullSummary;
import student.point.service.api.dto.SemesterSummary200Response;
import student.point.service.mapper.GradesCustomMapper;

@Service
@Transactional
public class GradesDelegateImpl implements GradesApiDelegate {

    private final GradesRepository gradesRepository;
    private final GradesCustomMapper gradesCustomMapper;

    public GradesDelegateImpl(GradesRepository gradesRepository, GradesCustomMapper gradesCustomMapper) {
        this.gradesRepository = gradesRepository;
        this.gradesCustomMapper = gradesCustomMapper;
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
                item.setStudentId(String.valueOf(row[0]));
                item.setFullName(String.valueOf(row[1]));
                item.setAcademicYear(String.valueOf(row[2]));
                item.setTotalCredits(String.valueOf(row[3]));
                item.setAvgScore10(String.valueOf(row[4]));
                item.setAvgScore4(String.valueOf(row[5]));
                item.setSemesterRanking(String.valueOf(row[6]));
                return item;
            })
            .collect(Collectors.toList());

        List<Grades> gradesList = gradesRepository.findAllByStudentId(studentId);
        List<GradesCustomDTO> gradesCustomDTOS = gradesCustomMapper.toDto(gradesList);
        SemesterSummary200Response response = new SemesterSummary200Response();
        response.setGrades(gradesCustomDTOS);
        response.setSummary(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
