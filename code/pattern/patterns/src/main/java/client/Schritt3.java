package client;

import java.util.logging.Logger;
import service.CountryService;
import service.CountryServiceFactory;

public class Schritt3 {

    private static final Logger LOGGER = Logger.getLogger(Schritt3.class.getName());

    public static void main(String[] args) {
        CountryService service = CountryServiceFactory.getInstance().getService();

        LOGGER.info(service.getCountryName("CH"));
    }

}
