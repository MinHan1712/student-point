package student.point.service.mapper;

import static student.point.domain.GradesAsserts.*;
import static student.point.domain.GradesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GradesMapperTest {

    private GradesMapper gradesMapper;

    @BeforeEach
    void setUp() {
        gradesMapper = new GradesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGradesSample1();
        var actual = gradesMapper.toEntity(gradesMapper.toDto(expected));
        assertGradesAllPropertiesEquals(expected, actual);
    }
}
