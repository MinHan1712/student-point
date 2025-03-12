package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class StatisticsDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticsDetailsDTO.class);
        StatisticsDetailsDTO statisticsDetailsDTO1 = new StatisticsDetailsDTO();
        statisticsDetailsDTO1.setId(1L);
        StatisticsDetailsDTO statisticsDetailsDTO2 = new StatisticsDetailsDTO();
        assertThat(statisticsDetailsDTO1).isNotEqualTo(statisticsDetailsDTO2);
        statisticsDetailsDTO2.setId(statisticsDetailsDTO1.getId());
        assertThat(statisticsDetailsDTO1).isEqualTo(statisticsDetailsDTO2);
        statisticsDetailsDTO2.setId(2L);
        assertThat(statisticsDetailsDTO1).isNotEqualTo(statisticsDetailsDTO2);
        statisticsDetailsDTO1.setId(null);
        assertThat(statisticsDetailsDTO1).isNotEqualTo(statisticsDetailsDTO2);
    }
}
