package schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderBookEntry {

    @SerializedName("Quantity")
    private double quantity;
    @SerializedName("Rate")
    private double rate;
}
