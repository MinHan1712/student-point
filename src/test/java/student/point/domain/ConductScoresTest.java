package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.ConductScoresTestSamples.*;
import static student.point.domain.StudentTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ConductScoresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConductScores.class);
        ConductScores conductScores1 = getConductScoresSample1();
        ConductScores conductScores2 = new ConductScores();
        assertThat(conductScores1).isNotEqualTo(conductScores2);

        conductScores2.setId(conductScores1.getId());
        assertThat(conductScores1).isEqualTo(conductScores2);

        conductScores2 = getConductScoresSample2();
        assertThat(conductScores1).isNotEqualTo(conductScores2);
    }

    @Test
    void studentTest() {
        ConductScores conductScores = getConductScoresRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        conductScores.setStudent(studentBack);
        assertThat(conductScores.getStudent()).isEqualTo(studentBack);

        conductScores.student(null);
        assertThat(conductScores.getStudent()).isNull();
    }
}
