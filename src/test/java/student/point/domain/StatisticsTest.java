package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.StatisticsDetailsTestSamples.*;
import static student.point.domain.StatisticsTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class StatisticsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistics.class);
        Statistics statistics1 = getStatisticsSample1();
        Statistics statistics2 = new Statistics();
        assertThat(statistics1).isNotEqualTo(statistics2);

        statistics2.setId(statistics1.getId());
        assertThat(statistics1).isEqualTo(statistics2);

        statistics2 = getStatisticsSample2();
        assertThat(statistics1).isNotEqualTo(statistics2);
    }

    @Test
    void statisticsDetailsTest() {
        Statistics statistics = getStatisticsRandomSampleGenerator();
        StatisticsDetails statisticsDetailsBack = getStatisticsDetailsRandomSampleGenerator();

        statistics.addStatisticsDetails(statisticsDetailsBack);
        assertThat(statistics.getStatisticsDetails()).containsOnly(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStatistics()).isEqualTo(statistics);

        statistics.removeStatisticsDetails(statisticsDetailsBack);
        assertThat(statistics.getStatisticsDetails()).doesNotContain(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStatistics()).isNull();

        statistics.statisticsDetails(new HashSet<>(Set.of(statisticsDetailsBack)));
        assertThat(statistics.getStatisticsDetails()).containsOnly(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStatistics()).isEqualTo(statistics);

        statistics.setStatisticsDetails(new HashSet<>());
        assertThat(statistics.getStatisticsDetails()).doesNotContain(statisticsDetailsBack);
        assertThat(statisticsDetailsBack.getStatistics()).isNull();
    }
}
