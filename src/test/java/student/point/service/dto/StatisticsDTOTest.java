package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class StatisticsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticsDTO.class);
        StatisticsDTO statisticsDTO1 = new StatisticsDTO();
        statisticsDTO1.setId(1L);
        StatisticsDTO statisticsDTO2 = new StatisticsDTO();
        assertThat(statisticsDTO1).isNotEqualTo(statisticsDTO2);
        statisticsDTO2.setId(statisticsDTO1.getId());
        assertThat(statisticsDTO1).isEqualTo(statisticsDTO2);
        statisticsDTO2.setId(2L);
        assertThat(statisticsDTO1).isNotEqualTo(statisticsDTO2);
        statisticsDTO1.setId(null);
        assertThat(statisticsDTO1).isNotEqualTo(statisticsDTO2);
    }
}
