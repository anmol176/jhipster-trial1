package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyEntityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEntityDTO.class);
        CompanyEntityDTO companyEntityDTO1 = new CompanyEntityDTO();
        companyEntityDTO1.setId(1L);
        CompanyEntityDTO companyEntityDTO2 = new CompanyEntityDTO();
        assertThat(companyEntityDTO1).isNotEqualTo(companyEntityDTO2);
        companyEntityDTO2.setId(companyEntityDTO1.getId());
        assertThat(companyEntityDTO1).isEqualTo(companyEntityDTO2);
        companyEntityDTO2.setId(2L);
        assertThat(companyEntityDTO1).isNotEqualTo(companyEntityDTO2);
        companyEntityDTO1.setId(null);
        assertThat(companyEntityDTO1).isNotEqualTo(companyEntityDTO2);
    }
}
