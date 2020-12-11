package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyUserGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUserGroupDTO.class);
        CompanyUserGroupDTO companyUserGroupDTO1 = new CompanyUserGroupDTO();
        companyUserGroupDTO1.setId(1L);
        CompanyUserGroupDTO companyUserGroupDTO2 = new CompanyUserGroupDTO();
        assertThat(companyUserGroupDTO1).isNotEqualTo(companyUserGroupDTO2);
        companyUserGroupDTO2.setId(companyUserGroupDTO1.getId());
        assertThat(companyUserGroupDTO1).isEqualTo(companyUserGroupDTO2);
        companyUserGroupDTO2.setId(2L);
        assertThat(companyUserGroupDTO1).isNotEqualTo(companyUserGroupDTO2);
        companyUserGroupDTO1.setId(null);
        assertThat(companyUserGroupDTO1).isNotEqualTo(companyUserGroupDTO2);
    }
}
