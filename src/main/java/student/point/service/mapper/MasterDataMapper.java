package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.MasterData;
import student.point.service.dto.MasterDataDTO;

/**
 * Mapper for the entity {@link MasterData} and its DTO {@link MasterDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface MasterDataMapper extends EntityMapper<MasterDataDTO, MasterData> {}
