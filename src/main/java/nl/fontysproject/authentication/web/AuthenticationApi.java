package nl.fontysproject.authentication.web;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD")
@ApplicationPath("")
@OpenAPIDefinition(info = @Info(
        title = "Rekeningrijden | Authentication API",
        version = "0.0.1"),
        servers = {
                @Server(url = "http://localhost:8080"),
                @Server(url = "http://auth.fontys-project.nl")
        }
)
@SecuritySchemes(
        @SecurityScheme(name = "jwt",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
)
public class AuthenticationApi extends Application {

}