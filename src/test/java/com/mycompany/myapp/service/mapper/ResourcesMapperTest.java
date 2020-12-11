package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourcesMapperTest {

    private ResourcesMapper resourcesMapper;

    @BeforeEach
    public void setUp() {
        resourcesMapper = new ResourcesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resourcesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resourcesMapper.fromId(null)).isNull();
    }
}
