package com.laodai.network;

import android.app.Application;
import android.content.Context;

import com.laodai.network.config.OkHttpConfig;
import com.laodai.network.cookie.CookieJarImpl;
import com.laodai.network.cookie.store.CookieStore;
import com.laodai.network.download.DownloadHelper;
import com.laodai.network.factory.ApiFactory;
import com.laodai.network.manger.RxHttpManager;
import com.laodai.network.upload.UploadHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:32 2020-01-07
 * @ Description：Rx网络请求工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxHttpUtils {
    //单例
    private static RxHttpUtils instance;
    //全局上下文
    private static Application context;

    /**
     * 请求单例(双重校验锁的方式)
     *
     * @return RxHttpUtils
     */
    public static RxHttpUtils getInstance() {
        if (instance == null) {
            synchronized (RxHttpUtils.class) {
                if (instance == null) {
                    instance = new RxHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 使用全局参数创建请求
     *
     * @param tClass Class
     * @param <T> 泛型
     * @return T
     */
    public static <T> T createApi(Class<T> tClass) {
        return ApiFactory.getInstance().createApi(tClass);
    }

    /**
     * 获取全局上下文
     *
     * @return Context
     */
    public static Context getContext() {
        checkInitialize();
        return context;
    }

    /**
     * 检测是否调用初始化方法
     */
    private static void checkInitialize() {
        if (context == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用RxHttpUtils.getInstance().init(this) 初始化！");
        }
    }

    /**
     * 切换baseUrl
     *
     * @param baseUrlKey 域名的key
     * @param baseUrlValue 域名的value
     * @param tClass class
     * @param <T> 泛型
     * @return T
     */
    public static <T> T createApi(String baseUrlKey, String baseUrlValue, Class<T> tClass) {
        return ApiFactory.getInstance().createApi(baseUrlKey, baseUrlValue, tClass);
    }

    /**
     * 文件下载
     *
     * @param fileUrl 地址
     * @return ResponseBody（响应体）
     */
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadHelper.downloadFile(fileUrl);
    }

    /**
     * 上传单张图片
     *
     * @param uploadUrl url地址
     * @param filePath 文件路径
     * @return ResponseBody（响应体）
     */
    public static Observable<ResponseBody> uploadImg(String uploadUrl, String filePath) {
        return UploadHelper.uploadImage(uploadUrl, filePath);
    }

    /**
     * 上传多张图片
     *
     * @param uploadUrl url地址
     * @param filePaths 文件集合路径
     * @return ResponseBody（响应体）
     */
    public static Observable<ResponseBody> uploadImgs(String uploadUrl, List<String> filePaths) {
        return UploadHelper.uploadImages(uploadUrl, filePaths);
    }

    /**
     * 同时上传图片和参数
     *
     * @param uploadUrl url地址
     * @param fileName  图片名称
     * @param paramsMap 普通参数
     * @param filePaths 文件集合路径
     * @return ResponseBody（响应体）
     */
    public static Observable<ResponseBody> uploadImgsWithParams(String uploadUrl, String fileName,
                                                                Map<String, Object> paramsMap, List<String> filePaths) {
        return UploadHelper.uploadFilesWithParams(uploadUrl, fileName, paramsMap, filePaths);
    }

    /**
     * 获取全局CookieJarImpl实例
     *
     * @return CookieJarImpl
     */
    private static CookieJarImpl getCookieJar() {
        return (CookieJarImpl) OkHttpConfig.getInstance().getOkHttpClient().cookieJar();
    }

    /**
     * 获取全局的CookieStore实例
     *
     * @return CookieStore
     */
    private static CookieStore getCookieStore() {
        return getCookieJar().getCookieStore();
    }

    /**
     * 获取某个URL对应的全部Cookie
     *
     * @param url url
     * @return List<Cookie>集合
     */
    public static List<Cookie> getCookieByUrl(String url) {
        CookieStore cs = getCookieStore();
        HttpUrl hu = HttpUrl.parse(url);
        return cs.getCookie(hu);
    }

    /**
     * 获取所有的Cookie
     *
     * @return List<Cookie>集合
     */
    public static List<Cookie> getAllCookie() {
        CookieStore cs = getCookieStore();
        return cs.getAllCookie();
    }

    /**
     * 移除某个url下的全部Cookie
     *
     * @param url url
     */
    public static void removeCookieByUrl(String url) {
        CookieStore cs = getCookieStore();
        HttpUrl hu = HttpUrl.parse(url);
        cs.removeCookie(hu);
    }

    /**
     * 移除全部Cookie
     */
    public static void removeAllCookie() {
        CookieStore cs = getCookieStore();
        cs.removeAllCookie();
    }

    /**
     * 取消某个或某些请求
     *
     * @param tag tag
     */
    public static void cancel(Object... tag) {
        RxHttpManager.getInstance().cancel(tag);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        RxHttpManager.getInstance().cancelAll();
    }

    /**
     * 必须在全局Application先调用，获取contxet上下文，否则缓存无法使用
     *
     * @param application 全局Application
     * @return 当前对象
     */
    public RxHttpUtils init(Application application) {
        context = application;
        return this;
    }

    public ApiFactory config() {
        checkInitialize();
        return ApiFactory.getInstance();
    }
}
