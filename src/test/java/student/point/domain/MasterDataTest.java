package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.MasterDataTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class MasterDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterData.class);
        MasterData masterData1 = getMasterDataSample1();
        MasterData masterData2 = new MasterData();
        assertThat(masterData1).isNotEqualTo(masterData2);

        masterData2.setId(masterData1.getId());
        assertThat(masterData1).isEqualTo(masterData2);

        masterData2 = getMasterDataSample2();
        assertThat(masterData1).isNotEqualTo(masterData2);
    }
}
