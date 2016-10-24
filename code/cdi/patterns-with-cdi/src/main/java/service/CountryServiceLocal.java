package service;

import java.util.Locale;
import java.util.logging.Logger;
import interceptor.MethodTrace;

/**
 * Local CountrySerivce implementation
 */
@MethodTrace
public class CountryServiceLocal implements CountryService {

    private static final Logger LOGGER = Logger.getLogger(CountryServiceLocal.class
            .getName());

    /*
     * (non-Javadoc)
     * 
     * @see ds.service.CountryService#getCountryName(java.lang.String)
     */
    @Override
    public String getCountryName(String countryCode) {
        LOGGER.info("LOCAL");
        
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }

}
