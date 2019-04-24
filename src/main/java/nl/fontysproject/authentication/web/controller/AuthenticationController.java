package nl.fontysproject.authentication.web.controller;

import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import nl.fontysproject.authentication.domain.model.Credential;
import nl.fontysproject.authentication.domain.model.JwtToken;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.domain.exception.UnauthorizedException;
import nl.fontysproject.authentication.service.AuthenticationService;
import nl.fontysproject.authentication.service.JwtService;

@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController {

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private JwtService jwtService;

    @POST
    @Operation(description = "Authenticate using a username and password.", parameters = {
            @Parameter(schema = @Schema(implementation = Credential.class), required = true, description = "The username and password.")
    }, responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JWT token, valid for this user.",
                    content = @Content(
                            schema = @Schema(implementation = JwtToken.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Not authorized with the given username and password."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Something went wrong during the authentication process, please check the error messages."
            )
    })
    public Response post(@Valid Credential credential) {
        try {
            User user = authenticationService.authenticate(credential.getUsername(), credential.getPassword());
            JwtToken token = jwtService.createToken(user);

            return Response.ok(token).build();

        } catch (UnauthorizedException e) {
            return Response.status(403)
                    .build();
        } catch (NotFoundException e) {
            return Response.status(404)
                    .entity("User not found.")
                    .build();
        } catch (Exception e) {
            return Response.status(400)
                    .entity(e.getMessage())
                    .build();
        }
    }

}