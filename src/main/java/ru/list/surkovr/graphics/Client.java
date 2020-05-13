package ru.list.surkovr.graphics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.list.surkovr.Constants;
import ru.list.surkovr.deserializers.MultipleResultsDeserializer;
import ru.list.surkovr.deserializers.OrderBookMultipleResultsDeserializer;
import ru.list.surkovr.deserializers.SingleResultDeserializer;
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

public class Client {

    public String sendGetRequest(String urlString, String param) {
        try {
            String urlWithParam = param == null || param.equals("") ? urlString : urlString + "?" + param;
            final URL url = new URL(urlWithParam);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(Constants.GET_METHOD);
            connection.setRequestProperty("Accept", Constants.CONTENT_TYPE);
            connection.setRequestProperty("host", Constants.API_HOST);
            connection.setRequestProperty("port", String.valueOf(Constants.API_PORT));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // PUBLIC
    public Result<List<Market>> getAvailableTradingMarkets() {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_MARKETS, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Market.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<Currency>> getAvailableCurrencies() {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_CURRENCIES, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Currency.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<MarketTick> getTicker(String marketString) {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_TICKER, Constants.MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithSingleResult(Result.class, MarketTick.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<MarketSummary>> getMarketSummaries() {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_MARKET_SUMMARIES, "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<MarketSummary>> getGetMarketSummary(String marketString) {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_MARKET_SUMMARY, Constants.MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketSummary.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<OrderBook> getOrderBook(String marketString, String orderBookTypeName) {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_ORDER_BOOK,
                Constants.MARKET_PARAM + marketString + "&" + Constants.TYPE_PARAM + orderBookTypeName);
        Gson gson = setUpGsonOrderBookDeserializer();
        return gson.fromJson(json, Result.class);
    }

    public Result<List<MarketHistoryEntry>> getMarketHistory(String marketString) {
        String json = sendGetRequest(Constants.PUBLIC_API_ROOT_URL + Constants.GET_MARKET_HISTORY, Constants.MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, MarketHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }


    // MARKET
    public Result<UuidEntity> buyLimit(String marketString, double quantity, double rate,
                                       TimeInForce timeInForce) {
        return getUuidEntityResult(marketString, quantity, rate, timeInForce, Constants.BUY_LIMIT);
    }

    public Result<UuidEntity> sellLimit(String marketString, double quantity, double rate,
                                        TimeInForce timeInForce) {
        return getUuidEntityResult(marketString, quantity, rate, timeInForce, Constants.SELL_LIMIT);
    }

    private Result<UuidEntity> getUuidEntityResult(String marketString, double quantity, double rate,
                                                   TimeInForce timeInForce, String limitOrderType) {
        String json = sendGetRequest(Constants.MARKET_API_ROOT_URL + limitOrderType, Constants.APIKEY_PARAM + Constants.API_KEY +
                "&" + Constants.MARKET_PARAM + marketString + "&" + Constants.QUANTITY_PARAM + quantity + "&" + Constants.RATE_PARAM + rate +
                (timeInForce != null ? "&" + "timeInForce=" + timeInForce.name() : ""));
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<UuidEntity> cancelOrder(String orderUuid) {
        String json = sendGetRequest(Constants.MARKET_API_ROOT_URL + Constants.CANCEL, Constants.APIKEY_PARAM + Constants.API_KEY +
                "&" + Constants.UUID_PARAM + orderUuid);
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<Order>> getOpenOrders(String marketString) {
        String json = sendGetRequest(Constants.MARKET_API_ROOT_URL + Constants.GET_OPEN_ORDERS, Constants.APIKEY_PARAM + Constants.API_KEY +
                "&" + Constants.MARKET_PARAM + marketString);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }


    // ACCOUNT
    public Result<List<Balance>> getBalances() {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_BALANCES, Constants.APIKEY_PARAM + Constants.API_KEY);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Balance.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<Balance> getBalance(String currencyName) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_BALANCE, Constants.APIKEY_PARAM + Constants.API_KEY
                + "&" + Constants.CURRENCY_PARAM + currencyName);
        Gson gson = setUpGsonWithSingleResult(Result.class, Balance.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<Address> getDepositAddress(String currencyName) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_DEPOSIT_ADDRESS,
                Constants.APIKEY_PARAM + Constants.API_KEY + "&" + Constants.CURRENCY_PARAM + currencyName);
        Gson gson = setUpGsonWithSingleResult(Result.class, Address.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<UuidEntity> withdrawFunds(String currencyName, double quantity, String receiveAddress,
                                            String paymentid) { // Paymentid - memo, message etc
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.WITHDRAW, Constants.APIKEY_PARAM + Constants.API_KEY +
                "&" + Constants.CURRENCY_PARAM + currencyName + "&" + Constants.QUANTITY_PARAM + quantity +
                "&" + Constants.ADDRESS_PARAM + receiveAddress
                + (paymentid != null && paymentid.equals("") ? "&" + Constants.PAYMENTID_PARAM + paymentid : ""));
        Gson gson = setUpGsonWithSingleResult(Result.class, UuidEntity.class);
        return gson.fromJson(json, Result.class);
    }

    // TODO add signing for all necessary requests https://bittrex.github.io/api/v1-1#authentication
    public Result<List<Order>> getOrder(String uuid) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_ORDER, Constants.UUID_PARAM + uuid);
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<Order>> getOrderHistory(String marketString) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_ORDER_HISTORY,
                marketString != null && marketString.equals("") ? Constants.MARKET_PARAM + marketString : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, Order.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<WithdrawalHistoryEntry>> getWithdrawalHistory(String currencyName) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_WITHDRAWAL_HISTORY,
                currencyName != null && currencyName.equals("") ? Constants.CURRENCY_PARAM + currencyName : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, WithdrawalHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }

    public Result<List<DepositHistoryEntry>> getDepositHistory(String currencyName) {
        String json = sendGetRequest(Constants.ACCOUNT_API_ROOT_URL + Constants.GET_DEPOSIT_HISTORY,
                currencyName != null && currencyName.equals("") ? Constants.CURRENCY_PARAM + currencyName : "");
        Gson gson = setUpGsonWithMultipleResults(Result.class, DepositHistoryEntry.class);
        return gson.fromJson(json, Result.class);
    }

    private String getResponseString(HttpURLConnection conn) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            final StringBuilder stringBuffer = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append('\r');
            }
            reader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> Gson setUpGsonWithSingleResult(Type rootType, Class<T> innerType) {
        return new GsonBuilder()
                .registerTypeAdapter(rootType, new SingleResultDeserializer<>(innerType))
                .create();
    }

    private <T> Gson setUpGsonWithMultipleResults(Type rootType, Class<T> innerType) {
        return new GsonBuilder()
                .registerTypeAdapter(rootType, new MultipleResultsDeserializer<>(innerType))
                .create();
    }

    private Gson setUpGsonOrderBookDeserializer() {
        return new GsonBuilder()
                .registerTypeAdapter(Result.class, new OrderBookMultipleResultsDeserializer())
                .create();
    }
}