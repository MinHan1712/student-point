package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class TeachersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachersDTO.class);
        TeachersDTO teachersDTO1 = new TeachersDTO();
        teachersDTO1.setId(1L);
        TeachersDTO teachersDTO2 = new TeachersDTO();
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
        teachersDTO2.setId(teachersDTO1.getId());
        assertThat(teachersDTO1).isEqualTo(teachersDTO2);
        teachersDTO2.setId(2L);
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
        teachersDTO1.setId(null);
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
    }
}
