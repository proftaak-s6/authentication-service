package nl.fontysproject.authentication.web.controller;

import nl.fontysproject.authentication.web.annotation.Authorize;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestController {

    @GET
    @Authorize(roles = {"admin"})
    public Response get() {
        return Response.ok("Hoi!").build();
    }
}
