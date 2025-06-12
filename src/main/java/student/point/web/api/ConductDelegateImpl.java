package student.point.web.api;

import ch.qos.logback.core.util.StringCollectionUtil;
import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ConductScores;
import student.point.domain.Student;
import student.point.repository.ConductScoresRepository;
import student.point.repository.StudentRepository;
import student.point.service.api.dto.ConductScoresCustomDTO;
import student.point.service.enity.StudentAttempt;
import student.point.service.mapper.ConductScoresCustomMapper;

@Service
@Transactional
public class ConductDelegateImpl implements ConductApiDelegate {

    private final StudentRepository studentRepository;
    private final ConductScoresRepository conductScoresRepository;

    private final ConductScoresCustomMapper conductScoresCustomMapper;

    public ConductDelegateImpl(
        StudentRepository studentRepository,
        ConductScoresRepository conductScoresRepository,
        ConductScoresCustomMapper conductScoresCustomMapper
    ) {
        this.studentRepository = studentRepository;
        this.conductScoresRepository = conductScoresRepository;
        this.conductScoresCustomMapper = conductScoresCustomMapper;
    }

    @Override
    public ResponseEntity<Map<String, Object>> conductCreate(String classIName, String academicYear) throws Exception {
        List<Student> students = studentRepository.findAllByClasName(classIName);
        List<Long> studentIds = students.stream().map(Student::getId).toList();
        List<ConductScores> conductScoresCurrent = conductScoresRepository.findAllStudent(studentIds, academicYear);
        Map<Long, ConductScores> conductScoresMap = conductScoresCurrent
            .stream()
            .collect(Collectors.toMap(x -> x.getStudent().getId(), Function.identity(), (existing, replacement) -> replacement));

        List<ConductScores> conductScoresAdd = new ArrayList<>();
        for (Student s : students) {
            ConductScores scores = conductScoresMap.get(s.getId());
            if (Objects.nonNull(scores)) {
                continue;
            }
            ConductScores item = new ConductScores();
            item.setAcademicYear(academicYear);
            item.setStudent(s);
            conductScoresAdd.add(item);
        }
        conductScoresRepository.saveAll(conductScoresAdd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ConductScoresCustomDTO>> conductGet(String classIName, String academicYear) throws Exception {
        if (StringUtils.isBlank(classIName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        List<ConductScores> conductScores = conductScoresRepository.findAllConduct(classIName, academicYear);
        List<ConductScoresCustomDTO> res = conductScores.stream().map(conductScoresCustomMapper::toDto).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
