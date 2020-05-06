package deserializers;

import com.google.gson.*;
import schemas.OrderBook;
import schemas.OrderBookEntry;
import schemas.Result;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class OrderBookMultipleResultsDeserializer implements JsonDeserializer<Result<OrderBook>> {

    @Override
    public Result<OrderBook> deserialize(JsonElement json, Type type,
                                         JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Result<OrderBook> result = new Result<>();
        result.setSuccess(jsonObject.get("success").getAsBoolean());
        result.setMessage(jsonObject.get("message").getAsString());
        JsonObject element = jsonObject.get("result").getAsJsonObject();

        JsonArray buysArray = element.getAsJsonObject().get("buy").getAsJsonArray();
        JsonArray sellsArray = element.getAsJsonObject().get("sell").getAsJsonArray();

        List<OrderBookEntry> buys = new LinkedList<>();
        List<OrderBookEntry> sells = new LinkedList<>();
        for (JsonElement e : buysArray) buys.add(context.deserialize(e, OrderBookEntry.class));
        for (JsonElement e : sellsArray) sells.add(context.deserialize(e, OrderBookEntry.class));
        OrderBook currentOrderBook = context.deserialize(element, OrderBook.class);
        currentOrderBook.setBuy(buys);
        currentOrderBook.setSell(sells);

        result.setResult(currentOrderBook);
        return result;
    }
}
