package client;

import event.LoginEvent;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import service.CountryService;

public class Client {

    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    
    @Inject
    private CountryService service;

    public void main(@Observes ContainerInitialized event) {
        LOGGER.info(service.getCountryName("CH"));
    }
    
    public void observer(@Observes LoginEvent loginEvent) {
        LOGGER.info("LoginEvent caught");
    }
}
