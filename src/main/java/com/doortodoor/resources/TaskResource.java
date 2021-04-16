package com.doortodoor.resources;

import com.doortodoor.dto.TaskDto;
import com.doortodoor.services.TaskService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/task")
public class TaskResource {
    TaskService taskService;

    @Inject
    public TaskResource (final TaskService taskService) {
        this.taskService = taskService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public UUID createTask (TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{taskId}")
    public TaskDto getTask (@PathParam UUID taskId) {
        return taskService.getTask(taskId);
    }
}
