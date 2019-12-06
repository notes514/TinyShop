package com.laodai.tinyshop.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface AppServer {

    /**
     * GET 请求：用户登录
     * @param url
     * @param map
     * @return
     */
    @GET
    Observable<ResponseBody> sendGet(@Url String url, @QueryMap Map<String, Object> map);

}
