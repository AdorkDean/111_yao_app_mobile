package com.rc.openapi.config;

import java.util.Properties;

import com.rc.openapi.util.PropertiesUtil;

/**
 * Created by ws on 2015/8/21.
 */
public class CacheKeysConfig {

    public static Properties cacheKeysConfigProperties;

    static {
        cacheKeysConfigProperties = PropertiesUtil.readPropertiesFile("cache_keys.properties");
    }

    public static Properties getProperties()
    {
        if( cacheKeysConfigProperties == null )
        {
            cacheKeysConfigProperties = PropertiesUtil.readPropertiesFile("cache_keys.properties");
        }

        return cacheKeysConfigProperties;
    }


}
