package student.point.service.mapper;

import static student.point.domain.ModuleConfigurationAsserts.*;
import static student.point.domain.ModuleConfigurationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModuleConfigurationMapperTest {

    private ModuleConfigurationMapper moduleConfigurationMapper;

    @BeforeEach
    void setUp() {
        moduleConfigurationMapper = new ModuleConfigurationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getModuleConfigurationSample1();
        var actual = moduleConfigurationMapper.toEntity(moduleConfigurationMapper.toDto(expected));
        assertModuleConfigurationAllPropertiesEquals(expected, actual);
    }
}
