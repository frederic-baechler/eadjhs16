package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericServiceProxy implements InvocationHandler {

    private static final Logger LOGGER = Logger.getLogger(GenericServiceProxy.class
            .getName());

    private final String dsServerUrl = "http://eadj-simas.rhcloud.com/";

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            sb.delete(0, 3);
            sb.delete(sb.indexOf("Name"), sb.length());

            URL url = new URL(dsServerUrl + sb.toString().toLowerCase() + "/" + args[0]);
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
