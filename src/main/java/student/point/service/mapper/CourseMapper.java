package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Course;
import student.point.service.dto.CourseDTO;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {}
