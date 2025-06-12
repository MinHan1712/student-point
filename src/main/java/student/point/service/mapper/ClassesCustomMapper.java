package student.point.service.mapper;

import org.mapstruct.Mapper;
import student.point.service.api.dto.ClassesCustomDTO;
import student.point.service.dto.ClassesDTO;

/**
 * Mapper for the entity {@link ClassesDTO} and its DTO {@link ClassesCustomDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassesCustomMapper extends EntityMapper<ClassesCustomDTO, ClassesDTO> {}
