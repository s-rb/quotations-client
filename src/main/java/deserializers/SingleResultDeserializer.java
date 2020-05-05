package deserializers;

import com.google.gson.*;
import schemas.Result;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class SingleResultDeserializer<T> implements JsonDeserializer<Result<T>> {

    private Class<T> tClass;

    public SingleResultDeserializer(Class<T> type) {
        this.tClass = type;
    }

    @Override
    public Result<T> deserialize(JsonElement json, Type type,
                                            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Result<T> result = new Result<>();
        result.setSuccess(jsonObject.get("success").getAsBoolean());
        result.setMessage(jsonObject.get("message").getAsString());
        result.setResult(context.deserialize(jsonObject.get("result"), tClass));
        return result;
    }
}
