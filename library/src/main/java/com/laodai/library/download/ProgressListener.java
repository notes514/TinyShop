package com.laodai.library.download;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:51 2020-01-30
 * @ Description：下载进度监听
 * @ Modified By：
 * @Version: ：1.0
 */
public interface ProgressListener {
    /**
     * 下载进度监听
     *
     * @param bytesRead 已经下载文件的大小
     * @param contentLength 文件的大小
     * @param progress 当前进度
     * @param done 是否下载完成
     * @param filePath 文件路劲
     */
    void onResponseProgress(long bytesRead, long contentLength, int progress, boolean done,
                            String filePath);
}
