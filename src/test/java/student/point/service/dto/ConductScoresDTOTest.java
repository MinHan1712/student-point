package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ConductScoresDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConductScoresDTO.class);
        ConductScoresDTO conductScoresDTO1 = new ConductScoresDTO();
        conductScoresDTO1.setId(1L);
        ConductScoresDTO conductScoresDTO2 = new ConductScoresDTO();
        assertThat(conductScoresDTO1).isNotEqualTo(conductScoresDTO2);
        conductScoresDTO2.setId(conductScoresDTO1.getId());
        assertThat(conductScoresDTO1).isEqualTo(conductScoresDTO2);
        conductScoresDTO2.setId(2L);
        assertThat(conductScoresDTO1).isNotEqualTo(conductScoresDTO2);
        conductScoresDTO1.setId(null);
        assertThat(conductScoresDTO1).isNotEqualTo(conductScoresDTO2);
    }
}
