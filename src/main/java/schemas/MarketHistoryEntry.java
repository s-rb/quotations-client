package schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarketHistoryEntry {
    @SerializedName("Id")
    private int id;
    @SerializedName("TimeStamp")
    private String timeStamp;
    @SerializedName("Quantity")
    private double quantity;
    @SerializedName("Price")
    private double price;
    @SerializedName("Total")
    private double total;
    @SerializedName("FillType")
    private String fillType;
    @SerializedName("OrderType")
    private String orderType;
}
