package nl.fontysproject.authentication.service.implementation;

import nl.fontysproject.authentication.domain.config.AppConfig;
import nl.fontysproject.authentication.domain.exception.VerificationException;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.BrpService;
import nl.fontysproject.authentication.web.dto.UserDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class DefaultBrpService implements BrpService {
    private static final String BIRTHDAY_KEY = "geboortedatum";
    private static final String BRP_DATE_FORMAT = "M/dd/yyyy";
    private static final String NL_DATE_FORMAT = "dd/MM/yyyy";
    private static final String ERRORS_KEY = "errors";
    private static final String DATA_KEY = "data";
    private static final String ID_KEY = "id";
    private static final String PERSON_BY_BSN = "personByBsn";

    @Inject
    private AppConfig config;

    private WebTarget brpService;

    @PostConstruct
    public void init() {
        brpService = ClientBuilder.newClient().target(config.services().getBrp());
    }

    @Override
    public User verify(UserDto user) throws VerificationException {
        try {
            JsonObject data = readUserData(brpService.path("/graphql"), user.getBsn());
            Optional<String> errorMessage = verifyDto(user, data);

            if (errorMessage.isPresent()) {
                throw new VerificationException(errorMessage.get());
            }

            return constructUser(user, data);
        } catch (Exception e) {
            throw new VerificationException(e.getMessage());
        }
    }

    private User constructUser(UserDto dto, JsonObject data) {
        User u = new User();
        u.setBirthday(data.getString(BIRTHDAY_KEY));
        u.setBrpId(data.getInt(ID_KEY));
        u.setBsn(dto.getBsn());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setUsername(dto.getUsername());
        u.setRoles(dto.getRoles());
        return u;
    }

    private Optional<String> verifyDto(UserDto user, JsonObject data) {
        if (data.getString(BIRTHDAY_KEY) == null) {
            return Optional.of("No date of birth for this person.");
        }

        LocalDate actualDate = LocalDate.parse(data.getString(BIRTHDAY_KEY), DateTimeFormatter.ofPattern(BRP_DATE_FORMAT));
        LocalDate providedDate = LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern(NL_DATE_FORMAT));

        if (!actualDate.equals(providedDate)) {
            return Optional.of("Invalid birthday");
        }

        return Optional.empty();
    }

    private JsonObject readUserData(WebTarget graphql, String bsn) throws VerificationException, UnsupportedEncodingException {
        String rawQuery = String.format("{ %s(bsn: \"%s\" ) { %s, %s } }", PERSON_BY_BSN,  bsn, BIRTHDAY_KEY, ID_KEY);
        String encodedQuery = URLEncoder.encode(rawQuery, "UTF-8");
        WebTarget query = graphql.queryParam("query", encodedQuery);

        String response = query.request().get().readEntity(String.class);

        JsonObject result = Json.createReader(new StringReader(response)).readObject();

        if (result.getJsonArray(ERRORS_KEY) != null) {
            throw new VerificationException("Error while reading query.\n" + result.toString());
        }

        if (result.getJsonObject(DATA_KEY) == null) {
            throw new VerificationException("No records for person with bsn: " + bsn);
        }

        return result.getJsonObject(DATA_KEY).getJsonObject(PERSON_BY_BSN);
    }
}
