package nl.fontysproject.authentication.web;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import nl.fontysproject.authentication.web.controller.AuthenticationController;
import nl.fontysproject.authentication.web.controller.UserController;

@ApplicationPath("")
@OpenAPIDefinition(info = @Info(
    title = "Rekeningrijden | Authentication API", 
    version = "0.0.1"),
    servers = @Server(url = "http://localhost:8080")
)
public class AuthenticationApi extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>() {{
            add(AuthenticationController.class);
            add(UserController.class);
        }};
    }
}