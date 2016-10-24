package client;

import java.util.Locale;
import java.util.logging.Logger;

import service.CountryService;
import service.CountryServiceLocal;

public class Schritt1 {

    private static final Logger LOGGER = Logger.getLogger(Schritt1.class.getName());

    public static void main(String[] args) {
        Locale.setDefault(Locale.FRANCE);

        CountryService service = new CountryServiceLocal();

        LOGGER.info(service.getCountryName("CH"));
    }

}
