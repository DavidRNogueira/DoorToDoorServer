package com.doortodoor.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/organization")
public class OrganizationResource {

    @GET
    public String test () {
        return "Hello world!";
    }
}
