package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourceActionGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceActionGroup.class);
        ResourceActionGroup resourceActionGroup1 = new ResourceActionGroup();
        resourceActionGroup1.setId(1L);
        ResourceActionGroup resourceActionGroup2 = new ResourceActionGroup();
        resourceActionGroup2.setId(resourceActionGroup1.getId());
        assertThat(resourceActionGroup1).isEqualTo(resourceActionGroup2);
        resourceActionGroup2.setId(2L);
        assertThat(resourceActionGroup1).isNotEqualTo(resourceActionGroup2);
        resourceActionGroup1.setId(null);
        assertThat(resourceActionGroup1).isNotEqualTo(resourceActionGroup2);
    }
}
