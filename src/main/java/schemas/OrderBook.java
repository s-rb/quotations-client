package schemas;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderBook {

    private List<OrderBookEntry> buy;
    private List<OrderBookEntry> sell;
}
