package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Statistics;
import student.point.domain.StatisticsDetails;
import student.point.domain.Student;
import student.point.service.dto.StatisticsDTO;
import student.point.service.dto.StatisticsDetailsDTO;
import student.point.service.dto.StudentDTO;

/**
 * Mapper for the entity {@link StatisticsDetails} and its DTO {@link StatisticsDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatisticsDetailsMapper extends EntityMapper<StatisticsDetailsDTO, StatisticsDetails> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    @Mapping(target = "statistics", source = "statistics", qualifiedByName = "statisticsId")
    StatisticsDetailsDTO toDto(StatisticsDetails s);

    @Named("studentId")
    StudentDTO toDtoStudentId(Student student);

    @Named("statisticsId")
    StatisticsDTO toDtoStatisticsId(Statistics statistics);
}
