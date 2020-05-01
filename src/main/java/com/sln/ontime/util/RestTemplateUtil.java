package com.sln.ontime.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @description http请求传输类
 * @author guopei
 * @date 2020-04-24 16:59
 */
@Component
public class RestTemplateUtil {

    private static class SingleTonHolder{

        private static RestTemplate restTemplate = new RestTemplate();
    }

    private RestTemplateUtil() {}

    /**
     * 单例实例
     * @return
     */
    public static RestTemplate getInstance(){

        RestTemplate restTemplate =  SingleTonHolder.restTemplate;

        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        httpMessageConverters.stream().forEach(httpMessageConverter -> {
            if(httpMessageConverter instanceof StringHttpMessageConverter){
                StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
            }
        });

        return restTemplate;
    }

    /**
     * post请求 不加密
     * @param requestUrl 访问连接
     * @param data 传输参数json
     * @return
     */
    public static String post(String requestUrl, MultiValueMap<String, String> data ){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type","application/x-www-form-urlencoded");

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(data, httpHeaders);
            return RestTemplateUtil.getInstance().exchange(requestUrl, HttpMethod.POST, httpEntity,String.class).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "请求失败";
        }
    }

    /**
     * get根据url获取对象
     */
    public static String get(String url) {
        try {
            return RestTemplateUtil.getInstance().getForObject(url, String.class, new Object[] {});
        } catch (RestClientException e) {
            e.printStackTrace();
            return "请求失败";
        }
    }
}
