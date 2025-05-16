package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Course;
import student.point.domain.CourseFaculties;
import student.point.domain.Faculties;
import student.point.service.dto.CourseDTO;
import student.point.service.dto.CourseFacultiesDTO;
import student.point.service.dto.FacultiesDTO;

/**
 * Mapper for the entity {@link CourseFaculties} and its DTO {@link CourseFacultiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseFacultiesMapper extends EntityMapper<CourseFacultiesDTO, CourseFaculties> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    @Mapping(target = "faculties", source = "faculties", qualifiedByName = "facultiesId")
    CourseFacultiesDTO toDto(CourseFaculties s);

    @Named("courseId")
    CourseDTO toDtoCourseId(Course course);

    @Named("facultiesId")
    FacultiesDTO toDtoFacultiesId(Faculties faculties);
}
