package com.laodai.library.http;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:34 2020-02-09
 * @ Description：SSL证书工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class SSLUtils {

    /**
     * 为了解决客户端不信任服务器数字证书的问题，网络上大部分的解决方案都是让客户端不对证书做任何检查，
     * 这是一种有很大安全漏洞的办法
     */
    public static X509TrustManager UnSafeTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };
    /**
     * 此类事用于主机名验证的机接口。
     * 在握手期间，如果URL的主机名和服务器的主机名标识不匹配，则验证机制可以回调此接口的实现程序来确定是否应该
     * 允许此连接。策略可以是基于证书的或依赖于其他验证方案。当验证URL主机名使用的默认规则失败时使用这些回调。
     * 如果主机名是可接受的，则返回true
     */
    public static HostnameVerifier UnSafeHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public static SSLParams getSslSocketFactory() {
        return getSslSocketFactoryBase(null, null, null);
    }

    /**
     * HTTPS单向认证
     * 可以额外配置新人服务端的证书策略，否则默认是按CA证书去验证的，若不是CA可信任的证书，则无法通过
     *
     * @param trustManager trustManager
     * @return SSLParams
     */
    public static SSLParams getSslSocketFactory(X509TrustManager trustManager) {
        return getSslSocketFactoryBase(trustManager, null, null);
    }

    /**
     * HTTPS单向认证
     * 用含有服务器公钥的证书校验服务端证书
     *
     * @param certificates certificates
     * @return SSLParams
     */
    public static SSLParams getSslSocketFactory(InputStream... certificates) {
        return getSslSocketFactoryBase(null, null, null, certificates);
    }

    /**
     * HTTPS双向认证
     * bksFile和password -> 客户端使用bks证书校验服务端证书
     * cretificates -> 用含有服务端公钥的证书校验服务端证书
     *
     * @param bksFile bksFile文件
     * @param password 密码
     * @param certificates 证书
     * @return SSLParams
     */
    public static SSLParams getSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
        return getSslSocketFactoryBase(null, bksFile, password, certificates);
    }

    /**
     * HTTPS双向认证
     * bksFile和password -> 客户端使用bks证书校验服务端证书
     * X509TrustManager -> 如果需要自己校验，那么可以自己实现相关校验，如果不需要自己校验，那么传null即可。
     *
     * @param bksFile bksFile文件
     * @param password 密码
     * @param trustManager trustManager
     * @return
     */
    public static SSLParams getSslSocketFactory(InputStream bksFile, String password, X509TrustManager trustManager) {
        return getSslSocketFactoryBase(trustManager, bksFile, password);
    }

//    private static SSLParams getSslSocketFactoryBase(X509TrustManager trustManager,
//    InputStream bksFile, String password, InputStream... certificates) {
//        SSLParams sslParams = new SSLParams();
//        try {

//            // 创建TLS类型的SSLContext对象,使用我们的信任管理器
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
//            // 第一个参数是授权的密钥管理器，用来授权验证，比如授权自签名的证书验证。第二个是被授权的证书管理器，用来验证服务器端的证书
//            sslContext.init(keyManagers, new TrustManager[]{manager}, null);
//            // 通过sslContext获取SSLSocketFactory对象
//            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
//            sslParams.trustManager = manager;
//            return sslParams;
//        } catch (NoSuchAlgorithmException e) {
//            throw new AssertionError(e);
//        } catch (KeyManagementException e) {
//            throw new AssertionError(e);
//        }
//    }
//

    private static SSLParams getSslSocketFactoryBase(X509TrustManager trustManager, InputStream bksFile,
                                                     String password, InputStream... certificates) {
        SSLParams sslParams = new SSLParams();
        try {
            KeyManager[] keyManagers = prepareKeyManger(bksFile, password);
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            X509TrustManager manager;
            if (trustManager != null) {
                //优先使用用户自定义的TrustManager
                manager = trustManager;
            } else if (trustManagers != null) {
                //然后使用默认的TrustManager
                manager = chooseTrustManager(trustManagers);
            } else {
                //否则使用不安全的TrustManager
                manager = UnSafeTrustManager;
            }
            //创建TLS类型的SSLContext对象,使用我们的信任管理器
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(keyManagers, new TrustManager[]{manager}, null);
            //通过sslContext获取SSLSocketFactory对象
            sslParams.sslSocketFactory = sslContext.getSocketFactory();
            sslParams.trustManager = manager;
            return sslParams;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 预备密码管理方法
     *
     * @param bksFile bksFile
     * @param password password
     * @return KeyManager[]
     */
    private static KeyManager[] prepareKeyManger(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) return null;
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientKeyStore, password.toCharArray());
            return kmf.getKeyManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0) return null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //创建一个默认类型的KeyStore，存储我们信任的证书
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            //索引
            int index = 0;
            for (InputStream certStream : certificates) {
                String certificateAlias = Integer.toString(index++);
                //证书工厂根据证书文件的刘生成证书cert
                Certificate cert = certificateFactory.generateCertificate(certStream);
                //将cert作为可信任证书放到keyStore中
                keyStore.setCertificateEntry(certificateAlias, cert);
                try {
                    if (certStream != null) certStream.close();
                } catch (IOException e) {
                    Log.e("ssl", Objects.requireNonNull(e.getMessage()));
                }
            }
            //创建一个默认类型的TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //用之前的keyStory实例初始化TrustManagerFactory，这样tmf就会信任keyStory中的证书
            tmf.init(keyStore);
            //通过tmf获取TrustManager数组，TrustManager也会信任keyStory中的证书
            return tmf.getTrustManagers();
        } catch (Exception e) {
            Log.e("ssl", Objects.requireNonNull(e.getMessage()));
        }
        return null;
    }

    /**
     * 选择 chooseTrustManager
     *
     * @param trustManagers trustManagers
     * @return
     */
    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    public static class SSLParams {
        //默认的 SSLSocketFactory 校验服务器
        public SSLSocketFactory sslSocketFactory;
        //校验服务器的证书
        public X509TrustManager trustManager;
    }

//    /**
//     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
//     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
//     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
//     */
//    public static HostnameVerifier UnSafeHostnameVerifier = new HostnameVerifier() {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            return true;
//        }
//    };

}
