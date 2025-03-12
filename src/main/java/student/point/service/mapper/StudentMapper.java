package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Student;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {}
