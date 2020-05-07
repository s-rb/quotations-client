package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarketSummary {

    @SerializedName("MarketName")
    private String marketName;
    @SerializedName("High")
    private double high;
    @SerializedName("Low")
    private double low;
    @SerializedName("Volume")
    private double volume;
    @SerializedName("Last")
    private double last;
    @SerializedName("BaseVolume")
    private double baseVolume;
    @SerializedName("TimeStamp")
    private String timeStamp;
    @SerializedName("Bid")
    private double bid;
    @SerializedName("Ask")
    private double ask;
    @SerializedName("OpenBuyOrders")
    private int openBuyOrders;
    @SerializedName("OpenSellOrders")
    private int openSellOrders;
    @SerializedName("PrevDay")
    private double prevDay;
    @SerializedName("Created")
    private String created;
    @SerializedName("DisplayMarketName")
    private String displayMarketName;
}
