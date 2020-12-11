package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ServiceEnrollmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEnrollment.class);
        ServiceEnrollment serviceEnrollment1 = new ServiceEnrollment();
        serviceEnrollment1.setId(1L);
        ServiceEnrollment serviceEnrollment2 = new ServiceEnrollment();
        serviceEnrollment2.setId(serviceEnrollment1.getId());
        assertThat(serviceEnrollment1).isEqualTo(serviceEnrollment2);
        serviceEnrollment2.setId(2L);
        assertThat(serviceEnrollment1).isNotEqualTo(serviceEnrollment2);
        serviceEnrollment1.setId(null);
        assertThat(serviceEnrollment1).isNotEqualTo(serviceEnrollment2);
    }
}
