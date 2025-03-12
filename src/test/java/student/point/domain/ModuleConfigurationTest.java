package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ModuleConfigurationTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ModuleConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleConfiguration.class);
        ModuleConfiguration moduleConfiguration1 = getModuleConfigurationSample1();
        ModuleConfiguration moduleConfiguration2 = new ModuleConfiguration();
        assertThat(moduleConfiguration1).isNotEqualTo(moduleConfiguration2);

        moduleConfiguration2.setId(moduleConfiguration1.getId());
        assertThat(moduleConfiguration1).isEqualTo(moduleConfiguration2);

        moduleConfiguration2 = getModuleConfigurationSample2();
        assertThat(moduleConfiguration1).isNotEqualTo(moduleConfiguration2);
    }
}
