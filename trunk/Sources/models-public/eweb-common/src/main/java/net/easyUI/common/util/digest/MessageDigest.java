package net.easyUI.common.util.digest;

/**
 * 信息摘要接口
 * 
 */
public interface MessageDigest {

    /**
     * 对明文信息进行信息摘要处理,使用UTF-8编码
     * 
     * @param text 明文信息
     * @return String 信息摘要
     */
    public String digest(String text);

    /**
     * 对明文信息进行信息摘要处理,使用指定字符集编码
     *  
     * @param text 明文信息
     * @param encoding 字符集编码
     * @return 信息摘要
     */
    public String digest(String text, String encoding);

}
