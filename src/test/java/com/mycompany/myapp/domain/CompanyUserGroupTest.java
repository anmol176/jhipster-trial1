package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyUserGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUserGroup.class);
        CompanyUserGroup companyUserGroup1 = new CompanyUserGroup();
        companyUserGroup1.setId(1L);
        CompanyUserGroup companyUserGroup2 = new CompanyUserGroup();
        companyUserGroup2.setId(companyUserGroup1.getId());
        assertThat(companyUserGroup1).isEqualTo(companyUserGroup2);
        companyUserGroup2.setId(2L);
        assertThat(companyUserGroup1).isNotEqualTo(companyUserGroup2);
        companyUserGroup1.setId(null);
        assertThat(companyUserGroup1).isNotEqualTo(companyUserGroup2);
    }
}
