package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyGroupDTO.class);
        CompanyGroupDTO companyGroupDTO1 = new CompanyGroupDTO();
        companyGroupDTO1.setId(1L);
        CompanyGroupDTO companyGroupDTO2 = new CompanyGroupDTO();
        assertThat(companyGroupDTO1).isNotEqualTo(companyGroupDTO2);
        companyGroupDTO2.setId(companyGroupDTO1.getId());
        assertThat(companyGroupDTO1).isEqualTo(companyGroupDTO2);
        companyGroupDTO2.setId(2L);
        assertThat(companyGroupDTO1).isNotEqualTo(companyGroupDTO2);
        companyGroupDTO1.setId(null);
        assertThat(companyGroupDTO1).isNotEqualTo(companyGroupDTO2);
    }
}
