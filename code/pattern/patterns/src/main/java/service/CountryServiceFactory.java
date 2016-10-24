package service;

import java.util.logging.Logger;

public class CountryServiceFactory {

    private static final String COUNTRY_SERVICE_LOCATION = "CountryService.location";

    private static final Logger LOGGER = Logger.getLogger(CountryServiceFactory.class
            .getName());

    /**
     * Singleton instance
     */
    private static final CountryServiceFactory factory = new CountryServiceFactory();

    /**
     * Private default contructor to avoid instantiation
     */
    private CountryServiceFactory() {
    }

    /**
     * Singleton access method
     *
     * @return singleton instance
     */
    public static CountryServiceFactory getInstance() {
        return factory;
    }

    /**
     * Returns the service based on the system property CountryService.location
     *
     * @return the concrete service implementation
     */
    public CountryService getService() {
        String location = System.getenv(COUNTRY_SERVICE_LOCATION);
        if (location != null) {
            LOGGER.info("remote");
            return new CountryServiceProxy(location);
        } else {
            LOGGER.info("local");
            return new CountryServiceLocal();
        }
    }
}
