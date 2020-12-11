package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourceActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceActionDTO.class);
        ResourceActionDTO resourceActionDTO1 = new ResourceActionDTO();
        resourceActionDTO1.setId(1L);
        ResourceActionDTO resourceActionDTO2 = new ResourceActionDTO();
        assertThat(resourceActionDTO1).isNotEqualTo(resourceActionDTO2);
        resourceActionDTO2.setId(resourceActionDTO1.getId());
        assertThat(resourceActionDTO1).isEqualTo(resourceActionDTO2);
        resourceActionDTO2.setId(2L);
        assertThat(resourceActionDTO1).isNotEqualTo(resourceActionDTO2);
        resourceActionDTO1.setId(null);
        assertThat(resourceActionDTO1).isNotEqualTo(resourceActionDTO2);
    }
}
