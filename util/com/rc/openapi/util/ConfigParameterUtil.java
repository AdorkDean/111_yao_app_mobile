package com.rc.openapi.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.common.util.CollectionUtils;

/**
 * Created by ws on 2015/8/26.
 */
public class ConfigParameterUtil {

    public static String parameterReplaceTwofold( String s, Map<String, String> parameterMap )
    {
        return parameterReplace(parameterReplace(s, parameterMap, "\\{", "\\}"), parameterMap, "~@\\$", "\\$@~");
    }
    public static String parameterReplace( String s, Map<String, String> parameterMap )
    {
        return parameterReplace(s, parameterMap, "\\{", "\\}");
    }
    public static String parameterReplace( String s, Map<String, String> parameterMap, String paramPrefix, String paramSuffix )
    {
        String ret = s;
        if( StringUtil.isNull(s) || null == parameterMap || parameterMap.size() == 0 )
        {
            return s;
        }
        try{

            Set<String> keySet = parameterMap.keySet();
            if( CollectionUtils.isEmpty(keySet) )
            {
                return s;
            }
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext())
            {
                String keyObj = iterator.next();
                String valueObj = parameterMap.get( keyObj );
                if( null == keyObj || null == valueObj )
                {
                    continue;
                }

                String pPrefix = (StringUtil.isNull(paramPrefix) ? "" : paramPrefix );
                String pSuffix = (StringUtil.isNull(paramSuffix) ? "" : paramSuffix );

                ret = StringSearchHelper.replaceAll(ret, pPrefix + keyObj.toString() + pSuffix, valueObj.toString());

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return ret;
    }


    public static void main( String args[] )
    {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";
        Map map = new HashMap();
        map.put("ACCESS_TOKEN", "123");
        map.put("OPENID", "456");
        String s = parameterReplace( url, map );
        System.out.println( s );

        s = "{\n" +
                "    \"first\": {\n" +
                "        \"value\": \"恭喜您购买成功！\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    },\n" +
                "    \"keyword1\": {\n" +
                "        \"value\": \"{buyerName}\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    },\n" +
                "    \"keyword2\": {\n" +
                "        \"value\": \"{productName}\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    },\n" +
                "    \"keyword3\": {\n" +
                "        \"value\": \"{productPrice}\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    },\n" +
                "    \"keyword4\": {\n" +
                "        \"value\": \"{releaseDate}\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    },\n" +
                "    \"remark\": {\n" +
                "        \"value\": \"德生堂,祝您早日康复!\",\n" +
                "        \"color\": \"#173177\"\n" +
                "    }\n" +
                "}";

        Map pMap = new HashMap();
        s = parameterReplace( url, map );
        System.out.println( s );

    }

}
