package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Statistics;
import student.point.service.dto.StatisticsDTO;

/**
 * Mapper for the entity {@link Statistics} and its DTO {@link StatisticsDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatisticsMapper extends EntityMapper<StatisticsDTO, Statistics> {}
