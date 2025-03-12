package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Classes;
import student.point.domain.Course;
import student.point.domain.Teachers;
import student.point.service.dto.ClassesDTO;
import student.point.service.dto.CourseDTO;
import student.point.service.dto.TeachersDTO;

/**
 * Mapper for the entity {@link Classes} and its DTO {@link ClassesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassesMapper extends EntityMapper<ClassesDTO, Classes> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    @Mapping(target = "teachers", source = "teachers", qualifiedByName = "teachersId")
    ClassesDTO toDto(Classes s);

    @Named("courseId")
    CourseDTO toDtoCourseId(Course course);

    @Named("teachersId")
    TeachersDTO toDtoTeachersId(Teachers teachers);
}
