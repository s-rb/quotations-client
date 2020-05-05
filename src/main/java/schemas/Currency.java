package schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Currency {
    @SerializedName("Currency")
    private String currency;
    @SerializedName("CurrencyLong")
    private String currencyLong;
    @SerializedName("CoinType")
    private String coinType;
    @SerializedName("MinConfirmation")
    private int minConfirmation;
    @SerializedName("TxFee")
    private double txFee;
    @SerializedName("IsActive")
    private boolean isActive;
    @SerializedName("IsRestricted")
    private boolean isRestricted;
    @SerializedName("BaseAddress")
    private String baseAddress;
}
