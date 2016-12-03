package com.rc.openapi.config;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.rc.openapi.util.StringSearchHelper;

/**
 * Created by ws on 2015/8/21.
 */
public class CacheKeysConfigProperties {

    private static final Logger log = Logger.getLogger(CacheKeysConfigProperties.class);

    public static Properties cacheKeysConfigProperties;

    public static String prefix;
    public static String expiry_sufix;
    public static String key_all_sub_districts_of_parentId;
    public static long key_all_sub_districts_of_parentId_expiry = 315360000000l;

    public static String key_sub_districts_of_parentId;
    public static long key_sub_districts_of_parentId_expiry = 315360000000l;

    public static String key_district_self_value_without_subs;
    public static long key_district_self_value_without_subs_expiry = 315360000000l;



    static {
        init();
    }
    public static void init()
    {
        cacheKeysConfigProperties = CacheKeysConfig.getProperties();
        prefix = "cache_key.";
        expiry_sufix = ".expiry";

        //
        key_all_sub_districts_of_parentId = cacheKeysConfigProperties.getProperty( prefix + "key_all_sub_districts_of_parentId" );
        try{
            key_all_sub_districts_of_parentId_expiry = Long.parseLong( cacheKeysConfigProperties.getProperty( prefix + "key_all_sub_districts_of_parentId" + expiry_sufix ) );
        }catch ( Exception e ){
            log.error(e);
            e.printStackTrace();
        }

        key_sub_districts_of_parentId = cacheKeysConfigProperties.getProperty( prefix + "key_sub_districts_of_parentId" );
        try{
            key_sub_districts_of_parentId_expiry = Long.parseLong( cacheKeysConfigProperties.getProperty( prefix + "key_sub_districts_of_parentId" + expiry_sufix ) );
        }catch ( Exception e ){
            log.error(e);
            e.printStackTrace();
        }

        key_district_self_value_without_subs = cacheKeysConfigProperties.getProperty( prefix + "key_district_self_value_without_subs" );
        try{
            key_district_self_value_without_subs_expiry = Long.parseLong( cacheKeysConfigProperties.getProperty( prefix + "key_district_self_value_without_subs" + expiry_sufix ) );
        }catch ( Exception e ){
            log.error(e);
            e.printStackTrace();
        }


    }

    public static String getProperty( String key )
    {
        return cacheKeysConfigProperties.getProperty(key);
    }

    public static Properties getCacheKeysConfigProperties() {
        return cacheKeysConfigProperties;
    }

    public static void setCacheKeysConfigProperties(Properties cacheKeysConfigProperties) {
        CacheKeysConfigProperties.cacheKeysConfigProperties = cacheKeysConfigProperties;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        CacheKeysConfigProperties.prefix = prefix;
    }

    public static String getKey_all_sub_districts_of_parentId( String parentId) {
        return StringSearchHelper.replaceAll(key_all_sub_districts_of_parentId, "\\{PARENT_ID\\}", parentId);
    }

    public static void setKey_all_sub_districts_of_parentId(String key_all_sub_districts_of_parentId) {
        CacheKeysConfigProperties.key_all_sub_districts_of_parentId = key_all_sub_districts_of_parentId;
    }

    public static String getKey_sub_districts_of_parentId( String parentId ) {
        return StringSearchHelper.replaceAll(key_sub_districts_of_parentId, "\\{PARENT_ID\\}", parentId);
    }

    public static String getKey_district_self_value_without_subs( String districtId ) {
        return StringSearchHelper.replaceAll(key_district_self_value_without_subs, "\\{DISTRICT_ID\\}", districtId );
    }

    public static void setKey_district_self_value_without_subs(String key_district_self_value_without_subs) {
        CacheKeysConfigProperties.key_district_self_value_without_subs = key_district_self_value_without_subs;
    }

    public static long getKey_sub_districts_of_parentId_expiry() {
        return key_sub_districts_of_parentId_expiry;
    }

    public static void setKey_sub_districts_of_parentId_expiry(long key_sub_districts_of_parentId_expiry) {
        CacheKeysConfigProperties.key_sub_districts_of_parentId_expiry = key_sub_districts_of_parentId_expiry;
    }

    public static long getKey_district_self_value_without_subs_expiry() {
        return key_district_self_value_without_subs_expiry;
    }

    public static void setKey_district_self_value_without_subs_expiry(long key_district_self_value_without_subs_expiry) {
        CacheKeysConfigProperties.key_district_self_value_without_subs_expiry = key_district_self_value_without_subs_expiry;
    }

    public static void setKey_all_sub_districts_of_parentId_expiry(long key_all_sub_districts_of_parentId_expiry) {
        CacheKeysConfigProperties.key_all_sub_districts_of_parentId_expiry = key_all_sub_districts_of_parentId_expiry;
    }
    public static long getKey_all_sub_districts_of_parentId_expiry() {
        return key_all_sub_districts_of_parentId_expiry;
    }



}
