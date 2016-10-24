package service;

import event.LoginEvent;
import java.util.Locale;
import java.util.logging.Logger;
import interceptor.MethodTrace;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Local CountrySerivce implementation
 */
@MethodTrace
public class CountryServiceLocal implements CountryService {

    private static final Logger LOGGER = Logger.getLogger(CountryServiceLocal.class
            .getName());

    @Inject
    private Event<LoginEvent> event;
    
    /*
     * (non-Javadoc)
     * 
     * @see ds.service.CountryService#getCountryName(java.lang.String)
     */
    @Override
    public String getCountryName(String countryCode) {
        LOGGER.info("LOCAL");
        
        event.fire(new LoginEvent());
        
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }

}
