package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Faculties;
import student.point.service.dto.FacultiesDTO;

/**
 * Mapper for the entity {@link Faculties} and its DTO {@link FacultiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface FacultiesMapper extends EntityMapper<FacultiesDTO, Faculties> {}
