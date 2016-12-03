package com.rc.openapi.bean;


import org.apache.log4j.Logger;

/**
 * Created by ws on 2016/6/21.
 */
public class LocationResponseBean extends BaseResponse {

    private  Logger logger = Logger.getLogger( LocationResponseBean.class );


    private String location;
    private String level;

    public String getLocation() {

        return location;

    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLevel() {

        return level;

    }

    public void setLevel(String level) {
        this.level = level;
    }




}
