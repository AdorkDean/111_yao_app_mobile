package com.rc.openapi.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.rc.openapi.util.MemCached;

/**
 * Created by ws on 2015/8/21.
 */
public class ACacheClient {

    private final Logger log = Logger.getLogger(this.getClass());

    private static Map aCacheMap;
    private static Map aCacheExpireMap;
    private static MemCachedClient memCachedClient;

    static {
        init();
    }
    private static void  init()
    {
        aCacheMap = new HashMap();
        aCacheExpireMap = new HashMap();
        memCachedClient = MemCached.getmcc();
    }

    public static boolean set( String key, Object value, long expiry_in )
    {
        Date expiryDate = new Date( new Date().getTime() + expiry_in );

        return set( key, value, expiryDate );
    }
    public static boolean set( String key, Object value, Date expiry )
    {
        boolean ret = false;
        boolean mSet = memCachedClient.set( key, value, expiry );
        if( !mSet )
        {
            aCacheMap.put(key, value);
            aCacheExpireMap.put(key, expiry);

            Timer timer = new Timer();
            MapCacheClearTimerTask mapCacheClearTimerTask = new MapCacheClearTimerTask( key, aCacheMap );
            timer.schedule(mapCacheClearTimerTask, expiry );
        }

        return ret;
    }




    public static Object get( String key )
    {
        Object obj = memCachedClient.get( key );
        if( null == obj )
        {
            obj = aCacheMap.get( key );
        }

        return obj;
    }


    public static boolean delete(String key) {
        boolean mDel = memCachedClient.delete(key);
        aCacheMap.remove(key);
        aCacheExpireMap.remove(key);

        //System.gc();

        return mDel;
    }


}
