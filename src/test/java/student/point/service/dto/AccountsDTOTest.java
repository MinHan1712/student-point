package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class AccountsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountsDTO.class);
        AccountsDTO accountsDTO1 = new AccountsDTO();
        accountsDTO1.setId(1L);
        AccountsDTO accountsDTO2 = new AccountsDTO();
        assertThat(accountsDTO1).isNotEqualTo(accountsDTO2);
        accountsDTO2.setId(accountsDTO1.getId());
        assertThat(accountsDTO1).isEqualTo(accountsDTO2);
        accountsDTO2.setId(2L);
        assertThat(accountsDTO1).isNotEqualTo(accountsDTO2);
        accountsDTO1.setId(null);
        assertThat(accountsDTO1).isNotEqualTo(accountsDTO2);
    }
}
