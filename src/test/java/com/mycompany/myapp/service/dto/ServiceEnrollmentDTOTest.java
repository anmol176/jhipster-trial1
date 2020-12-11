package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ServiceEnrollmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceEnrollmentDTO.class);
        ServiceEnrollmentDTO serviceEnrollmentDTO1 = new ServiceEnrollmentDTO();
        serviceEnrollmentDTO1.setId(1L);
        ServiceEnrollmentDTO serviceEnrollmentDTO2 = new ServiceEnrollmentDTO();
        assertThat(serviceEnrollmentDTO1).isNotEqualTo(serviceEnrollmentDTO2);
        serviceEnrollmentDTO2.setId(serviceEnrollmentDTO1.getId());
        assertThat(serviceEnrollmentDTO1).isEqualTo(serviceEnrollmentDTO2);
        serviceEnrollmentDTO2.setId(2L);
        assertThat(serviceEnrollmentDTO1).isNotEqualTo(serviceEnrollmentDTO2);
        serviceEnrollmentDTO1.setId(null);
        assertThat(serviceEnrollmentDTO1).isNotEqualTo(serviceEnrollmentDTO2);
    }
}
