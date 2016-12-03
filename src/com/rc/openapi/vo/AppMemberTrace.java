package com.rc.openapi.vo;

import java.util.Date;

public class AppMemberTrace {
    private Long id;

    private Long memberId;

    private Date createDate;

    private Date lastLoginDate;

    private String netType;

    private String systemType;

    private String clientType;

    private String channelSn;

    private String channelName;

    private String ratio;

    private String uuid;

    private String clientVersion;

    
    
    public AppMemberTrace() {
		super();
	}

	public AppMemberTrace( Date createDate,
			Date lastLoginDate, String netType, String systemType,
			String clientType, String channelSn, String channelName,
			String ratio, String uuid, String clientVersion) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
		this.netType = netType;
		this.systemType = systemType;
		this.clientType = clientType;
		this.channelSn = channelSn;
		this.channelName = channelName;
		this.ratio = ratio;
		this.uuid = uuid;
		this.clientVersion = clientVersion;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getChannelSn() {
        return channelSn;
    }

    public void setChannelSn(String channelSn) {
        this.channelSn = channelSn;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}