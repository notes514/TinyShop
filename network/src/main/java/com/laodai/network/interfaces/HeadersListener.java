package com.laodai.network.interfaces;

import java.util.Map;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:01 2020-02-23
 * @ Description：请求头接口
 * @ Modified By：
 * @Version: ：1.0
 */
public interface HeadersListener {

    /**
     * 创建请求头
     *
     * @return Map<String, String>
     */
    Map<String, String> buildHeaders();

}
