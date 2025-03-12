package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Classes;
import student.point.domain.Grades;
import student.point.domain.Student;
import student.point.service.dto.ClassesDTO;
import student.point.service.dto.GradesDTO;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link Grades} and its DTO {@link GradesDTO}.
 */
@Mapper(componentModel = "spring")
public interface GradesMapper extends EntityMapper<GradesDTO, Grades> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    @Mapping(target = "classes", source = "classes", qualifiedByName = "classesId")
    GradesDTO toDto(Grades s);

    @Named("studentId")
    StudentDTO toDtoStudentId(Student student);

    @Named("classesId")
    ClassesDTO toDtoClassesId(Classes classes);
}
