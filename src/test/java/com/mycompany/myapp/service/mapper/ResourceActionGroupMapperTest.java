package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourceActionGroupMapperTest {

    private ResourceActionGroupMapper resourceActionGroupMapper;

    @BeforeEach
    public void setUp() {
        resourceActionGroupMapper = new ResourceActionGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(resourceActionGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(resourceActionGroupMapper.fromId(null)).isNull();
    }
}
