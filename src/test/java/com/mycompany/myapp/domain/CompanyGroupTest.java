package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyGroup.class);
        CompanyGroup companyGroup1 = new CompanyGroup();
        companyGroup1.setId(1L);
        CompanyGroup companyGroup2 = new CompanyGroup();
        companyGroup2.setId(companyGroup1.getId());
        assertThat(companyGroup1).isEqualTo(companyGroup2);
        companyGroup2.setId(2L);
        assertThat(companyGroup1).isNotEqualTo(companyGroup2);
        companyGroup1.setId(null);
        assertThat(companyGroup1).isNotEqualTo(companyGroup2);
    }
}
