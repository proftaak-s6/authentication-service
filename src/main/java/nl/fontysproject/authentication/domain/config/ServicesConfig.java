package nl.fontysproject.authentication.domain.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;

@ConfigBundle("appConfig.services")
public class ServicesConfig {

    private String brp;

    public String getBrp() {
        return brp;
    }

    public void setBrp(String brp) {
        this.brp = brp;
    }
}
