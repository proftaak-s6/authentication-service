package nl.fontysproject.authentication.domain.validation;

import javax.json.*;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

public class ValidationUtils {

    public static <T> Response validationError(Set<ConstraintViolation<T>> violations) {
        return Response.status(400).type(MediaType.APPLICATION_JSON).entity(createMessage(violations)).build();
    }

    private static <T> String createMessage(Set<ConstraintViolation<T>> violations) {
        JsonArrayBuilder errors = Json.createArrayBuilder();

        for (ConstraintViolation<T> violation : violations) {
            errors.add(Json.createObjectBuilder()
                    .add("parameter", violation.getPropertyPath().toString())
                    .add("error", violation.getMessage())
            );
        }

        return Json.createObjectBuilder().add("errors", errors).build().toString();
    }
}
