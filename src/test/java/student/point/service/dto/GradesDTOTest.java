package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class GradesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradesDTO.class);
        GradesDTO gradesDTO1 = new GradesDTO();
        gradesDTO1.setId(1L);
        GradesDTO gradesDTO2 = new GradesDTO();
        assertThat(gradesDTO1).isNotEqualTo(gradesDTO2);
        gradesDTO2.setId(gradesDTO1.getId());
        assertThat(gradesDTO1).isEqualTo(gradesDTO2);
        gradesDTO2.setId(2L);
        assertThat(gradesDTO1).isNotEqualTo(gradesDTO2);
        gradesDTO1.setId(null);
        assertThat(gradesDTO1).isNotEqualTo(gradesDTO2);
    }
}
