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
 * @ Date       ：Created in 22:06 2020-02-09
 * @ Description：定义为int型，如果后台返回""或null，则返回0
 * @ Modified By：
 * @Version: ：1.0
 */
public class IntegerDefaultAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

    /**
     * 重写反序列化
     * @param json json
     * @param typeOfT typeOfT
     * @param context context
     * @return int
     * @throws JsonParseException Exception
     */
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        //捕获异常
        try {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) return 0;
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }
        //否则返回json.getAsInt()中的值
        try {
            return json.getAsInt();
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
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

}
