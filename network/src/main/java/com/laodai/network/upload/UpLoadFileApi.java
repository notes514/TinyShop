package com.laodai.network.upload;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:11 2020-03-03
 * @ Description：文件上传网络请求接口
 * @ Modified By：
 * @Version: ：1.0
 */
public interface UpLoadFileApi {

    /**
     * 上传多个文件
     *
     * @param uplaodUrl 地址
     * @param files     文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String uplaodUrl, @Part List<MultipartBody.Part> files);

}
