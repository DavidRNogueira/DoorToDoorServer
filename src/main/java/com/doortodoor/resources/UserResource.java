package com.doortodoor.resources;

import com.doortodoor.dto.UserDto;
import com.doortodoor.services.UserService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/user")
public class UserResource {

    UserService userService;

    @Inject
    public UserResource (final UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UUID create (@Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public UserDto getUserById (@PathParam final UUID userId){
        return userService.getUserById(userId);
    }

}
