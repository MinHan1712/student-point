package student.point.service.mapper;

import static student.point.domain.FacultiesAsserts.*;
import static student.point.domain.FacultiesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FacultiesMapperTest {

    private FacultiesMapper facultiesMapper;

    @BeforeEach
    void setUp() {
        facultiesMapper = new FacultiesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFacultiesSample1();
        var actual = facultiesMapper.toEntity(facultiesMapper.toDto(expected));
        assertFacultiesAllPropertiesEquals(expected, actual);
    }
}
