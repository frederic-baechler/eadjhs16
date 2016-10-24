package client;

import java.util.logging.Logger;
import service.CountryService;
import service.CurrencyService;
import service.ServiceFactory;

public class DynamicProxyClient {

    private static final Logger LOGGER = Logger.getLogger(DynamicProxyClient.class
            .getName());

    public static void main(String[] args) {
        CountryService countryService = ServiceFactory.getInstance(
                ServiceFactory.REMOTE).getCountryService();

        LOGGER.info(countryService.getCountryName("CH"));

        CurrencyService currencyService = ServiceFactory.getInstance(
                ServiceFactory.REMOTE).getCurrencyService();

        LOGGER.info(currencyService.getCurrencyName("CHF"));
    }

}
