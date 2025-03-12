package student.point.service.mapper;

import static student.point.domain.ClassesAsserts.*;
import static student.point.domain.ClassesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassesMapperTest {

    private ClassesMapper classesMapper;

    @BeforeEach
    void setUp() {
        classesMapper = new ClassesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClassesSample1();
        var actual = classesMapper.toEntity(classesMapper.toDto(expected));
        assertClassesAllPropertiesEquals(expected, actual);
    }
}
