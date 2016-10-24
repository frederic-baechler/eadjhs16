package service;

import interceptor.MethodTrace;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Alternative;

/**
 * Proxy to implement remote call to the country service
 */
@Alternative
@MethodTrace
public class CountryServiceProxy implements CountryService {

    private final static Logger LOGGER = Logger.getLogger(CountryServiceProxy.class
            .getName());

    private String dsServerUrl = "http://eadj-simas.rhcloud.com/country/";

    public CountryServiceProxy() {

    }

    public CountryServiceProxy(String location) {
        this.dsServerUrl = location;
    }

    @Override
    public String getCountryName(String countryCode) {
        LOGGER.info("REMOTE");

        try {
            URL url = new URL(dsServerUrl + countryCode);
            return this.convertStreamToString((InputStream) url.getContent());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Problem while communicating with server occured", e);
            return null;
        }
    }

    /**
     * Converts an input stream to a single string
     *
     * @param input stream
     * @return string representation of the input stream
     * @throws IOException
     */
    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

}
