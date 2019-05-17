package nl.fontysproject.authentication.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import nl.fontysproject.authentication.web.controller.AuthenticationController;
import nl.fontysproject.authentication.web.controller.TestController;
import nl.fontysproject.authentication.web.controller.UserController;
import nl.fontysproject.authentication.web.controller.filter.AuthorizeFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
@OpenAPIDefinition(info = @Info(
        title = "Rekeningrijden | Authentication API",
        version = "0.0.1"),
        servers = @Server(url = "http://localhost:8080")
)
@SecuritySchemes(
        @SecurityScheme(name = "jwt",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
)
public class AuthenticationApi extends Application {

}