package com.doortodoor.mapper;

import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto userDaoBeanToDto(UserDaoBean userDaoBean);
    UserDaoBean userDtoToDaoBean(UserDto userDto);
}