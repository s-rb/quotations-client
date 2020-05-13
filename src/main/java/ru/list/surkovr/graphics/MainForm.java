package ru.list.surkovr.graphics;

import ru.list.surkovr.enums.OrderBookType;
import ru.list.surkovr.schemas.*;

import javax.swing.*;
import java.util.List;

public class MainForm {
    public static final String USD_BTC = "USD-BTC";
    public static final String BTC_ETH = "BTC-ETH";
    public static final String BTC_XRP = "BTC-XRP";
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private JButton usdBtcButton;
    private JButton ethBtcButton;
    private JButton xrpBtcButton;
    private JLabel currentMarketLabel;
    private JTextArea resultTextArea;
    private JPanel buttonsPanel;
    private JButton getAvailableMarketsButton;
    private JButton getAvailableCurrenciesButton;
    private JButton tickerButton;
    private JButton marketSummariesButton;
    private JButton marketSummaryButton;
    private JButton orderbookButton;
    private JButton marketHistoryButton;
    private Client client;
    private String currentMarket;

    public MainForm(Client client) {
        resultTextArea.setLineWrap(true);
        this.client = client;
        usdBtcButton.addActionListener(e -> {
            currentMarket = USD_BTC;
            currentMarketLabel.setText(USD_BTC);
        });
        ethBtcButton.addActionListener(e -> {
                    currentMarket = BTC_ETH;
                    currentMarketLabel.setText(BTC_ETH);
                });
        xrpBtcButton.addActionListener(e -> {
                    currentMarket = BTC_XRP;
                    currentMarketLabel.setText(BTC_XRP);
                });
        getAvailableMarketsButton.addActionListener(e -> {
            Result<List<Market>> result = client.getAvailableTradingMarkets();
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        getAvailableCurrenciesButton.addActionListener(e -> {
            Result<List<Currency>> result = client.getAvailableCurrencies();
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        tickerButton.addActionListener(e -> {
            Result<MarketTick> result = client.getTicker(currentMarket);
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        marketSummariesButton.addActionListener(e -> {
            Result<List<MarketSummary>> result = client.getMarketSummaries();
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        marketSummaryButton.addActionListener(e -> {
            Result<List<MarketSummary>> result = client.getGetMarketSummary(currentMarket);
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        orderbookButton.addActionListener(e -> {
            Result<OrderBook> result = client.getOrderBook(currentMarket, OrderBookType.both.name());
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
        marketHistoryButton.addActionListener(e -> {
            Result<List<MarketHistoryEntry>> result = client.getMarketHistory(currentMarket);
            resultTextArea.setText("");
            resultTextArea.append(result.toString());
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
