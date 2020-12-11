package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyEntityAccountMapperTest {

    private CompanyEntityAccountMapper companyEntityAccountMapper;

    @BeforeEach
    public void setUp() {
        companyEntityAccountMapper = new CompanyEntityAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyEntityAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyEntityAccountMapper.fromId(null)).isNull();
    }
}
