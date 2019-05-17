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
import nl.fontysproject.authentication.web.dto.UserDto;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Set;

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

    @POST
    @Operation(description = "Register a new user.", responses = {
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
            return Response.status(400)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}