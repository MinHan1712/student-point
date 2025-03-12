package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ClassRegistrationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRegistrationDTO.class);
        ClassRegistrationDTO classRegistrationDTO1 = new ClassRegistrationDTO();
        classRegistrationDTO1.setId(1L);
        ClassRegistrationDTO classRegistrationDTO2 = new ClassRegistrationDTO();
        assertThat(classRegistrationDTO1).isNotEqualTo(classRegistrationDTO2);
        classRegistrationDTO2.setId(classRegistrationDTO1.getId());
        assertThat(classRegistrationDTO1).isEqualTo(classRegistrationDTO2);
        classRegistrationDTO2.setId(2L);
        assertThat(classRegistrationDTO1).isNotEqualTo(classRegistrationDTO2);
        classRegistrationDTO1.setId(null);
        assertThat(classRegistrationDTO1).isNotEqualTo(classRegistrationDTO2);
    }
}
