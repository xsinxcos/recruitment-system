package com.achobeta.utils;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

public class WechatUtil {
    /**
     *测试号接口配置检验
     * @Param signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @Param timestamp	时间戳
     * @Param nonce	随机数
     * @Param echostr	随机字符串
     * @Param token
     * @return  boolean
     */
    public static boolean checkAccess(String signature ,String timestamp ,String nonce ,String echoStr ,String token){
        //将token、timestamp、nonce三个参数进行排序
        try {
            String[] requestParam=new String[]{token,timestamp,nonce};
            Arrays.sort(requestParam);
            StringBuilder sb=new StringBuilder();
            for (String s : requestParam) {
                sb.append(s);
            }
            //sha1加密
            String shaHex = DigestUtils.sha1Hex(sb.toString());
            //与signature进行比较
            if (signature.equals(shaHex)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public static String obj2Xml(Object obj) {
        XStream xstream = new XStream();
        xstream.alias("xml", obj.getClass());
        return xstream.toXML(obj);
    }
}
