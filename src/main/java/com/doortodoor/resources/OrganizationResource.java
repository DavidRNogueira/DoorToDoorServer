package com.doortodoor.resources;

import com.doortodoor.dto.TaskDto;
import com.doortodoor.services.TaskService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/organization")
public class OrganizationResource {
    TaskService taskService;

    @Inject
    public OrganizationResource (final TaskService taskService){
        this.taskService = taskService;
    }

    @GET
    public String test () {
        return "Hello world!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{organizationId}/tasks")
    public List<TaskDto> getAllTasks (@PathParam UUID organizationId) {
        return taskService.getAllTasks(organizationId);
    }
}
