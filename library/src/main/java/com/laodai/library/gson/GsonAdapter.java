package com.laodai.library.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:02 2020-02-09
 * @ Description：gosn适配
 * @ Modified By：
 * @Version: ：1.0
 */
public class GsonAdapter {

    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                .registerTypeAdapter(long.class, new LongDefaultAdapter())
                .create();
    }

}
