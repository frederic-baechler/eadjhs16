package service;

import java.util.Locale;

/**
 * Local CountrySerivce implementation
 *
 * @author AMARTS
 *
 */
public class CountryServiceLocal implements CountryService {

    /*
     * (non-Javadoc)
     * 
     * @see ds.service.CountryService#getCountryName(java.lang.String)
     */
    @Override
    public String getCountryName(String countryCode) {
        Locale locale = new Locale("", countryCode);
        return locale.getDisplayCountry();
    }

}
