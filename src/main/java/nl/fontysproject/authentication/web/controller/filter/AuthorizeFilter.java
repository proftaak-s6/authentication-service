package nl.fontysproject.authentication.web.controller.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.fontysproject.authentication.web.annotation.Authorize;

import javax.annotation.Priority;
import javax.json.Json;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthorizeFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret"))
            .withIssuer("authentication.rekeningrijden.fontys-project.nl")
            .build();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        Authorize authorize = method.getAnnotation(Authorize.class);

        if (authorize.roles().length == 0) {
            unauthorized(requestContext, "No roles were specified for this route.");
            return;
        }

        try {
            String authHeader = requestContext.getHeaders().getFirst("Authorization");

            if (authHeader == null) {
                unauthorized(requestContext, "No auth header.");
                return;
            }

            String[] token = authHeader.split("Bearer ");

            if (token.length != 2) {
                unauthorized(requestContext, "Invalid token.");
                return;
            }

            DecodedJWT jwt = verifier.verify(token[1]);

            if (Collections.disjoint(Arrays.asList(authorize.roles()), getRoles(jwt))) {
                unauthorized(requestContext, "Wrong role.");
            }

        } catch (JWTVerificationException e) {
            unauthorized(requestContext, "Invalid or unauthorized JWT.");
        } catch (IndexOutOfBoundsException e) {
            unauthorized(requestContext, "Invalid JWT");
        } catch (Exception e) {
            unauthorized(requestContext, e.getMessage());
        }
    }

    private void unauthorized(ContainerRequestContext context, String message) {
        context.abortWith(Response.status(401)
                .type(MediaType.TEXT_PLAIN)
                .entity(message)
                .build());
    }

    private List<String> getRoles(DecodedJWT jwt) {
        String payload = new String(Base64.getDecoder().decode(jwt.getPayload()));

        return Json.createReader(new StringReader(payload))
                .readObject()
                .getJsonArray("roles")
                .stream()
                .map(j -> j.toString().replace("\"", ""))
                .collect(Collectors.toList());
    }
}
