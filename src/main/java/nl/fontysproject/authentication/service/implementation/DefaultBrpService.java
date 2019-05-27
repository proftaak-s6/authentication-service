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
import javax.ws.rs.core.Response;
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
    public static final String FIRSTNAME_KEY = "gegeven_naam";
    public static final String LASTNAME_KEY = "achternaam";

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

    private JsonObject readUserData(WebTarget graphql, String bsn) throws VerificationException, UnsupportedEncodingException {
        Response response = getPersonByBsn(graphql, bsn);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Could not reach BRP.");
        }

        JsonObject result = readJsonObject(response);

        if (result.getJsonArray(ERRORS_KEY) != null) {
            throw new VerificationException("Error while reading query.\n" + result.toString());
        }

        if (result.getJsonObject(DATA_KEY) == null) {
            throw new VerificationException("No records for person with bsn: " + bsn);
        }

        return result.getJsonObject(DATA_KEY).getJsonObject(PERSON_BY_BSN);
    }

    private User constructUser(UserDto dto, JsonObject data) {
        User u = new User();

        u.setBirthday(data.getString(BIRTHDAY_KEY));
        u.setBrpId(data.getInt(ID_KEY));
        u.setBsn(dto.getBsn());
        u.setFirstName(data.getString(FIRSTNAME_KEY));
        u.setLastName(data.getString(LASTNAME_KEY));
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setZipCode(dto.getZipCode());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setUsername(dto.getUsername());
        u.setRoles(dto.getRoles());

        return u;
    }

    private Response getPersonByBsn(WebTarget graphql, String bsn) throws UnsupportedEncodingException {
        String rawQuery = String.format("{ %s(bsn: \"%s\" ) { %s, %s, %s, %s } }", PERSON_BY_BSN, bsn, BIRTHDAY_KEY, ID_KEY, FIRSTNAME_KEY, LASTNAME_KEY);
        String encodedQuery = URLEncoder.encode(rawQuery, "UTF-8");
        WebTarget query = graphql.queryParam("query", encodedQuery);

        return query.request().get();
    }

    private JsonObject readJsonObject(Response response) {
        String jsonString = response.readEntity(String.class);

        return Json.createReader(new StringReader(jsonString)).readObject();
    }

    private Optional<String> verifyDto(UserDto user, JsonObject data) {
        Optional<String> errorMessage = verifyBirthday(user, data);

        if (errorMessage != null && errorMessage.isPresent()) {
            return errorMessage;
        }

        return Optional.empty();
    }

    private Optional<String> verifyBirthday(UserDto user, JsonObject data) {
        if (data.getString(BIRTHDAY_KEY) == null) {
            return Optional.of("Geen geboortedatum gevonden voor deze persoon.");
        }

        LocalDate actualDate = LocalDate.parse(data.getString(BIRTHDAY_KEY), DateTimeFormatter.ofPattern(BRP_DATE_FORMAT));
        LocalDate providedDate = LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern(NL_DATE_FORMAT));

        if (!actualDate.equals(providedDate)) {
            return Optional.of("Geboortedatum komt niet overeen met de gegevens uit het basisregister persoonsgegevens..");
        }

        return Optional.empty();
    }
}
