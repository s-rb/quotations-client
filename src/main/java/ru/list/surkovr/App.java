package ru.list.surkovr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.list.surkovr.deserializers.MultipleResultsDeserializer;
import ru.list.surkovr.deserializers.OrderBookMultipleResultsDeserializer;
import ru.list.surkovr.deserializers.SingleResultDeserializer;
import ru.list.surkovr.enums.OrderBookType;
import ru.list.surkovr.enums.TimeInForce;
import ru.list.surkovr.schemas.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class App {

    public static final String HTTPS = "https://";
    public static final String API_HOST = "api.bittrex.com/api/v1.1";
    public static final int API_PORT = 443;
    public static final String CONTENT_TYPE = "application/json";
    public static final String API_KEY = "";
    public static final String GET_METHOD = "GET";
    public static final String PUBLIC = "public";
    public static final String MARKET = "market";
    public static final String ACCOUNT = "account";

    public static final String PUBLIC_API_ROOT_URL = HTTPS + API_HOST + "/" + PUBLIC + "/";
    public static final String MARKET_API_ROOT_URL = HTTPS + API_HOST + "/" + MARKET + "/";
    public static final String ACCOUNT_API_ROOT_URL = HTTPS + API_HOST + "/" + ACCOUNT + "/";

    public static final String GET_MARKETS = "getmarkets";
    public static final String GET_CURRENCIES = "getcurrencies";
    public static final String GET_TICKER = "getticker";
    public static final String GET_MARKET_SUMMARIES = "getmarketsummaries";
    public static final String GET_MARKET_SUMMARY = "getmarketsummary";
    public static final String GET_ORDER_BOOK = "getorderbook";
    public static final String SELL_LIMIT = "selllimit";
    public static final String CANCEL = "cancel";
    public static final String GET_OPEN_ORDERS = "getopenorders";
    public static final String GET_MARKET_HISTORY = "getmarkethistory";
    public static final String BUY_LIMIT = "buylimit";
    public static final String GET_BALANCES = "getbalances";
    public static final String GET_BALANCE = "getbalance";
    public static final String GET_DEPOSIT_ADDRESS = "getdepositaddress";
    public static final String GET_ORDER = "getorder";
    public static final String GET_ORDER_HISTORY = "getorderhistory";
    public static final String GET_WITHDRAWAL_HISTORY = "getwithdrawalhistory";
    public static final String GET_DEPOSIT_HISTORY = "getdeposithistory";
    public static final String WITHDRAW = "withdraw";

    public static final String MARKET_PARAM = "market=";
    public static final String TYPE_PARAM = "type=";
    public static final String APIKEY_PARAM = "apikey=";
    public static final String QUANTITY_PARAM = "quantity=";
    public static final String RATE_PARAM = "rate=";
    public static final String UUID_PARAM = "uuid=";
    public static final String CURRENCY_PARAM = "currency=";
    public static final String ADDRESS_PARAM = "address=";
    public static final String PAYMENTID_PARAM = "paymentid=";

    public static void main(String[] args) throws Exception {
//        IO.Options options = new IO.Options();
//        options.forceNew = true;
//
//        final OkHttpClient client = new OkHttpClient();
//        options.webSocketFactory = client;
//        options.callFactory = client;

        // tests
        Result<List<Market>> result = getAvailableTradingMarkets();
        System.out.println(result);
        System.out.println("MarketName - " + result.getResult().get(0).getMarketName());
        System.out.println("MinTradeSize - " + result.getResult().get(0).getMinTradeSize());

        Result<List<Currency>> curResult = getAvailableCurrencies();
        System.out.println(curResult);
        System.out.println("Currency - " + curResult.getResult().get(2).getCurrency());
        System.out.println("TxFee - " + curResult.getResult().get(2).getTxFee());

        Result<MarketTick> tickRes = getTicker("BTC-LTC");
        System.out.println(tickRes);
        System.out.println("Bid - " + tickRes.getResult().getBid());
        System.out.println("Last - " + tickRes.getResult().getLast());

        Result<List<MarketSummary>> marketSummaries = getMarketSummaries();
        System.out.println(marketSummaries);
        System.out.println("Bid - " + marketSummaries.getResult().get(1).getBid());
        System.out.println("Last - " + marketSummaries.getResult().get(1).getLast());

        marketSummaries = getGetMarketSummary("BTC-LTC");
        System.out.println(marketSummaries);
        System.out.println("Bid - " + marketSummaries.getResult().get(0).getBid());
        System.out.println("Last - " + marketSummaries.getResult().get(0).getLast());

        Result<OrderBook> resOrderBook = getOrderBook("BTC-LTC", OrderBookType.both.name());
        System.out.println(resOrderBook);
        System.out.println("Buy quantity - " + resOrderBook.getResult().getBuy().get(0).getQuantity());
        System.out.println("Sell rate - " + resOrderBook.getResult().getSell().get(0).getRate());

        Result<List<MarketHistoryEntry>> marketHistoryEntries = getMarketHistory("BTC-DOGE");
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
        connection.setRequestProperty("path", urlString + "/?" + param);
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

    // PUBLIC
    public static Result<List<Market>> getAvailableTradingMarkets() throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKETS, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Market.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<Currency>> getAvailableCurrencies() throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_CURRENCIES, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Currency.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<MarketTick> getTicker(String marketString) throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_TICKER, MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithSingleResult(Result.class, MarketTick.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<MarketSummary>> getMarketSummaries() throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_SUMMARIES, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<MarketSummary>> getGetMarketSummary(String marketString) throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_SUMMARY, MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<OrderBook> getOrderBook(String marketString, String orderBookTypeName) throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_ORDER_BOOK,
                MARKET_PARAM + marketString + "&" + TYPE_PARAM + orderBookTypeName);
        Gson gson = setUpGsonOrderBookDeserializer();
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<MarketHistoryEntry>> getMarketHistory(String marketString) throws Exception {
        String json = sendGetRequest(PUBLIC_API_ROOT_URL + GET_MARKET_HISTORY, MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }


    // MARKET
    public static Result<UuidEntity> buyLimit(String marketString, double quantity, double rate,
                                              TimeInForce timeInForce) throws Exception {
        return getUuidEntityResult(marketString, quantity, rate, timeInForce, BUY_LIMIT);
    }

    public static Result<UuidEntity> sellLimit(String marketString, double quantity, double rate,
                                               TimeInForce timeInForce) throws Exception {
        return getUuidEntityResult(marketString, quantity, rate, timeInForce, SELL_LIMIT);
    }

    private static Result<UuidEntity> getUuidEntityResult(String marketString, double quantity, double rate,
                                                          TimeInForce timeInForce, String limitOrderType) throws Exception {
        String json = sendGetRequest(MARKET_API_ROOT_URL + limitOrderType, APIKEY_PARAM + API_KEY +
                "&" + MARKET_PARAM + marketString + "&" + QUANTITY_PARAM + quantity + "&" + RATE_PARAM + rate +
                (timeInForce != null ? "&" + "timeInForce=" + timeInForce.name() : ""));
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<UuidEntity> cancelOrder(String orderUuid) throws Exception {
        String json = sendGetRequest(MARKET_API_ROOT_URL + CANCEL, APIKEY_PARAM + API_KEY +
                "&" + UUID_PARAM + orderUuid);
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<Order>> getOpenOrders(String marketString) throws Exception {
        String json = sendGetRequest(MARKET_API_ROOT_URL + GET_OPEN_ORDERS, APIKEY_PARAM + API_KEY +
                "&" + MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }


    // ACCOUNT
    public static Result<List<Balance>> getBalances() throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_BALANCES, APIKEY_PARAM + API_KEY);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Balance.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<Balance> getBalance(String currencyName) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_BALANCE, APIKEY_PARAM + API_KEY
                + "&" + CURRENCY_PARAM + currencyName);
        Gson gson = setUpGsonWithSingleResult(Result.class, Balance.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<Address> getDepositAddress(String currencyName) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_DEPOSIT_ADDRESS,
                APIKEY_PARAM + API_KEY + "&" + CURRENCY_PARAM + currencyName);
        Gson gson = setUpGsonWithSingleResult(Result.class, Address.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<UuidEntity> withdrawFunds(String currencyName, double quantity, String receiveAddress,
                                                   String paymentid) throws Exception { // Paymentid - memo, message etc
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + WITHDRAW, APIKEY_PARAM + API_KEY +
                "&" + CURRENCY_PARAM + currencyName + "&" + QUANTITY_PARAM + quantity +
                "&" + ADDRESS_PARAM + receiveAddress
                + (paymentid != null && paymentid.equals("") ? "&" + PAYMENTID_PARAM + paymentid : ""));
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    // TODO add signing for all necessary requests https://bittrex.github.io/api/v1-1#authentication
    public static Result<List<Order>> getOrder(String uuid) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_ORDER, UUID_PARAM + uuid);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<Order>> getOrderHistory(String marketString) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_ORDER_HISTORY,
                marketString != null && marketString.equals("") ? MARKET_PARAM + marketString : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<WithdrawalHistoryEntry>> getWithdrawalHistory(String currencyName) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_WITHDRAWAL_HISTORY,
                currencyName != null && currencyName.equals("") ? CURRENCY_PARAM + currencyName : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, WithdrawalHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }

    public static Result<List<DepositHistoryEntry>> getDepositHistory(String currencyName) throws Exception {
        String json = sendGetRequest(ACCOUNT_API_ROOT_URL + GET_DEPOSIT_HISTORY,
                currencyName != null && currencyName.equals("") ? CURRENCY_PARAM + currencyName : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, DepositHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }

    private static String getResponseString(HttpURLConnection conn) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
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

    private static <T> Gson setUpGsonWithSingleResult(Type rootType, Class<T> innerType) {
        return new GsonBuilder()
                .registerTypeAdapter(rootType, new SingleResultDeserializer<>(innerType))
                .create();
    }

    private static <T> Gson setUpGsonWithMultipleResults(Type rootType, Class<T> innerType) {
        return new GsonBuilder()
                .registerTypeAdapter(rootType, new MultipleResultsDeserializer<>(innerType))
                .create();
    }

    private static Gson setUpGsonOrderBookDeserializer() {
        return new GsonBuilder()
                .registerTypeAdapter(Result.class, new OrderBookMultipleResultsDeserializer())
                .create();
    }
}