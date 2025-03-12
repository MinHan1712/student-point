package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class FacultiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacultiesDTO.class);
        FacultiesDTO facultiesDTO1 = new FacultiesDTO();
        facultiesDTO1.setId(1L);
        FacultiesDTO facultiesDTO2 = new FacultiesDTO();
        assertThat(facultiesDTO1).isNotEqualTo(facultiesDTO2);
        facultiesDTO2.setId(facultiesDTO1.getId());
        assertThat(facultiesDTO1).isEqualTo(facultiesDTO2);
        facultiesDTO2.setId(2L);
        assertThat(facultiesDTO1).isNotEqualTo(facultiesDTO2);
        facultiesDTO1.setId(null);
        assertThat(facultiesDTO1).isNotEqualTo(facultiesDTO2);
    }
}
