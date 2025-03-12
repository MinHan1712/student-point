package student.point.service.mapper;

import static student.point.domain.TeachersAsserts.*;
import static student.point.domain.TeachersTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeachersMapperTest {

    private TeachersMapper teachersMapper;

    @BeforeEach
    void setUp() {
        teachersMapper = new TeachersMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTeachersSample1();
        var actual = teachersMapper.toEntity(teachersMapper.toDto(expected));
        assertTeachersAllPropertiesEquals(expected, actual);
    }
}
