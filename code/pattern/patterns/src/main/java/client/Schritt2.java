package client;

import java.util.logging.Logger;
import service.CountryService;
import service.CountryServiceProxy;

public class Schritt2 {

    private static final Logger LOGGER = Logger.getLogger(Schritt2.class.getName());

    public static void main(String[] args) {
        CountryService service = new CountryServiceProxy();

        LOGGER.info(service.getCountryName("CH"));
    }

}
