package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.ClassCourse;
import student.point.domain.Faculties;
import student.point.service.dto.ClassCourseDTO;
import student.point.service.dto.FacultiesDTO;

/**
 * Mapper for the entity {@link ClassCourse} and its DTO {@link ClassCourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassCourseMapper extends EntityMapper<ClassCourseDTO, ClassCourse> {
    @Mapping(target = "faculties", source = "faculties", qualifiedByName = "facultiesId")
    ClassCourseDTO toDto(ClassCourse s);

    @Named("facultiesId")
    FacultiesDTO toDtoFacultiesId(Faculties faculties);
}
