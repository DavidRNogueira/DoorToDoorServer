package com.doortodoor.mapper;

import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(source = "numberOfSeats", target = "seatCount")
    UserDto userBeanToDto(UserDaoBean userDaoBean);
}
