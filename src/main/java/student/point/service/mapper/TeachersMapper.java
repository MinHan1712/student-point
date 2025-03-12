package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Faculties;
import student.point.domain.Teachers;
import student.point.service.dto.FacultiesDTO;
import student.point.service.dto.TeachersDTO;

/**
 * Mapper for the entity {@link Teachers} and its DTO {@link TeachersDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeachersMapper extends EntityMapper<TeachersDTO, Teachers> {
    @Mapping(target = "faculties", source = "faculties", qualifiedByName = "facultiesId")
    TeachersDTO toDto(Teachers s);

    @Named("facultiesId")
    FacultiesDTO toDtoFacultiesId(Faculties faculties);
}
