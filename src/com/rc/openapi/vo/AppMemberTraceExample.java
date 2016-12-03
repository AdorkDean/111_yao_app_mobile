package com.rc.openapi.vo;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rc.app.framework.webapp.model.BaseModel;

public class AppMemberTraceExample  extends BaseModel{

    protected String orderByClause;

    protected List oredCriteria;

    public AppMemberTraceExample() {
        oredCriteria = new ArrayList();


    }

    protected AppMemberTraceExample(AppMemberTraceExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;


    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;


    }

    public String getOrderByClause() {
        return orderByClause;


    }

    public List getOredCriteria() {
        return oredCriteria;


    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);


    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);


        }
        return criteria;


    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;


    }

    public void clear() {
        oredCriteria.clear();


    }

    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();


        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;


        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;


        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;


        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;


        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;


        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");


            }
            criteriaWithoutValue.add(condition);


        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");


            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);


        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");


            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);


        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");


            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);


        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return this;


        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return this;


        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return this;


        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return this;


        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return this;


        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return this;


        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return this;


        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return this;


        }

        public Criteria andIdIn(List values) {
            addCriterion("id in", values, "id");
            return this;


        }

        public Criteria andIdNotIn(List values) {
            addCriterion("id not in", values, "id");
            return this;


        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return this;


        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return this;


        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return this;


        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return this;


        }

        public Criteria andMemberIdEqualTo(Long value) {
            addCriterion("member_id =", value, "memberId");
            return this;


        }

        public Criteria andMemberIdNotEqualTo(Long value) {
            addCriterion("member_id <>", value, "memberId");
            return this;


        }

        public Criteria andMemberIdGreaterThan(Long value) {
            addCriterion("member_id >", value, "memberId");
            return this;


        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id >=", value, "memberId");
            return this;


        }

        public Criteria andMemberIdLessThan(Long value) {
            addCriterion("member_id <", value, "memberId");
            return this;


        }

        public Criteria andMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("member_id <=", value, "memberId");
            return this;


        }

        public Criteria andMemberIdIn(List values) {
            addCriterion("member_id in", values, "memberId");
            return this;


        }

        public Criteria andMemberIdNotIn(List values) {
            addCriterion("member_id not in", values, "memberId");
            return this;


        }

        public Criteria andMemberIdBetween(Long value1, Long value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return this;


        }

        public Criteria andMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return this;


        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return this;


        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return this;


        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return this;


        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return this;


        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return this;


        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return this;


        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return this;


        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return this;


        }

        public Criteria andCreateDateIn(List values) {
            addCriterion("create_date in", values, "createDate");
            return this;


        }

        public Criteria andCreateDateNotIn(List values) {
            addCriterion("create_date not in", values, "createDate");
            return this;


        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return this;


        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return this;


        }

        public Criteria andLastLoginDateIsNull() {
            addCriterion("last_login_date is null");
            return this;


        }

        public Criteria andLastLoginDateIsNotNull() {
            addCriterion("last_login_date is not null");
            return this;


        }

        public Criteria andLastLoginDateEqualTo(Date value) {
            addCriterion("last_login_date =", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateNotEqualTo(Date value) {
            addCriterion("last_login_date <>", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateGreaterThan(Date value) {
            addCriterion("last_login_date >", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_login_date >=", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateLessThan(Date value) {
            addCriterion("last_login_date <", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateLessThanOrEqualTo(Date value) {
            addCriterion("last_login_date <=", value, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateIn(List values) {
            addCriterion("last_login_date in", values, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateNotIn(List values) {
            addCriterion("last_login_date not in", values, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateBetween(Date value1, Date value2) {
            addCriterion("last_login_date between", value1, value2, "lastLoginDate");
            return this;


        }

        public Criteria andLastLoginDateNotBetween(Date value1, Date value2) {
            addCriterion("last_login_date not between", value1, value2, "lastLoginDate");
            return this;


        }

        public Criteria andNetTypeIsNull() {
            addCriterion("net_type is null");
            return this;


        }

        public Criteria andNetTypeIsNotNull() {
            addCriterion("net_type is not null");
            return this;


        }

        public Criteria andNetTypeEqualTo(String value) {
            addCriterion("net_type =", value, "netType");
            return this;


        }

        public Criteria andNetTypeNotEqualTo(String value) {
            addCriterion("net_type <>", value, "netType");
            return this;


        }

        public Criteria andNetTypeGreaterThan(String value) {
            addCriterion("net_type >", value, "netType");
            return this;


        }

        public Criteria andNetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("net_type >=", value, "netType");
            return this;


        }

        public Criteria andNetTypeLessThan(String value) {
            addCriterion("net_type <", value, "netType");
            return this;


        }

        public Criteria andNetTypeLessThanOrEqualTo(String value) {
            addCriterion("net_type <=", value, "netType");
            return this;


        }

        public Criteria andNetTypeLike(String value) {
            addCriterion("net_type like", value, "netType");
            return this;


        }

        public Criteria andNetTypeNotLike(String value) {
            addCriterion("net_type not like", value, "netType");
            return this;


        }

        public Criteria andNetTypeIn(List values) {
            addCriterion("net_type in", values, "netType");
            return this;


        }

        public Criteria andNetTypeNotIn(List values) {
            addCriterion("net_type not in", values, "netType");
            return this;


        }

        public Criteria andNetTypeBetween(String value1, String value2) {
            addCriterion("net_type between", value1, value2, "netType");
            return this;


        }

        public Criteria andNetTypeNotBetween(String value1, String value2) {
            addCriterion("net_type not between", value1, value2, "netType");
            return this;


        }

        public Criteria andSystemTypeIsNull() {
            addCriterion("system_type is null");
            return this;


        }

        public Criteria andSystemTypeIsNotNull() {
            addCriterion("system_type is not null");
            return this;


        }

        public Criteria andSystemTypeEqualTo(String value) {
            addCriterion("system_type =", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeNotEqualTo(String value) {
            addCriterion("system_type <>", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeGreaterThan(String value) {
            addCriterion("system_type >", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("system_type >=", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeLessThan(String value) {
            addCriterion("system_type <", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeLessThanOrEqualTo(String value) {
            addCriterion("system_type <=", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeLike(String value) {
            addCriterion("system_type like", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeNotLike(String value) {
            addCriterion("system_type not like", value, "systemType");
            return this;


        }

        public Criteria andSystemTypeIn(List values) {
            addCriterion("system_type in", values, "systemType");
            return this;


        }

        public Criteria andSystemTypeNotIn(List values) {
            addCriterion("system_type not in", values, "systemType");
            return this;


        }

        public Criteria andSystemTypeBetween(String value1, String value2) {
            addCriterion("system_type between", value1, value2, "systemType");
            return this;


        }

        public Criteria andSystemTypeNotBetween(String value1, String value2) {
            addCriterion("system_type not between", value1, value2, "systemType");
            return this;


        }

        public Criteria andClientTypeIsNull() {
            addCriterion("client_type is null");
            return this;


        }

        public Criteria andClientTypeIsNotNull() {
            addCriterion("client_type is not null");
            return this;


        }

        public Criteria andClientTypeEqualTo(String value) {
            addCriterion("client_type =", value, "clientType");
            return this;


        }

        public Criteria andClientTypeNotEqualTo(String value) {
            addCriterion("client_type <>", value, "clientType");
            return this;


        }

        public Criteria andClientTypeGreaterThan(String value) {
            addCriterion("client_type >", value, "clientType");
            return this;


        }

        public Criteria andClientTypeGreaterThanOrEqualTo(String value) {
            addCriterion("client_type >=", value, "clientType");
            return this;


        }

        public Criteria andClientTypeLessThan(String value) {
            addCriterion("client_type <", value, "clientType");
            return this;


        }

        public Criteria andClientTypeLessThanOrEqualTo(String value) {
            addCriterion("client_type <=", value, "clientType");
            return this;


        }

        public Criteria andClientTypeLike(String value) {
            addCriterion("client_type like", value, "clientType");
            return this;


        }

        public Criteria andClientTypeNotLike(String value) {
            addCriterion("client_type not like", value, "clientType");
            return this;


        }

        public Criteria andClientTypeIn(List values) {
            addCriterion("client_type in", values, "clientType");
            return this;


        }

        public Criteria andClientTypeNotIn(List values) {
            addCriterion("client_type not in", values, "clientType");
            return this;


        }

        public Criteria andClientTypeBetween(String value1, String value2) {
            addCriterion("client_type between", value1, value2, "clientType");
            return this;


        }

        public Criteria andClientTypeNotBetween(String value1, String value2) {
            addCriterion("client_type not between", value1, value2, "clientType");
            return this;


        }

        public Criteria andChannelSnIsNull() {
            addCriterion("channel_sn is null");
            return this;


        }

        public Criteria andChannelSnIsNotNull() {
            addCriterion("channel_sn is not null");
            return this;


        }

        public Criteria andChannelSnEqualTo(String value) {
            addCriterion("channel_sn =", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnNotEqualTo(String value) {
            addCriterion("channel_sn <>", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnGreaterThan(String value) {
            addCriterion("channel_sn >", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnGreaterThanOrEqualTo(String value) {
            addCriterion("channel_sn >=", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnLessThan(String value) {
            addCriterion("channel_sn <", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnLessThanOrEqualTo(String value) {
            addCriterion("channel_sn <=", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnLike(String value) {
            addCriterion("channel_sn like", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnNotLike(String value) {
            addCriterion("channel_sn not like", value, "channelSn");
            return this;


        }

        public Criteria andChannelSnIn(List values) {
            addCriterion("channel_sn in", values, "channelSn");
            return this;


        }

        public Criteria andChannelSnNotIn(List values) {
            addCriterion("channel_sn not in", values, "channelSn");
            return this;


        }

        public Criteria andChannelSnBetween(String value1, String value2) {
            addCriterion("channel_sn between", value1, value2, "channelSn");
            return this;


        }

        public Criteria andChannelSnNotBetween(String value1, String value2) {
            addCriterion("channel_sn not between", value1, value2, "channelSn");
            return this;


        }

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return this;


        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return this;


        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return this;


        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return this;


        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return this;


        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return this;


        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return this;


        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return this;


        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return this;


        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return this;


        }

        public Criteria andChannelNameIn(List values) {
            addCriterion("channel_name in", values, "channelName");
            return this;


        }

        public Criteria andChannelNameNotIn(List values) {
            addCriterion("channel_name not in", values, "channelName");
            return this;


        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return this;


        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
            return this;


        }

        public Criteria andRatioIsNull() {
            addCriterion("ratio is null");
            return this;


        }

        public Criteria andRatioIsNotNull() {
            addCriterion("ratio is not null");
            return this;


        }

        public Criteria andRatioEqualTo(String value) {
            addCriterion("ratio =", value, "ratio");
            return this;


        }

        public Criteria andRatioNotEqualTo(String value) {
            addCriterion("ratio <>", value, "ratio");
            return this;


        }

        public Criteria andRatioGreaterThan(String value) {
            addCriterion("ratio >", value, "ratio");
            return this;


        }

        public Criteria andRatioGreaterThanOrEqualTo(String value) {
            addCriterion("ratio >=", value, "ratio");
            return this;


        }

        public Criteria andRatioLessThan(String value) {
            addCriterion("ratio <", value, "ratio");
            return this;


        }

        public Criteria andRatioLessThanOrEqualTo(String value) {
            addCriterion("ratio <=", value, "ratio");
            return this;


        }

        public Criteria andRatioLike(String value) {
            addCriterion("ratio like", value, "ratio");
            return this;


        }

        public Criteria andRatioNotLike(String value) {
            addCriterion("ratio not like", value, "ratio");
            return this;


        }

        public Criteria andRatioIn(List values) {
            addCriterion("ratio in", values, "ratio");
            return this;


        }

        public Criteria andRatioNotIn(List values) {
            addCriterion("ratio not in", values, "ratio");
            return this;


        }

        public Criteria andRatioBetween(String value1, String value2) {
            addCriterion("ratio between", value1, value2, "ratio");
            return this;


        }

        public Criteria andRatioNotBetween(String value1, String value2) {
            addCriterion("ratio not between", value1, value2, "ratio");
            return this;


        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return this;


        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return this;


        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return this;


        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return this;


        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return this;


        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return this;


        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return this;


        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return this;


        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return this;


        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return this;


        }

        public Criteria andUuidIn(List values) {
            addCriterion("uuid in", values, "uuid");
            return this;


        }

        public Criteria andUuidNotIn(List values) {
            addCriterion("uuid not in", values, "uuid");
            return this;


        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return this;


        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return this;


        }

        public Criteria andClientVersionIsNull() {
            addCriterion("client_version is null");
            return this;


        }

        public Criteria andClientVersionIsNotNull() {
            addCriterion("client_version is not null");
            return this;


        }

        public Criteria andClientVersionEqualTo(String value) {
            addCriterion("client_version =", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionNotEqualTo(String value) {
            addCriterion("client_version <>", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionGreaterThan(String value) {
            addCriterion("client_version >", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionGreaterThanOrEqualTo(String value) {
            addCriterion("client_version >=", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionLessThan(String value) {
            addCriterion("client_version <", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionLessThanOrEqualTo(String value) {
            addCriterion("client_version <=", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionLike(String value) {
            addCriterion("client_version like", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionNotLike(String value) {
            addCriterion("client_version not like", value, "clientVersion");
            return this;


        }

        public Criteria andClientVersionIn(List values) {
            addCriterion("client_version in", values, "clientVersion");
            return this;


        }

        public Criteria andClientVersionNotIn(List values) {
            addCriterion("client_version not in", values, "clientVersion");
            return this;


        }

        public Criteria andClientVersionBetween(String value1, String value2) {
            addCriterion("client_version between", value1, value2, "clientVersion");
            return this;


        }

        public Criteria andClientVersionNotBetween(String value1, String value2) {
            addCriterion("client_version not between", value1, value2, "clientVersion");
            return this;


        }


    }


}
