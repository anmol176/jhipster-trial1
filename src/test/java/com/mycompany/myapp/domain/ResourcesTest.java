package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ResourcesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resources.class);
        Resources resources1 = new Resources();
        resources1.setId(1L);
        Resources resources2 = new Resources();
        resources2.setId(resources1.getId());
        assertThat(resources1).isEqualTo(resources2);
        resources2.setId(2L);
        assertThat(resources1).isNotEqualTo(resources2);
        resources1.setId(null);
        assertThat(resources1).isNotEqualTo(resources2);
    }
}
