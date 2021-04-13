package com.doortodoor.mapper;

import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.OrganizationDto;
import com.doortodoor.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {
    OrganizationDto organizationDaoBeanToDto (OrganizationDaoBean organizationDaoBean);
    OrganizationDaoBean organizationDtoToDaoBean (OrganizationDto organizationDto);
}