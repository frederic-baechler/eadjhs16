package service;

public interface CurrencyService {

    /**
     * Returns the currency name for a code
     *
     * @param currencyCode (e.g. CHF)
     * @return name of the currency (e.g. Schweizer Franken) or null if currency
     * code does not exists
     */
    String getCurrencyName(String currencyCode);

}
