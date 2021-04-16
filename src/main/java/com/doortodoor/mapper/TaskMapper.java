package com.doortodoor.mapper;

import com.doortodoor.dao.bean.CoordinateDaoBean;
import com.doortodoor.dao.bean.TaskDaoBean;
import com.doortodoor.dto.CoordinateDto;
import com.doortodoor.dto.TaskDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    TaskDto taskDaoBeanToDto (TaskDaoBean taskDaoBean);
    TaskDaoBean taskDtoToDaoBean (TaskDto taskDto);
    List<TaskDto> taskListDaoBeanToDto (List<TaskDaoBean> taskDaoBeanList);
    List<TaskDaoBean> taskListDtoToDaoBean (List<TaskDto> taskDtoList);

    @Mapping(target = "task", ignore = true)
    CoordinateDaoBean coordinateDtoToDaoBean (CoordinateDto coordinateDto);

    @Mapping(target = "task", ignore = true)
    CoordinateDto coordinateDaoBeanToDto (CoordinateDaoBean coordinateDaoBean);
}