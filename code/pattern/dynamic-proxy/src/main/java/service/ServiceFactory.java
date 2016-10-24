package service;

import java.lang.reflect.Proxy;
import java.util.logging.Logger;


/**
 * Factory implemented as Singleton
s */
public class ServiceFactory {

    private static final Logger LOGGER = Logger.getLogger(ServiceFactory.class
            .getName());

    public static final String REMOTE = "remote";

    public static final String LOCAL = "local";

    private static String location;

    /**
     * Singleton instance
     */
    private static ServiceFactory factory = new ServiceFactory();

    /**
     * Private default contructor to avoid instantiation
     */
    private ServiceFactory() {
    }

    /**
     * Singleton access method
     *
     * @return singleton instance
     */
    public static ServiceFactory getInstance(String location) {
        ServiceFactory.location = location;
        return factory;
    }

    public CountryService getCountryService() {
        if (location != null && location.equals(REMOTE)) {
            LOGGER.info(REMOTE);
            // Create Dynamic Proxy
            return (CountryService) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{CountryService.class},
                    new GenericServiceProxy());
        } else {
            LOGGER.info(LOCAL);
            return new CountryServiceLocal();
        }
    }

    public CurrencyService getCurrencyService() {
        if (location != null && location.equals(REMOTE)) {
            LOGGER.info(REMOTE);
            // Create Dynamic Proxy
            return (CurrencyService) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{CurrencyService.class},
                    new GenericServiceProxy());
        } else {
            // No local currency service available
            return null;
        }
    }
}
