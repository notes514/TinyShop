package com.laodai.library.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:18 2020-02-09
 * @ Description：定义为double类型,如果后台返回""或者null,则返回0.00
 * @ Modified By：
 * @Version: ：1.0
 */
public class DoubleDefaultAdapter implements JsonSerializer<Double>, JsonDeserializer<Double> {

    /**
     * 重写反序列化
     * @param json json
     * @param typeOfT typeOfT
     * @param context context
     * @return Double
     * @throws JsonParseException Exception
     */
    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        //捕获异常
        try {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) return 0.00;
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }
        try {
            return json.getAsDouble();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    /**
     * 序列化
     * @param src src
     * @param typeOfSrc typeOfSrc
     * @param context context
     * @return JsonElement
     */
    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

}