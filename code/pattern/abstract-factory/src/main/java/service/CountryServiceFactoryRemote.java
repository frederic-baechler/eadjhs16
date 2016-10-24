package service;

public class CountryServiceFactoryRemote implements CountryServiceFactory {

    @Override
    public CountryService createCountryService() {
        return new CountryServiceProxy();
    }

}
