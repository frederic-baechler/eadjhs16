package service;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bootstrap {

    private static final Logger LOGGER = Logger.getLogger(CountryServiceProxy.class
            .getName());

    public static CountryServiceFactory createCountryServiceFactory() {
        String impl = System.getProperty("countryService");
        CountryServiceFactory factory = null;
        try {
            Class<?> implClass = Class.forName(impl);
            factory = (CountryServiceFactory) implClass.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        return factory;
    }
}
