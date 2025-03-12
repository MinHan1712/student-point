package student.point.service.mapper;

import static student.point.domain.ConductScoresAsserts.*;
import static student.point.domain.ConductScoresTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConductScoresMapperTest {

    private ConductScoresMapper conductScoresMapper;

    @BeforeEach
    void setUp() {
        conductScoresMapper = new ConductScoresMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConductScoresSample1();
        var actual = conductScoresMapper.toEntity(conductScoresMapper.toDto(expected));
        assertConductScoresAllPropertiesEquals(expected, actual);
    }
}
