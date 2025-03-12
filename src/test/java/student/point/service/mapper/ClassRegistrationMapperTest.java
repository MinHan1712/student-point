package student.point.service.mapper;

import static student.point.domain.ClassRegistrationAsserts.*;
import static student.point.domain.ClassRegistrationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassRegistrationMapperTest {

    private ClassRegistrationMapper classRegistrationMapper;

    @BeforeEach
    void setUp() {
        classRegistrationMapper = new ClassRegistrationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClassRegistrationSample1();
        var actual = classRegistrationMapper.toEntity(classRegistrationMapper.toDto(expected));
        assertClassRegistrationAllPropertiesEquals(expected, actual);
    }
}
