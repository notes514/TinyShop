package com.laodai.library.cookie;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

import okhttp3.Cookie;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:07 2020-01-31
 * @ Description：
 * @ Modified By：
 * @Version: ：1.0
 */
public class SerializableCookie implements Serializable {
    public static final String HOST = "host";
    public static final String NAME = "name";
    public static final String DOMAIN = "domain";
    public static final String COOKIE = "cookie";
    //序列号
    private static final long serialVersionUID = 6374381323722046732L;
    public String host;
    public String name;
    public String domain;
    private transient Cookie cookie;
    private transient Cookie clientCookie;

    public SerializableCookie(String host, Cookie cookie) {
        this.host = host;
        this.cookie = cookie;
        this.name = cookie.name();
        this.domain = cookie.domain();
    }

    public static SerializableCookie parseCursorToBean(Cursor cursor) {
        String host = cursor.getString(cursor.getColumnIndex(HOST));
        byte[] cookieBytes = cursor.getBlob(cursor.getColumnIndex(COOKIE));
        Cookie cookie = bytesToCookie(cookieBytes);
        return new SerializableCookie(host, cookie);
    }

    public static ContentValues getContentValues(SerializableCookie serializableCookie) {
        ContentValues values = new ContentValues();
        values.put(SerializableCookie.HOST, serializableCookie.host);
        values.put(SerializableCookie.NAME, serializableCookie.NAME);
        values.put(SerializableCookie.DOMAIN, serializableCookie.DOMAIN);
        values.put(SerializableCookie.COOKIE, serializableCookie.COOKIE);
        return values;
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    public static Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        return bytesToCookie(bytes);
    }

    public static Cookie bytesToCookie(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).getCookie();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * cookies序列号成string
     *
     * @param host host
     * @param cookie 要序列化
     * @return 序列化之后的string
     */
    public static String encodeCookie(String host, Cookie cookie) {
        if (cookie == null) return null;
        byte[] cookieBytes = cookieToBytes(host, cookie);
        return byteArrayToHexString(cookieBytes);
    }

    public static byte[] cookieToBytes(String host, Cookie cookie) {
        SerializableCookie serializableCookie = new SerializableCookie(host, cookie);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(serializableCookie);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return os.toByteArray();
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param byteps 要转换的字节数组
     * @return 包含十六进制值的字符串
     */
    private static String byteArrayToHexString(byte[] byteps) {
        StringBuilder sb = new StringBuilder(byteps.length * 2);
        for (byte element : byteps) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString 十六进制编码值字符串
     * @return 解码字节数组
     */
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 获取cookie
     *
     * @return Cookie
     */
    public Cookie getCookie() {
        Cookie bestCookie = cookie;
        if (clientCookie != null) {
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeLong(cookie.expiresAt());
        out.writeObject(cookie.domain());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
        out.writeBoolean(cookie.httpOnly());
        out.writeBoolean(cookie.hostOnly());
        out.writeBoolean(cookie.persistent());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        clientCookie = builder.build();
    }

    /**
     * 重写equals() 标识host, name, domain的一个cookie是否唯一
     *
     * @param obj 父类型
     * @return boolean
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        SerializableCookie that = (SerializableCookie) obj;

        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return domain != null ? domain.equals(that.domain) : that.domain == null;
    }

    /**
     * 重写hashCode() 方法
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }

}
