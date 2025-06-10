package student.point.web.api;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import student.point.domain.ClassRegistration;
import student.point.domain.Classes;
import student.point.domain.Grades;
import student.point.domain.Student;
import student.point.domain.enumeration.ClassRegistrationStatus;
import student.point.domain.enumeration.StudentStatus;
import student.point.repository.ClassRegistrationRepository;
import student.point.repository.ClassesRepository;
import student.point.repository.GradesRepository;
import student.point.repository.StudentRepository;
import student.point.service.ClassesService;
import student.point.service.CourseService;
import student.point.service.api.dto.ClassesCustomDTO;
import student.point.service.dto.ClassesDTO;
import student.point.service.dto.CourseDTO;
import student.point.service.dto.TeachersDTO;
import student.point.service.enity.StudentAttempt;
import student.point.service.mapper.ClassesCustom2Mapper;
import student.point.service.mapper.ClassesCustomMapper;
import student.point.service.mapper.ClassesMapper;
import student.point.web.rest.errors.BadRequestAlertException;

@Service
@Transactional
public class ClassesDelegateImpl implements ClassesApiDelegate {

    private final ClassesService classesService;
    private final ClassesCustomMapper classesCustomMapper;
    private final ClassesMapper classesMapper;
    private final ClassesCustom2Mapper classesCustom2Mapper;
    private final ClassesRepository classesRepository;
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final ClassRegistrationRepository classRegistrationRepository;
    private final GradesRepository gradesRepository;

    public ClassesDelegateImpl(
        ClassesService classesService,
        ClassesCustomMapper classesCustomMapper,
        ClassesMapper classesMapper,
        ClassesCustom2Mapper classesCustom2Mapper,
        ClassesRepository classesRepository,
        StudentRepository studentRepository,
        CourseService courseService,
        ClassRegistrationRepository classRegistrationRepository,
        GradesRepository gradesRepository
    ) {
        this.classesService = classesService;
        this.classesCustomMapper = classesCustomMapper;
        this.classesMapper = classesMapper;
        this.classesCustom2Mapper = classesCustom2Mapper;
        this.classesRepository = classesRepository;
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.classRegistrationRepository = classRegistrationRepository;
        this.gradesRepository = gradesRepository;
    }

    @Override
    public ResponseEntity<ClassesCustomDTO> updateClasses(Long id, ClassesCustomDTO classesCustomDTO) throws Exception {
        ClassesDTO classesDTO = classesCustomMapper.toEntity(classesCustomDTO);
        CourseDTO courseDTO = classesDTO.getCourse() != null ? courseService.findOne(classesDTO.getCourse().getId()).orElse(null) : null;

        if (Objects.nonNull(courseDTO)) {
            classesDTO.setCourse(courseDTO);
        }

        if (Objects.nonNull(classesCustomDTO.getTeachersId())) {
            TeachersDTO teachersDTO = new TeachersDTO();
            teachersDTO.setId(classesCustomDTO.getTeachersId());
            classesDTO.setTeachers(teachersDTO);
        }
        classesDTO = classesService
            .partialUpdate(classesDTO)
            .orElseThrow(() -> new BadRequestAlertException("Class update failed", id.toString(), "classupdatefailed"));
        if (!CollectionUtils.isEmpty(classesCustomDTO.getStudentIds())) {
            addClassRegistration(classesCustomDTO.getStudentIds(), classesDTO, courseDTO);
        }

        if (!CollectionUtils.isEmpty(classesCustomDTO.getStudentIdRemove())) {
            deactivateClassesRegistration(classesDTO.getId(), classesCustomDTO.getStudentIdRemove());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    @Override
    //    @Transactional(readOnly = true)
    //    public ResponseEntity<List<ClassesCustomDTO>> getClasses(String classCode, String startDate, String endDate, Boolean status, Integer page, Integer size) throws Exception {
    //        Pageable pageable = new Pageable
    //        List<Classes> classes = classesRepository.findByClasses(classCode, startDate, endDate, status, Pageable.);
    //        List<ClassesCustomDTO> response = new ArrayList<>();
    //        if (!ObjectUtils.isEmpty(classes)) {
    //            response.addAll(classesCustom2Mapper.toDto(classes));
    //        }
    //
    //        HttpHeaders headers = new HttpHeaders();
    //        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
    //        return ResponseEntity.ok().headers(headers).body(response);
    //    }

    @Override
    public ResponseEntity<ClassesCustomDTO> deleteClasses(Long id) throws Exception {
        ClassesDTO classesDTO = classesService
            .findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Class not found", id.toString(), "classnotfound"));
        classesDTO.setStatus(false);
        classesService.partialUpdate(classesDTO);

        // class_registerId;
        List<ClassRegistration> classRegistrationList = classRegistrationRepository.findAllByClasses_Id(classesDTO.getId());
        List<Grades> gradesList = gradesRepository.findByClasses_Id(classesDTO.getId());

        List<ClassRegistration> classRegistrationUpdate = new ArrayList<>();
        for (ClassRegistration classRegistration : classRegistrationList) {
            ClassRegistration update = classRegistration;
            update.setStatus(ClassRegistrationStatus.Cancelled);
            classRegistrationUpdate.add(update);
        }

        List<Grades> gradesUpdate = new ArrayList<>();
        for (Grades grades : gradesList) {
            Grades update = grades;
            update.setStatus(false);
            gradesUpdate.add(update);
        }

        classRegistrationRepository.saveAll(classRegistrationUpdate);
        gradesRepository.saveAll(gradesUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClassesCustomDTO> createClasses(ClassesCustomDTO classesCustomDTO) throws Exception {
        ClassesDTO classesDTO = classesCustomMapper.toEntity(classesCustomDTO);
        CourseDTO courseDTO = courseService.findOne(classesCustomDTO.getCourseId()).orElse(null);

        if (Objects.nonNull(courseDTO)) {
            classesDTO.setCourse(courseDTO);
        }

        if (Objects.nonNull(classesCustomDTO.getTeachersId())) {
            TeachersDTO teachersDTO = new TeachersDTO();
            teachersDTO.setId(classesCustomDTO.getTeachersId());
            classesDTO.setTeachers(teachersDTO);
        }

        classesDTO = classesService.save(classesDTO);
        if (Objects.isNull(classesCustomDTO.getParentId())) {
            classesDTO.setParentId(classesDTO.getId().toString());
            classesDTO = classesService
                .partialUpdate(classesDTO)
                .orElseThrow(() -> new BadRequestAlertException("Class update failed", "", "classupdatefailed"));
        }
        if (!CollectionUtils.isEmpty(classesCustomDTO.getStudentIds())) {
            addClassRegistration(classesCustomDTO.getStudentIds(), classesDTO, courseDTO);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void addClassRegistration(List<Long> studentIds, ClassesDTO classesDTO, CourseDTO courseDTO) {
        List<Student> students = studentRepository.findAllByIdInAndStatus(studentIds, StudentStatus.Studying);
        Map<Long, Student> studentMap = students
            .stream()
            .collect(Collectors.toMap(Student::getId, Function.identity(), (existing, replacement) -> replacement));
        List<ClassRegistrationStatus> statuses = Arrays.asList(ClassRegistrationStatus.Cancelled, ClassRegistrationStatus.Failed);
        List<StudentAttempt> studentAttempts = classRegistrationRepository.findByStudentAttempt(
            statuses,
            studentIds,
            Objects.isNull(courseDTO) ? null : ObjectUtils.defaultIfNull(courseDTO.getId(), null)
        );
        Map<Long, StudentAttempt> studentAttemptsMap = studentAttempts
            .stream()
            .collect(Collectors.toMap(StudentAttempt::getStudentId, Function.identity(), (existing, replacement) -> replacement));

        List<ClassRegistration> classRegistrations = classRegistrationRepository.findAllByClassesIdAndStudentId(
            classesDTO.getId(),
            studentIds
        );
        Map<Long, ClassRegistration> classRegistrationsMap = classRegistrations
            .stream()
            .collect(Collectors.toMap(x -> x.getStudent().getId(), Function.identity(), (existing, replacement) -> replacement));

        List<Grades> gradesListCurrent = gradesRepository.findAllByClassesIdAndStudentId(classesDTO.getId(), studentIds);
        Map<Long, Grades> gradesMap = gradesListCurrent
            .stream()
            .collect(Collectors.toMap(x -> x.getStudent().getId(), Function.identity(), (existing, replacement) -> replacement));

        // create class_register
        List<ClassRegistration> classRegistrationList = new ArrayList<>();
        List<Grades> gradesList = new ArrayList<>();
        for (Long id : studentIds) {
            Student student = studentMap.get(id);
            if (Objects.isNull(student)) {
                continue;
            }

            ClassRegistration itemCurrent = classRegistrationsMap.get(id);
            Classes classes = new Classes();
            classes.setId(classesDTO.getId());
            if (itemCurrent != null) {
                itemCurrent.setStatus(ClassRegistrationStatus.InProgress);
                classRegistrationList.add(itemCurrent);
            } else {
                ClassRegistration registration = new ClassRegistration();
                registration.setClasses(classes);
                registration.setStudent(student);
                registration.setRegisterDate(Instant.now());
                registration.setStatus(ClassRegistrationStatus.InProgress);
                classRegistrationList.add(registration);
            }

            Grades gradesCurrent = gradesMap.get(id);
            if (gradesCurrent != null) {
                gradesCurrent.setStatus(true);
                gradesList.add(gradesCurrent);
            } else {
                Grades grades = new Grades();
                grades.setStudent(student);
                grades.setClasses(classes);
                grades.setStatus(true);
                grades.setCredit(Objects.isNull(courseDTO) ? null : ObjectUtils.defaultIfNull(courseDTO.getCredits(), null));
                StudentAttempt studentAttempt = studentAttemptsMap.get(id);
                Integer attempt = Math.toIntExact(
                    Objects.isNull(studentAttempt) || Objects.isNull(studentAttempt.getTotalAttempt())
                        ? 1
                        : studentAttempt.getTotalAttempt()
                );
                grades.studyAttempt(attempt); // so lan thi
                gradesList.add(grades);
            }
        }
        classRegistrationRepository.saveAll(classRegistrationList);
        gradesRepository.saveAll(gradesList);
    }

    public void deactivateClassesRegistration(Long classId, List<Long> student) throws Exception {
        List<ClassRegistration> classRegistrationList = classRegistrationRepository.findAllByClassesIdAndStudentId(classId, student);
        List<Grades> gradesList = gradesRepository.findAllByClassesIdAndStudentId(classId, student);

        List<ClassRegistration> classRegistrationUpdate = new ArrayList<>();
        for (ClassRegistration classRegistration : classRegistrationList) {
            ClassRegistration update = classRegistration;
            update.setStatus(ClassRegistrationStatus.Cancelled);
            classRegistrationUpdate.add(update);
        }

        List<Grades> gradesUpdate = new ArrayList<>();
        for (Grades grades : gradesList) {
            Grades update = grades;
            update.setStatus(false);
            gradesUpdate.add(update);
        }

        classRegistrationRepository.saveAll(classRegistrationUpdate);
        gradesRepository.saveAll(gradesUpdate);
    }
}
