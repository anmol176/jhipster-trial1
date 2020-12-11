package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEntity.class);
        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setId(1L);
        CompanyEntity companyEntity2 = new CompanyEntity();
        companyEntity2.setId(companyEntity1.getId());
        assertThat(companyEntity1).isEqualTo(companyEntity2);
        companyEntity2.setId(2L);
        assertThat(companyEntity1).isNotEqualTo(companyEntity2);
        companyEntity1.setId(null);
        assertThat(companyEntity1).isNotEqualTo(companyEntity2);
    }
}
