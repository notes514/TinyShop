package com.laodai.library.upload;

import com.laodai.library.factory.ApiFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:15 2020-03-03
 * @ Description：文件上传辅助类
 * @ Modified By：
 * @Version: ：1.0
 */
public class UploadHelper {

    /**
     * 上传一张图片
     *
     * @param uploadUrl 上传图片的服务器URL
     * @param filePath  图片路径
     * @return ResponseBody
     */
    public static Observable<ResponseBody> uploadImage(String uploadUrl, String filePath) {
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        return uploadFilesWithParams(uploadUrl, "upload_file", null, filePaths);
    }

    /**
     * 上传多张图片
     *
     * @param uploadUrl 上传图片的服务器URL
     * @param filePaths 图片集合路径
     * @return ResponseBody
     */
    public static Observable<ResponseBody> uploadImages(String uploadUrl, List<String> filePaths) {
        return uploadFilesWithParams(uploadUrl, "upload_file", null, filePaths);
    }

    /**
     * 图片和参数同时上传的请求
     *
     * @param uploadUrl 上传图片的服务器url
     * @param fileName  后台协定的接受图片的name（没特殊要求就可以随便写）
     * @param paramsMap 普通参数
     * @param filePaths 图片集合路径
     * @return ResponseBody
     */
    public static Observable<ResponseBody> uploadFilesWithParams(String uploadUrl, String fileName,
                                                                 Map<String, Object> paramsMap, List<String> filePaths) {
        //创建构建者模式，并设置传输类型
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, (String) paramsMap.get(key));
            }
        }
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            //multipart/form-data(不仅能够上传string类型的参数，还可以上传文件（流的形式，file）)
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //"fileName" + i 后台接受图片流的参数名
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        //默认文件文件上传URL的key
        String DEFAULT_UPLOAD_URL_KEY = "defaultUploadUrlKey";
        //默认文件上传URL的key的value
        String DEFAULT_UPLAOD_BASE_URL = "defaultUploadBaseUrl";
        return ApiFactory.getInstance()
                .createApi(DEFAULT_UPLOAD_URL_KEY, DEFAULT_UPLAOD_BASE_URL, UpLoadFileApi.class)
                .uploadFiles(uploadUrl, parts);
    }
}
