package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyEntityMapperTest {

    private CompanyEntityMapper companyEntityMapper;

    @BeforeEach
    public void setUp() {
        companyEntityMapper = new CompanyEntityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyEntityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyEntityMapper.fromId(null)).isNull();
    }
}
