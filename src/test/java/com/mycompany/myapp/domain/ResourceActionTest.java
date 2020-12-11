package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourceActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceAction.class);
        ResourceAction resourceAction1 = new ResourceAction();
        resourceAction1.setId(1L);
        ResourceAction resourceAction2 = new ResourceAction();
        resourceAction2.setId(resourceAction1.getId());
        assertThat(resourceAction1).isEqualTo(resourceAction2);
        resourceAction2.setId(2L);
        assertThat(resourceAction1).isNotEqualTo(resourceAction2);
        resourceAction1.setId(null);
        assertThat(resourceAction1).isNotEqualTo(resourceAction2);
    }
}
