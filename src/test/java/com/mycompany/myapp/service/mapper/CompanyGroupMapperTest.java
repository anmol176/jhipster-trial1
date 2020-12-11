package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyGroupMapperTest {

    private CompanyGroupMapper companyGroupMapper;

    @BeforeEach
    public void setUp() {
        companyGroupMapper = new CompanyGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyGroupMapper.fromId(null)).isNull();
    }
}
