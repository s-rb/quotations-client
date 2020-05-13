package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Market {

    @SerializedName("MarketCurrency")
    private String marketCurrency;
    @SerializedName("BaseCurrency")
    private String baseCurrency;
    @SerializedName("MarketCurrencyLong")
    private String marketCurrencyLong;
    @SerializedName("BaseCurrencyLong")
    private String baseCurrencyLong;
    @SerializedName("MinTradeSize")
    private double minTradeSize;
    @SerializedName("MarketName")
    private String marketName;
    @SerializedName("IsActive")
    private boolean isActive;
    @SerializedName("IsRestricted")
    private boolean isRestricted;
    @SerializedName("Created")
    private String created;
    @SerializedName("Notice")
    private String notice;
    @SerializedName("IsSponsored")
    private boolean isSponsored;
    @SerializedName("LogoUrl")
    private String logoUrl;

    @Override
    public String toString() {
        return "Market{" +
                "marketCurrency='" + marketCurrency + '\'' +
                ", \nbaseCurrency='" + baseCurrency + '\'' +
                ", \nmarketCurrencyLong='" + marketCurrencyLong + '\'' +
                ", \nbaseCurrencyLong='" + baseCurrencyLong + '\'' +
                ", \nminTradeSize=" + minTradeSize +
                ", \nmarketName='" + marketName + '\'' +
                ", \nisActive=" + isActive +
                ", \nisRestricted=" + isRestricted +
                ", \ncreated='" + created + '\'' +
                ", \nnotice='" + notice + '\'' +
                ", \nisSponsored=" + isSponsored +
                ", \nlogoUrl='" + logoUrl + '\'' +
                '}';
    }
}
