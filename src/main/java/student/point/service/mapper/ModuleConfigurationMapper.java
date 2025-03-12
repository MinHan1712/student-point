package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.ModuleConfiguration;
import student.point.service.dto.ModuleConfigurationDTO;

/**
 * Mapper for the entity {@link ModuleConfiguration} and its DTO {@link ModuleConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModuleConfigurationMapper extends EntityMapper<ModuleConfigurationDTO, ModuleConfiguration> {}
