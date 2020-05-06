
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import deserializers.MultipleResultsDeserializer;
import deserializers.OrderBookMultipleResultsDeserializer;
import deserializers.SingleResultDeserializer;
import enums.OrderBookType;
import schemas.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
    public static final String GET_MARKET_SUMMARIES = "getmarketsummaries";
    public static final String PUBLIC_API_ROOT_URL = HTTPS +  API_HOST + "/" + PUBLIC + "/";
    public static final String GET_MARKET_SUMMARY = "getmarketsummary";
    public static final String GET_ORDER_BOOK = "getorderbook";
    public static final String TYPE_PARAM = "type=";
    public static final String GET_MARKET_HISTORY = "getmarkethistory";

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
        setUpGsonWithMultipleResults(Result.class, Market.class);
        Result<List<Market>> result = gson.fromJson(json, Result.class);
        System.out.println(result);
        System.out.println("MarketName - " + result.getResult().get(0).getMarketName());
        System.out.println("MinTradeSize - " + result.getResult().get(0).getMinTradeSize());

        json = getAvailableCurrencies();
        setUpGsonWithMultipleResults(Result.class, Currency.class);
        Result<List<Currency>> curResult = gson.fromJson(json, Result.class);
        System.out.println(curResult);
        System.out.println("Currency - " + curResult.getResult().get(2).getCurrency());
        System.out.println("TxFee - " + curResult.getResult().get(2).getTxFee());

        json = getTicker("BTC-LTC");
        setUpGsonWithSingleResult(Result.class, MarketTick.class);
        Result<MarketTick> tickRes = gson.fromJson(json, Result.class);
        System.out.println(tickRes);
        System.out.println("Bid - " + tickRes.getResult().getBid());
        System.out.println("Last - " + tickRes.getResult().getLast());

        json = getMarketSummaries();
        setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        Result<List<MarketSummary>> marketSummaries = gson.fromJson(json, Result.class);
        System.out.println(marketSummaries);
        System.out.println("Bid - " + marketSummaries.getResult().get(1).getBid());
        System.out.println("Last - " + marketSummaries.getResult().get(1).getLast());

        json = getGetMarketSummary("BTC-LTC");
        setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        marketSummaries = gson.fromJson(json, Result.class);
        System.out.println(marketSummaries);
        System.out.println("Bid - " + marketSummaries.getResult().get(0).getBid());
        System.out.println("Last - " + marketSummaries.getResult().get(0).getLast());

        json = getOrderBook("BTC-LTC", OrderBookType.both.name());
        setUpGsonOrderBookDeserializer();
        Result<OrderBook> resOrderBook = gson.fromJson(json, Result.class);
        System.out.println(resOrderBook);
        System.out.println("Buy quantity - " + resOrderBook.getResult().getBuy().get(0).getQuantity());
        System.out.println("Sell rate - " + resOrderBook.getResult().getSell().get(0).getRate());

        json = getMarketHistory("BTC-DOGE");
        setUpGsonWithMultipleResults(Result.class, MarketHistoryEntry.class);
        Result<List<MarketHistoryEntry>> marketHistoryEntries = gson.fromJson(json, Result.class);
        System.out.println(marketHistoryEntries);
        System.out.println("OrderType - " + marketHistoryEntries.getResult().get(0).getOrderType());
        System.out.println("Price - " + marketHistoryEntries.getResult().get(0).getPrice());
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
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKETS, "");
    }

    public static String getAvailableCurrencies() throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_CURRENCIES, "");
    }

    public static String getTicker(String marketString) throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_TICKER, MARKET_PARAM + marketString);
    }
    public static String getMarketSummaries() throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_SUMMARIES, "");
    }
    public static String getGetMarketSummary(String marketString) throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_SUMMARY, MARKET_PARAM + marketString);
    }
    public static String getOrderBook(String marketString, String orderBookTypeName) throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_ORDER_BOOK, MARKET_PARAM + marketString
                + "&" + TYPE_PARAM + orderBookTypeName);
    }
    public static String getMarketHistory(String marketString) throws Exception {
        return sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_HISTORY, MARKET_PARAM + marketString);
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

    private static <T> void setUpGsonWithSingleResult(Type rootType, Class<T> innerType) {
        gson = new GsonBuilder()
                .registerTypeAdapter(rootType, new SingleResultDeserializer<>(innerType))
                .create();
    }
    private static <T> void setUpGsonWithMultipleResults(Type rootType, Class<T> innerType) {
        gson = new GsonBuilder()
                .registerTypeAdapter(rootType, new MultipleResultsDeserializer<>(innerType))
                .create();
    }
    private static void setUpGsonOrderBookDeserializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Result.class, new OrderBookMultipleResultsDeserializer())
                .create();
    }
}
