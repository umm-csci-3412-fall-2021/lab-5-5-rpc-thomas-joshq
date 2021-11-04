package xrate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Provide access to basic currency exchange rate services.
 */
public class ExchangeRateReader {

    private String accessKey;

    /**
     * Construct an exchange rate reader using the given base URL. All requests will
     * then be relative to that URL. If, for example, your source is Xavier Finance,
     * the base URL is http://api.finance.xaviermedia.com/api/ Rates for specific
     * days will be constructed from that URL by appending the year, month, and day;
     * the URL for 25 June 2010, for example, would be
     * http://api.finance.xaviermedia.com/api/2010/06/25.xml
     * 
     * @param baseURL the base URL for requests
     */
    public ExchangeRateReader(String baseURL) {
        /*
         * DON'T DO MUCH HERE! People often try to do a lot here, but the action is
         * actually in the two methods below. All you need to do here is store the
         * provided `baseURL` in a field (which you have to declare) so it will be
         * accessible in the two key functions. (You'll need it there to construct
         * the full URL.)
         */

        // TODO Your code here

        // Reads the Fixer.io API access key from the appropriate
        // environment variable.
        // You don't have to change this call.
        readAccessKey();
    }

    /**
     * This reads the `fixer_io` access key from from the system environment and
     * assigns it to the field `accessKey`.
     * 
     * You don't have to change anything here.
     */
    private void readAccessKey() {
        // Read the desired environment variable.
        accessKey = System.getenv("FIXER_IO_ACCESS_KEY");
        // If that environment variable isn't defined, then
        // `getenv()` returns `null`. We'll throw a (custom)
        // exception if that happens since the program can't
        // really run if we don't have an access key.
        if (accessKey == null) {
            throw new MissingAccessKeyException();
        }
    }

    /**
     * Get the exchange rate for the specified currency against the base currency
     * (the Euro) on the specified date.
     * 
     * @param currencyCode the currency code for the desired currency
     * @param year         the year as a four digit integer
     * @param month        the month as an integer (1=Jan, 12=Dec)
     * @param day          the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException if there are problems reading from the server
     */
    public float getExchangeRate(String currencyCode, int year, int month, int day) throws IOException {
        String urlStart = "http://data.fixer.io/api/";

        String accessKey ="?access_key=";
        String dayString = Integer.toString(day);
        String monthString = Integer.toString(month);
        if(day < 10){
            dayString = "0" + dayString;
        }
        if(month < 10){
            monthString = "0" + monthString;
        }

        String urlString = urlStart + year + "-" + monthString + "-" + dayString + accessKey + System.getenv("FIXER_IO_ACCESS_KEY");

        URL url = new URL(urlString);
        JSONTokener tokener = new JSONTokener(url.openStream());
        JSONObject obj = new JSONObject(tokener);
        JSONObject data = obj.getJSONObject("rates");

        float message = data.getFloat(currencyCode);

        return message;
        

    }

    /**
     * Get the exchange rate of the first specified currency against the second on
     * the specified date.
     * 
     * @param fromCurrency the currency code we're exchanging *from*
     * @param toCurrency   the currency code we're exchanging *to*
     * @param year         the year as a four digit integer
     * @param month        the month as an integer (1=Jan, 12=Dec)
     * @param day          the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException if there are problems reading from the server
     */
    public float getExchangeRate(String fromCurrency, String toCurrency, int year, int month, int day)
            throws IOException {
                String urlStart = "http://data.fixer.io/api/";

                String accessKey ="?access_key=";
                String dayString = Integer.toString(day);
                String monthString = Integer.toString(month);
                if(day < 10){
                    dayString = "0" + dayString;
                }
                if(month < 10){
                    monthString = "0" + monthString;
                }
        
                String urlString = urlStart + year + "-" + monthString + "-" + dayString + accessKey + System.getenv("FIXER_IO_ACCESS_KEY");
        
                URL url = new URL(urlString);
                JSONTokener tokener = new JSONTokener(url.openStream());
                JSONObject obj = new JSONObject(tokener);
                System.out.println(obj.toString());
                JSONObject data = obj.getJSONObject("rates");
        
                float messageFrom = data.getFloat(fromCurrency);
                float messageTo = data.getFloat(toCurrency);

        
                return messageFrom/messageTo;
    }
}