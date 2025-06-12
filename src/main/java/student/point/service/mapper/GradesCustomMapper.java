package student.point.service.mapper;

import org.mapstruct.Mapper;
import student.point.domain.Grades;
import student.point.service.api.dto.GradesCustomDTO;

/**
 * Mapper for the entity {@link Grades} and its DTO {@link GradesCustomDTO}.
 */
@Mapper(componentModel = "spring")
public interface GradesCustomMapper extends EntityMapper<GradesCustomDTO, Grades> {}
