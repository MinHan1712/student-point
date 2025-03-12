package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.ClassRegistration;
import student.point.domain.Classes;
import student.point.domain.Student;
import student.point.service.dto.ClassRegistrationDTO;
import student.point.service.dto.ClassesDTO;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link ClassRegistration} and its DTO {@link ClassRegistrationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassRegistrationMapper extends EntityMapper<ClassRegistrationDTO, ClassRegistration> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    @Mapping(target = "classes", source = "classes", qualifiedByName = "classesId")
    ClassRegistrationDTO toDto(ClassRegistration s);

    @Named("studentId")
    StudentDTO toDtoStudentId(Student student);

    @Named("classesId")
    ClassesDTO toDtoClassesId(Classes classes);
}
