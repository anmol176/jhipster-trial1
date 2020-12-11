package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourceActionGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceActionGroupDTO.class);
        ResourceActionGroupDTO resourceActionGroupDTO1 = new ResourceActionGroupDTO();
        resourceActionGroupDTO1.setId(1L);
        ResourceActionGroupDTO resourceActionGroupDTO2 = new ResourceActionGroupDTO();
        assertThat(resourceActionGroupDTO1).isNotEqualTo(resourceActionGroupDTO2);
        resourceActionGroupDTO2.setId(resourceActionGroupDTO1.getId());
        assertThat(resourceActionGroupDTO1).isEqualTo(resourceActionGroupDTO2);
        resourceActionGroupDTO2.setId(2L);
        assertThat(resourceActionGroupDTO1).isNotEqualTo(resourceActionGroupDTO2);
        resourceActionGroupDTO1.setId(null);
        assertThat(resourceActionGroupDTO1).isNotEqualTo(resourceActionGroupDTO2);
    }
}
