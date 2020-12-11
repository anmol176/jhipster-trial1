package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceEnrollmentMapperTest {

    private ServiceEnrollmentMapper serviceEnrollmentMapper;

    @BeforeEach
    public void setUp() {
        serviceEnrollmentMapper = new ServiceEnrollmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceEnrollmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceEnrollmentMapper.fromId(null)).isNull();
    }
}
