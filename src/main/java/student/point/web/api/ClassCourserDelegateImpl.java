package student.point.web.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ClassCourse;
import student.point.repository.ClassCourseRepository;
import student.point.service.api.dto.ClassCourseRes;
import student.point.service.api.dto.IClassName;

@Service
@Transactional
public class ClassCourserDelegateImpl implements ClassCourserApiDelegate {

    private final ClassCourseRepository classCourseRepository;

    public ClassCourserDelegateImpl(ClassCourseRepository classCourseRepository) {
        this.classCourseRepository = classCourseRepository;
    }

    @Override
    public ResponseEntity<ClassCourseRes> classCourserGet(Long id) throws Exception {
        List<ClassCourse> classCourseList = classCourseRepository.findAllByFaculties_Id(id);
        Map<String, List<ClassCourse>> classCourseMap = classCourseList.stream().collect(Collectors.groupingBy(ClassCourse::getCourseYear));
        ClassCourseRes res = new ClassCourseRes();
        res.setFacultyId(id);

        List<IClassName> iClassNames = new ArrayList<>();

        for (Map.Entry<String, List<ClassCourse>> entry : classCourseMap.entrySet()) {
            String courseYear = entry.getKey();
            List<String> classCourses = entry
                .getValue()
                .stream()
                .map(ClassCourse::getClassName)
                .collect(Collectors.toSet())
                .stream()
                .toList();

            IClassName className = new IClassName();
            className.setCourse(courseYear);
            className.setClassName(classCourses);
            iClassNames.add(className);
        }
        res.setCourses(iClassNames);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
