package client;

import java.util.logging.Logger;
import service.Bootstrap;
import service.CountryService;
import service.CountryServiceFactory;

public class ClientAbstractFactory {

    private static final Logger LOGGER = Logger.getLogger(ClientAbstractFactory.class.getName());

    public static void main(String[] args) {

        CountryServiceFactory factory = Bootstrap.createCountryServiceFactory();
        CountryService service = factory.createCountryService();

        LOGGER.info(service.getCountryName("CH"));
    }

}
