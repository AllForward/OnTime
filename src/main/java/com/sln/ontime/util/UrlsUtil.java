package com.sln.ontime.util;

/**
 * @description 记录所有需要调用微信的接口,并对其中的参数进行转换
 * @author guopei
 * @date 2020-04-27 18:01
 */
public class UrlsUtil {

    private static final String loginUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET" +
            "&js_code=JSCODE&grant_type=authorization_code";

    private static final String appid = "wx512e190ce5effdd8";

    private static final String secret = "34ab81b2d71daf0194175ae5324e5150";

    public static String getLoginUrl(String code) {
        //将appid、secret以及code替代url
        return loginUrl.replaceAll("APPID", appid).replaceAll("SECRET", secret)
                .replaceAll("JSCODE", code);
    }

}
