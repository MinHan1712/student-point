package student.point.service.mapper;

import static student.point.domain.MasterDataAsserts.*;
import static student.point.domain.MasterDataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MasterDataMapperTest {

    private MasterDataMapper masterDataMapper;

    @BeforeEach
    void setUp() {
        masterDataMapper = new MasterDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMasterDataSample1();
        var actual = masterDataMapper.toEntity(masterDataMapper.toDto(expected));
        assertMasterDataAllPropertiesEquals(expected, actual);
    }
}
