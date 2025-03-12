package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.ConductScores;
import student.point.domain.Student;
import student.point.service.dto.ConductScoresDTO;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link ConductScores} and its DTO {@link ConductScoresDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConductScoresMapper extends EntityMapper<ConductScoresDTO, ConductScores> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    ConductScoresDTO toDto(ConductScores s);

    @Named("studentId")
    StudentDTO toDtoStudentId(Student student);
}
