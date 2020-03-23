package com.laodai.network.utils;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:17 2020-02-10
 * @ Description：格式化后的JSON串
 * @ Modified By：
 * @Version: ：1.0
 */
public class JsonUtils {

    /**
     * 格式化字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return String
     */
    public static String formatJson(String jsonStr) {
        if (jsonStr == null || "".equals(jsonStr)) return "";
        //StringBuffer对象
        StringBuffer sb = new StringBuffer();
        //通过"\"对后面的"0"进行转义('\0'一般用于字符串，表示字符串结束)
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{、[则换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append("\n");
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到}、]则换行，且下一行缩进
                case '}':
                case ']':
                    sb.append("\n");
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到, 则换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append("\n");
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加空间(space)
     *
     * @param sb sb
     * @param indent ident
     */
    private static void addIndentBlank(StringBuffer sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 Unicode 编码转汉字转码
     *
     * @param theString theString
     * @return String
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        //获取字符长度
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int i = 0; i < len;) {
            aChar = theString.charAt(i++);
            if (aChar == '\\') {
                aChar = theString.charAt(i++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int j = 0; j < 4; j++) {
                        aChar = theString.charAt(i++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append(value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = 'r';
                    } else if (aChar == 'n') {
                        aChar = 'n';
                    } else if (aChar == 'f') {
                        aChar = 'f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

}
