package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static student.point.domain.AccountsTestSamples.*;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class AccountsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accounts.class);
        Accounts accounts1 = getAccountsSample1();
        Accounts accounts2 = new Accounts();
        assertThat(accounts1).isNotEqualTo(accounts2);

        accounts2.setId(accounts1.getId());
        assertThat(accounts1).isEqualTo(accounts2);

        accounts2 = getAccountsSample2();
        assertThat(accounts1).isNotEqualTo(accounts2);
    }
}
