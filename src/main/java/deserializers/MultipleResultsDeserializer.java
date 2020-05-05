package deserializers;

import com.google.gson.*;
import schemas.Result;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class MultipleResultsDeserializer<T> implements JsonDeserializer<Result<List<T>>> {

    private Class<T> tClass;

    public MultipleResultsDeserializer(Class<T> type) {
        this.tClass = type;
    }

    @Override
    public Result<List<T>> deserialize(JsonElement json, Type type,
                                            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Result<List<T>> result = new Result<>();
        result.setSuccess(jsonObject.get("success").getAsBoolean());
        result.setMessage(jsonObject.get("message").getAsString());
        JsonArray array = jsonObject.getAsJsonArray("result");
        List<T> tempList = new LinkedList<>();
        for (JsonElement element : array) {
            tempList.add(context.deserialize(element, tClass));
        }
        result.setResult(tempList);
        return result;
    }
}
