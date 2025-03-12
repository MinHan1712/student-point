package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class MasterDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterDataDTO.class);
        MasterDataDTO masterDataDTO1 = new MasterDataDTO();
        masterDataDTO1.setId(1L);
        MasterDataDTO masterDataDTO2 = new MasterDataDTO();
        assertThat(masterDataDTO1).isNotEqualTo(masterDataDTO2);
        masterDataDTO2.setId(masterDataDTO1.getId());
        assertThat(masterDataDTO1).isEqualTo(masterDataDTO2);
        masterDataDTO2.setId(2L);
        assertThat(masterDataDTO1).isNotEqualTo(masterDataDTO2);
        masterDataDTO1.setId(null);
        assertThat(masterDataDTO1).isNotEqualTo(masterDataDTO2);
    }
}
