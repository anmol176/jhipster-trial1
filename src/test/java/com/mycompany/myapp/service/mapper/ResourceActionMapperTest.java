package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourceActionMapperTest {

    private ResourceActionMapper resourceActionMapper;

    @BeforeEach
    public void setUp() {
        resourceActionMapper = new ResourceActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resourceActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resourceActionMapper.fromId(null)).isNull();
    }
}
