package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourcesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourcesDTO.class);
        ResourcesDTO resourcesDTO1 = new ResourcesDTO();
        resourcesDTO1.setId(1L);
        ResourcesDTO resourcesDTO2 = new ResourcesDTO();
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
        resourcesDTO2.setId(resourcesDTO1.getId());
        assertThat(resourcesDTO1).isEqualTo(resourcesDTO2);
        resourcesDTO2.setId(2L);
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
        resourcesDTO1.setId(null);
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
    }
}
