package com.rc.openapi.cache;

import java.util.Map;
import java.util.TimerTask;

import com.rc.openapi.util.StringUtil;

/**
 * Created by ws on 2015/8/21.
 */
public class MapCacheClearTimerTask extends TimerTask {

    private String key = null;
    private Map map = null;

    public MapCacheClearTimerTask(String key, Map map)
    {
        this.key = key;
        this.map = map;
    }

    @Override
    public void run() {
        if( !StringUtil.isNull(key) && null != map )
        {
            map.remove( key );
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
