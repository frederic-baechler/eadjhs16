package client;

import java.util.ServiceLoader;
import java.util.logging.Logger;
import service.CountryService;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class
            .getName());

    public static void main(String[] args) {

        ServiceLoader<CountryService> loader = ServiceLoader.load(CountryService.class);

        for (CountryService service : loader) {
            LOGGER.info(service.getCountryName("CH"));
        }
    }

}
