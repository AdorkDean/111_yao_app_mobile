package com.rc.openapi.config;

import java.util.Properties;

import com.rc.openapi.util.PropertiesUtil;

/**
 * Created by ws on 2015/8/21.
 */
public class CommonConfig {

    public static Properties commonConfigProperties;

    static {
        commonConfigProperties = PropertiesUtil.readPropertiesFile("commonConfig.properties");
    }

    public static Properties getProperties()
    {
        if( commonConfigProperties == null )
        {
            commonConfigProperties = PropertiesUtil.readPropertiesFile("commonConfig.properties");
        }

        return commonConfigProperties;
    }


}
