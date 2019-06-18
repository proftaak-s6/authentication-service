package nl.fontysproject.authentication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import nl.fontysproject.authentication.domain.exception.VerificationException;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.BrpService;
import nl.fontysproject.authentication.service.UserService;
import nl.fontysproject.authentication.web.dto.UserRoleDto;
import nl.fontysproject.authentication.web.dto.UserDto;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static nl.fontysproject.authentication.domain.validation.ValidationUtils.validationError;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserService service;

    @Inject
    private BrpService brp;

    private Validator validator;

    @PostConstruct
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @GET
    @Operation(description = "Get an overview of all users and their roles.", tags = {"Users"})
    public Response getAll() {
        List<User> users = service.getAll();

        if (users == null || users.isEmpty()) {
            return Response.noContent()
                    .build();
        }

        return Response.ok()
                .entity(users.stream().map(UserRoleDto::new).collect(Collectors.toList()))
                .build();
    }

    @POST
    @Operation(description = "Register a new user.", tags = {"Users"}, responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User was successfully registered.",
                    headers = {@Header(name = "location", schema = @Schema(type = "string"))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Something went wrong during the registration, please check the error messages.",
                    content = @Content(schema = @Schema(implementation = Exception.class))
            )
    })
    public Response post(UserDto userDto, @Context UriInfo context) {
        Set<ConstraintViolation<UserDto>> errors = validator.validate(userDto);

        if (!errors.isEmpty()) {
            return validationError(errors);
        }

        try {
            User user = brp.verify(userDto);

            long id = service.add(user);

            URI location = context.getAbsolutePathBuilder()
                    .path(Long.toString(id))
                    .build();

            return Response.created(location).build();

        } catch (VerificationException e) {
            return errorResponse(400, e.getMessage());
        } catch (Exception e) {
            return errorResponse(500, e.getMessage());
        }
    }

    @PUT
    @Path("/{userId}/roles")
    @Operation(description = "Update a users roles", tags = {"Users"})
    public Response update(@PathParam("userId") long id, String[] roles) {
        User user = service.find(id);

        if (user == null) {
            return Response.status(404).entity("Could not find user with id: " + id).build();
        }

        if (roles.length < 1) {
            return Response.status(400).entity("No roles where provided").build();
        }

        user.setRoles(new HashSet<>(Arrays.asList(roles)));
        service.update(user);

        return Response.noContent().build();
    }

    private Response errorResponse(int statusCode, String... messages) {
        JsonArrayBuilder errors = Json.createArrayBuilder();

        for (String message : messages) {
            errors.add(message);
        }

        return Response.status(statusCode).type(MediaType.APPLICATION_JSON_TYPE)
                .entity(Json.createObjectBuilder()
                        .add("errors", errors.build())
                        .build()
                        .toString())
                .build();
    }
}