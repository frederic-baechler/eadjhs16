package service;


public class CountryServiceFactoryLocal implements CountryServiceFactory {

    @Override
    public CountryService createCountryService() {
        return new CountryServiceLocal();
    }

}
