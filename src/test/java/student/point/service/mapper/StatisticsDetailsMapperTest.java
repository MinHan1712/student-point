package student.point.service.mapper;

import static student.point.domain.StatisticsDetailsAsserts.*;
import static student.point.domain.StatisticsDetailsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticsDetailsMapperTest {

    private StatisticsDetailsMapper statisticsDetailsMapper;

    @BeforeEach
    void setUp() {
        statisticsDetailsMapper = new StatisticsDetailsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStatisticsDetailsSample1();
        var actual = statisticsDetailsMapper.toEntity(statisticsDetailsMapper.toDto(expected));
        assertStatisticsDetailsAllPropertiesEquals(expected, actual);
    }
}
