package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyEntityAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEntityAccountDTO.class);
        CompanyEntityAccountDTO companyEntityAccountDTO1 = new CompanyEntityAccountDTO();
        companyEntityAccountDTO1.setId(1L);
        CompanyEntityAccountDTO companyEntityAccountDTO2 = new CompanyEntityAccountDTO();
        assertThat(companyEntityAccountDTO1).isNotEqualTo(companyEntityAccountDTO2);
        companyEntityAccountDTO2.setId(companyEntityAccountDTO1.getId());
        assertThat(companyEntityAccountDTO1).isEqualTo(companyEntityAccountDTO2);
        companyEntityAccountDTO2.setId(2L);
        assertThat(companyEntityAccountDTO1).isNotEqualTo(companyEntityAccountDTO2);
        companyEntityAccountDTO1.setId(null);
        assertThat(companyEntityAccountDTO1).isNotEqualTo(companyEntityAccountDTO2);
    }
}
