package student.point.service.mapper;

import org.mapstruct.Mapper;
import student.point.service.api.dto.StudentCustomDTO;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link StudentDTO} and its DTO {@link StudentCustomDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentCustomMapper extends EntityMapper<StudentCustomDTO, StudentDTO> {}
