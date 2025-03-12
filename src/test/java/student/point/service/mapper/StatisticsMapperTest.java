package student.point.service.mapper;

import static student.point.domain.StatisticsAsserts.*;
import static student.point.domain.StatisticsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticsMapperTest {

    private StatisticsMapper statisticsMapper;

    @BeforeEach
    void setUp() {
        statisticsMapper = new StatisticsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStatisticsSample1();
        var actual = statisticsMapper.toEntity(statisticsMapper.toDto(expected));
        assertStatisticsAllPropertiesEquals(expected, actual);
    }
}
