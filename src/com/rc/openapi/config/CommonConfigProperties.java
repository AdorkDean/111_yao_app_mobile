package com.rc.openapi.config;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.rc.openapi.util.StringUtil;

/**
 * Created by ws on 2015/8/21.
 */
public class CommonConfigProperties {

    private static final Logger log = Logger.getLogger(CommonConfigProperties.class);

    public static Properties commonConfigProperties;
    public static String prefix;
    public static String paramSignKey;
    public static String chartSet;
    public static String siteUrl;
    public static String uiUrl;
    public static String serviceServerURL;
    public static int httpUtilDefaultConnectionTimeOut = 10000;
    public static int httpUtilDefaultReadTimeout = 10000;


    static {
        init();
    }
    public static void init()
    {
        commonConfigProperties = CommonConfig.getProperties();
        prefix = "common.";
        paramSignKey = commonConfigProperties.getProperty( prefix + "paramSignKey" );

        chartSet = commonConfigProperties.getProperty(prefix + "chartSet");
        siteUrl = StringUtil.trim(commonConfigProperties.getProperty(prefix + "siteUrl"));
        uiUrl = StringUtil.trim(commonConfigProperties.getProperty(prefix + "uiUrl"));
        serviceServerURL = StringUtil.trim(commonConfigProperties.getProperty(prefix + "serviceServerURL"));

        String httpUtilDefaultConnectionTimeOut_str = commonConfigProperties.getProperty( prefix + "http.util.default.connection_timeout" );
        try{
            httpUtilDefaultConnectionTimeOut = Integer.parseInt( httpUtilDefaultConnectionTimeOut_str );
        }catch ( Exception e ){
            e.printStackTrace();
            log.error( e );
        }

        String httpUtilDefaultReadTimeout_str = commonConfigProperties.getProperty( prefix + "http.util.default.read_timeout" );
        try{
            httpUtilDefaultReadTimeout = Integer.parseInt( httpUtilDefaultReadTimeout_str );
        }catch ( Exception e ){
            e.printStackTrace();
            log.error( e );
        }


    }

    public static String getProperty( String key )
    {
        return commonConfigProperties.getProperty( key );
    }


    public static Properties getcommonConfigProperties() {
        return commonConfigProperties;
    }

    public static void setcommonConfigProperties(Properties commonConfigProperties) {
        CommonConfigProperties.commonConfigProperties = commonConfigProperties;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        CommonConfigProperties.prefix = prefix;
    }


    public static Properties getCommonConfigProperties() {
        return commonConfigProperties;
    }

    public static void setCommonConfigProperties(Properties commonConfigProperties) {
        CommonConfigProperties.commonConfigProperties = commonConfigProperties;
    }

    public static String getParamSignKey() {
        return paramSignKey;
    }

    public static void setParamSignKey(String paramSignKey) {
        CommonConfigProperties.paramSignKey = paramSignKey;
    }

    public static String getChartSet() {
        return chartSet;
    }

    public static void setChartSet(String chartSet) {
        CommonConfigProperties.chartSet = chartSet;
    }


    public static String getSiteUrl() {
        return siteUrl;
    }

    public static void setSiteUrl(String siteUrl) {
        CommonConfigProperties.siteUrl = siteUrl;
    }

    public static String getUiUrl() {
        return uiUrl;
    }

    public static void setUiUrl(String uiUrl) {
        CommonConfigProperties.uiUrl = uiUrl;
    }

    public static String getServiceServerURL() {
        return serviceServerURL;
    }

    public static void setServiceServerURL(String serviceServerURL) {
        CommonConfigProperties.serviceServerURL = serviceServerURL;
    }

    public static int getHttpUtilDefaultConnectionTimeOut() {
        return httpUtilDefaultConnectionTimeOut;
    }

    public static void setHttpUtilDefaultConnectionTimeOut(int httpUtilDefaultConnectionTimeOut) {
        CommonConfigProperties.httpUtilDefaultConnectionTimeOut = httpUtilDefaultConnectionTimeOut;
    }

    public static int getHttpUtilDefaultReadTimeout() {
        return httpUtilDefaultReadTimeout;
    }

    public static void setHttpUtilDefaultReadTimeout(int httpUtilDefaultReadTimeout) {
        CommonConfigProperties.httpUtilDefaultReadTimeout = httpUtilDefaultReadTimeout;
    }
}
