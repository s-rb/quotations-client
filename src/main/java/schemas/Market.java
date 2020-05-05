package schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
}
