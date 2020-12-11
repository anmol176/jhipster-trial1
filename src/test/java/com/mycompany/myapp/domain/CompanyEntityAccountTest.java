package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CompanyEntityAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEntityAccount.class);
        CompanyEntityAccount companyEntityAccount1 = new CompanyEntityAccount();
        companyEntityAccount1.setId(1L);
        CompanyEntityAccount companyEntityAccount2 = new CompanyEntityAccount();
        companyEntityAccount2.setId(companyEntityAccount1.getId());
        assertThat(companyEntityAccount1).isEqualTo(companyEntityAccount2);
        companyEntityAccount2.setId(2L);
        assertThat(companyEntityAccount1).isNotEqualTo(companyEntityAccount2);
        companyEntityAccount1.setId(null);
        assertThat(companyEntityAccount1).isNotEqualTo(companyEntityAccount2);
    }
}
