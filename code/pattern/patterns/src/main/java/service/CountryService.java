package service;

public interface CountryService {

    /**
     * Returns the country name for a given ISO code
     *
     * @param countryCode (e.g. CH)
     * @return name of the country (e.g. Switzerland) or null if country code
     * does not exists
     */
    String getCountryName(String countryCode);

}
