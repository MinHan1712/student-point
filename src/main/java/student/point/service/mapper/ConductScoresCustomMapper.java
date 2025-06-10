package student.point.service.mapper;

import org.mapstruct.Mapper;
import student.point.domain.ConductScores;
import student.point.service.api.dto.ConductScoresCustomDTO;

/**
 * Mapper for the entity {@link ConductScores} and its DTO {@link ConductScoresCustomDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConductScoresCustomMapper extends EntityMapper<ConductScoresCustomDTO, ConductScores> {}
