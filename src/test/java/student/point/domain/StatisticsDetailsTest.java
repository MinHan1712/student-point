package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.StatisticsDetailsTestSamples.*;
import static student.point.domain.StatisticsTestSamples.*;
import static student.point.domain.StudentTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class StatisticsDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticsDetails.class);
        StatisticsDetails statisticsDetails1 = getStatisticsDetailsSample1();
        StatisticsDetails statisticsDetails2 = new StatisticsDetails();
        assertThat(statisticsDetails1).isNotEqualTo(statisticsDetails2);

        statisticsDetails2.setId(statisticsDetails1.getId());
        assertThat(statisticsDetails1).isEqualTo(statisticsDetails2);

        statisticsDetails2 = getStatisticsDetailsSample2();
        assertThat(statisticsDetails1).isNotEqualTo(statisticsDetails2);
    }

    @Test
    void studentTest() {
        StatisticsDetails statisticsDetails = getStatisticsDetailsRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        statisticsDetails.setStudent(studentBack);
        assertThat(statisticsDetails.getStudent()).isEqualTo(studentBack);

        statisticsDetails.student(null);
        assertThat(statisticsDetails.getStudent()).isNull();
    }

    @Test
    void statisticsTest() {
        StatisticsDetails statisticsDetails = getStatisticsDetailsRandomSampleGenerator();
        Statistics statisticsBack = getStatisticsRandomSampleGenerator();

        statisticsDetails.setStatistics(statisticsBack);
        assertThat(statisticsDetails.getStatistics()).isEqualTo(statisticsBack);

        statisticsDetails.statistics(null);
        assertThat(statisticsDetails.getStatistics()).isNull();
    }
}
