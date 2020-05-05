
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import deserializers.MultipleResultsDeserializer;
import deserializers.SingleResultDeserializer;
import schemas.Currency;
import schemas.Market;
import schemas.MarketTick;
import schemas.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class App {

    public static final String API_HOST = "api.bittrex.com/api/v1.1";
    public static final int API_PORT = 443;
    public static final String TRADING_API_URL = "https://" + API_HOST + ":" + API_PORT;
    public static final String PUBLIC_API_URL = "https://" + API_HOST + "/public:" + API_PORT;
    public static final String HTTPS = "https://";
    public static final String PUBLIC = "public";
    public static final String GET_MARKETS = "getmarkets";
    public static final String CONTENT_TYPE = "application/json";
    public static final String API_KEY = "";
    public static final String GET_METHOD = "GET";
    public static final String GET_CURRENCIES = "getcurrencies";
    public static final String GET_TICKER = "getticker";
    public static final String MARKET_PARAM = "market=";

    static Gson gson;

    public static void main(String[] args) throws Exception {
//        IO.Options options = new IO.Options();
//        options.forceNew = true;
//
//        final OkHttpClient client = new OkHttpClient();
//        options.webSocketFactory = client;
//        options.callFactory = client;

        // tests

        String json = getAvailableTradingMarkets();
        setupGsonGetMarkets();
        Result<List<Market>> result = gson.fromJson(json, Result.class);
        System.out.println(result);
        System.out.println("MarketName - " + result.getResult().get(0).getMarketName());
        System.out.println("MinTradeSize - " + result.getResult().get(0).getMinTradeSize());

        json = getAvailableCurrencies();
        setupGsonGetCurrencies();
        Result<List<Currency>> curResult = gson.fromJson(json, Result.class);
        System.out.println(result);
        System.out.println("Currency - " + curResult.getResult().get(2).getCurrency());
        System.out.println("TxFee - " + curResult.getResult().get(2).getTxFee());

        json = getTicker("BTC-LTC");
        setUpGsonGetMarketTick();
        Result<MarketTick> tickRes = gson.fromJson(json, Result.class);
        System.out.println(result);
        System.out.println("Bid - " + tickRes.getResult().getBid());
        System.out.println("Last - " + tickRes.getResult().getLast());
    }

    public static String sendGetRequest(String urlString, String param) throws Exception {
        String urlWithParam = param == null || param.equals("") ? urlString : urlString + "?" + param;
        final URL url = new URL(urlWithParam);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET_METHOD);
        connection.setRequestProperty("Accept", CONTENT_TYPE);
        connection.setRequestProperty("host", API_HOST);
        connection.setRequestProperty("port", String.valueOf(API_PORT));
        connection.setRequestProperty("path", urlString +"/?"+ param);
        connection.setRequestProperty("User-Agent", "request");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded'");

        final String response = getResponseString(connection);
        final int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request: " + urlWithParam);
        System.out.println("Response Code : " + responseCode);

        if (responseCode == 200) {
            return response;
        } else {
            throw new Exception(response);
        }
    }

    public static String getAvailableTradingMarkets() throws Exception {
        return sendGetRequest(HTTPS +  API_HOST + "/" + PUBLIC + "/" + GET_MARKETS, "");
    }

    public static String getAvailableCurrencies() throws Exception {
        return sendGetRequest(HTTPS +  API_HOST + "/" + PUBLIC + "/" + GET_CURRENCIES, "");
    }

    public static String getTicker(String marketString) throws Exception {
        return sendGetRequest(HTTPS +  API_HOST + "/" + PUBLIC + "/" + GET_TICKER, MARKET_PARAM + marketString);
    }

    private static String getResponseString(HttpURLConnection conn) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)))
        {
            final StringBuilder stringBuffer = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append('\r');
            }
            reader.close();
            return stringBuffer.toString();
        }
    }

    private static void setupGsonGetMarkets() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Result.class, new MultipleResultsDeserializer<>(Market.class))
                .create();
    }
    private static void setupGsonGetCurrencies() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Result.class, new MultipleResultsDeserializer<>(Currency.class))
                .create();
    }
    private static void setUpGsonGetMarketTick() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Result.class, new SingleResultDeserializer<>(MarketTick.class))
                .create();
    }
}
