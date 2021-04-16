package com.doortodoor.mapper;

import com.doortodoor.dao.bean.CoordinateDaoBean;
import com.doortodoor.dto.CoordinateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoordinateMapper {
    @Mapping(target = "task", ignore = true)
    CoordinateDaoBean coordinateDtoToDaoBean (CoordinateDto coordinateDto);

    @Mapping(target = "task", ignore = true)
    CoordinateDto coordinateDaoBeanToDto (CoordinateDaoBean coordinateDaoBean);
}