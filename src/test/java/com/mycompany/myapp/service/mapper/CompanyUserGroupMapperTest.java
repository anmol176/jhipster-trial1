package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyUserGroupMapperTest {

    private CompanyUserGroupMapper companyUserGroupMapper;

    @BeforeEach
    public void setUp() {
        companyUserGroupMapper = new CompanyUserGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyUserGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyUserGroupMapper.fromId(null)).isNull();
    }
}
