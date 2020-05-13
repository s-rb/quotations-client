package ru.list.surkovr;

public class Constants {

    public static final String HTTPS = "https://";
    public static final String API_HOST = "api.bittrex.com/api/v1.1";
    public static final int API_PORT = 443;
    public static final String CONTENT_TYPE = "application/json";
    public static final String API_KEY = "";
    public static final String GET_METHOD = "GET";
    public static final String PUBLIC = "public";
    public static final String PUBLIC_API_ROOT_URL = HTTPS + API_HOST + "/" + PUBLIC + "/";
    public static final String MARKET = "market";
    public static final String MARKET_API_ROOT_URL = HTTPS + API_HOST + "/" + MARKET + "/";
    public static final String ACCOUNT = "account";
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
    public static final String TITLE_OF_PROGRAM = "Quotations RESTful client";
    public static final int START_LOCATION = 200;
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 700;
}
