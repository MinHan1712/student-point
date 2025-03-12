package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ModuleConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleConfigurationDTO.class);
        ModuleConfigurationDTO moduleConfigurationDTO1 = new ModuleConfigurationDTO();
        moduleConfigurationDTO1.setId(1L);
        ModuleConfigurationDTO moduleConfigurationDTO2 = new ModuleConfigurationDTO();
        assertThat(moduleConfigurationDTO1).isNotEqualTo(moduleConfigurationDTO2);
        moduleConfigurationDTO2.setId(moduleConfigurationDTO1.getId());
        assertThat(moduleConfigurationDTO1).isEqualTo(moduleConfigurationDTO2);
        moduleConfigurationDTO2.setId(2L);
        assertThat(moduleConfigurationDTO1).isNotEqualTo(moduleConfigurationDTO2);
        moduleConfigurationDTO1.setId(null);
        assertThat(moduleConfigurationDTO1).isNotEqualTo(moduleConfigurationDTO2);
    }
}
