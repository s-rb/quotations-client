package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarketTick {

    @SerializedName("Bid")
    private double bid;
    @SerializedName("Ask")
    private double ask;
    @SerializedName("Last")
    private double last;
}
