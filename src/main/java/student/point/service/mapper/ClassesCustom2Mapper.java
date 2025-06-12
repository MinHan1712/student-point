package student.point.service.mapper;

import org.mapstruct.Mapper;
import student.point.domain.Classes;
import student.point.service.api.dto.ClassesCustomDTO;

/**
 * Mapper for the entity {@link Classes} and its DTO {@link ClassesCustomDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassesCustom2Mapper extends EntityMapper<ClassesCustomDTO, Classes> {}
