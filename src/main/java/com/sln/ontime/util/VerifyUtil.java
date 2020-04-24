package com.sln.ontime.util;


import java.util.Collection;

/**
 * @Description : 判空工具类
 * @Author : guopei
 * @Date : 2020-01-29
 */
public class VerifyUtil {

    /**
     * @Description : 判断字符串是否为空
     * @Param : [string]
     * @Return : boolean
     */
    public static boolean isEmpty(String string){

        return  (null == string  || "".equals(string) || string.trim().isEmpty());
    }

    /**
     * @Description : 判断对象是否为空
     * @Param : [object]
     * @Return : boolean
     */
    public static boolean isNull(Object object){

        return null == object;
    }

    /**
     * @Description : 判断集合是否为空
     * @Param : [collection]
     * @Return : boolean
     */
    public static boolean isEmpty(Collection<?> collection){

        return  ( null == collection || collection.size() == 0 );
    }


    public static boolean isEmpty(Integer integer) {
       return (null == integer);
    }



}
