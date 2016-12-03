package com.rc.openapi.vo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rc.app.framework.webapp.model.BaseModel;

public class AppPaymethodAreaExample  extends BaseModel{

    protected String orderByClause;

    protected List oredCriteria;

    public AppPaymethodAreaExample() {
        oredCriteria = new ArrayList();


    }

    protected AppPaymethodAreaExample(AppPaymethodAreaExample example) {
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

        public Criteria andImgurlIsNull() {
            addCriterion("imgurl is null");
            return this;


        }

        public Criteria andImgurlIsNotNull() {
            addCriterion("imgurl is not null");
            return this;


        }

        public Criteria andImgurlEqualTo(String value) {
            addCriterion("imgurl =", value, "imgurl");
            return this;


        }

        public Criteria andImgurlNotEqualTo(String value) {
            addCriterion("imgurl <>", value, "imgurl");
            return this;


        }

        public Criteria andImgurlGreaterThan(String value) {
            addCriterion("imgurl >", value, "imgurl");
            return this;


        }

        public Criteria andImgurlGreaterThanOrEqualTo(String value) {
            addCriterion("imgurl >=", value, "imgurl");
            return this;


        }

        public Criteria andImgurlLessThan(String value) {
            addCriterion("imgurl <", value, "imgurl");
            return this;


        }

        public Criteria andImgurlLessThanOrEqualTo(String value) {
            addCriterion("imgurl <=", value, "imgurl");
            return this;


        }

        public Criteria andImgurlLike(String value) {
            addCriterion("imgurl like", value, "imgurl");
            return this;


        }

        public Criteria andImgurlNotLike(String value) {
            addCriterion("imgurl not like", value, "imgurl");
            return this;


        }

        public Criteria andImgurlIn(List values) {
            addCriterion("imgurl in", values, "imgurl");
            return this;


        }

        public Criteria andImgurlNotIn(List values) {
            addCriterion("imgurl not in", values, "imgurl");
            return this;


        }

        public Criteria andImgurlBetween(String value1, String value2) {
            addCriterion("imgurl between", value1, value2, "imgurl");
            return this;


        }

        public Criteria andImgurlNotBetween(String value1, String value2) {
            addCriterion("imgurl not between", value1, value2, "imgurl");
            return this;


        }

        public Criteria andIsonlineIsNull() {
            addCriterion("isonline is null");
            return this;


        }

        public Criteria andIsonlineIsNotNull() {
            addCriterion("isonline is not null");
            return this;


        }

        public Criteria andIsonlineEqualTo(Long value) {
            addCriterion("isonline =", value, "isonline");
            return this;


        }

        public Criteria andIsonlineNotEqualTo(Long value) {
            addCriterion("isonline <>", value, "isonline");
            return this;


        }

        public Criteria andIsonlineGreaterThan(Long value) {
            addCriterion("isonline >", value, "isonline");
            return this;


        }

        public Criteria andIsonlineGreaterThanOrEqualTo(Long value) {
            addCriterion("isonline >=", value, "isonline");
            return this;


        }

        public Criteria andIsonlineLessThan(Long value) {
            addCriterion("isonline <", value, "isonline");
            return this;


        }

        public Criteria andIsonlineLessThanOrEqualTo(Long value) {
            addCriterion("isonline <=", value, "isonline");
            return this;


        }

        public Criteria andIsonlineIn(List values) {
            addCriterion("isonline in", values, "isonline");
            return this;


        }

        public Criteria andIsonlineNotIn(List values) {
            addCriterion("isonline not in", values, "isonline");
            return this;


        }

        public Criteria andIsonlineBetween(Long value1, Long value2) {
            addCriterion("isonline between", value1, value2, "isonline");
            return this;


        }

        public Criteria andIsonlineNotBetween(Long value1, Long value2) {
            addCriterion("isonline not between", value1, value2, "isonline");
            return this;


        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return this;


        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return this;


        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return this;


        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return this;


        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return this;


        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return this;


        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return this;


        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return this;


        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return this;


        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return this;


        }

        public Criteria andNameIn(List values) {
            addCriterion("name in", values, "name");
            return this;


        }

        public Criteria andNameNotIn(List values) {
            addCriterion("name not in", values, "name");
            return this;


        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return this;


        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return this;


        }

        public Criteria andPaymethodIdIsNull() {
            addCriterion("paymethod_id is null");
            return this;


        }

        public Criteria andPaymethodIdIsNotNull() {
            addCriterion("paymethod_id is not null");
            return this;


        }

        public Criteria andPaymethodIdEqualTo(Long value) {
            addCriterion("paymethod_id =", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdNotEqualTo(Long value) {
            addCriterion("paymethod_id <>", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdGreaterThan(Long value) {
            addCriterion("paymethod_id >", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdGreaterThanOrEqualTo(Long value) {
            addCriterion("paymethod_id >=", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdLessThan(Long value) {
            addCriterion("paymethod_id <", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdLessThanOrEqualTo(Long value) {
            addCriterion("paymethod_id <=", value, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdIn(List values) {
            addCriterion("paymethod_id in", values, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdNotIn(List values) {
            addCriterion("paymethod_id not in", values, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdBetween(Long value1, Long value2) {
            addCriterion("paymethod_id between", value1, value2, "paymethodId");
            return this;


        }

        public Criteria andPaymethodIdNotBetween(Long value1, Long value2) {
            addCriterion("paymethod_id not between", value1, value2, "paymethodId");
            return this;


        }

        public Criteria andPaypluginIdIsNull() {
            addCriterion("payplugin_id is null");
            return this;


        }

        public Criteria andPaypluginIdIsNotNull() {
            addCriterion("payplugin_id is not null");
            return this;


        }

        public Criteria andPaypluginIdEqualTo(String value) {
            addCriterion("payplugin_id =", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdNotEqualTo(String value) {
            addCriterion("payplugin_id <>", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdGreaterThan(String value) {
            addCriterion("payplugin_id >", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdGreaterThanOrEqualTo(String value) {
            addCriterion("payplugin_id >=", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdLessThan(String value) {
            addCriterion("payplugin_id <", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdLessThanOrEqualTo(String value) {
            addCriterion("payplugin_id <=", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdLike(String value) {
            addCriterion("payplugin_id like", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdNotLike(String value) {
            addCriterion("payplugin_id not like", value, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdIn(List values) {
            addCriterion("payplugin_id in", values, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdNotIn(List values) {
            addCriterion("payplugin_id not in", values, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdBetween(String value1, String value2) {
            addCriterion("payplugin_id between", value1, value2, "paypluginId");
            return this;


        }

        public Criteria andPaypluginIdNotBetween(String value1, String value2) {
            addCriterion("payplugin_id not between", value1, value2, "paypluginId");
            return this;


        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return this;


        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return this;


        }

        public Criteria andAreaEqualTo(Long value) {
            addCriterion("area =", value, "area");
            return this;


        }

        public Criteria andAreaNotEqualTo(Long value) {
            addCriterion("area <>", value, "area");
            return this;


        }

        public Criteria andAreaGreaterThan(Long value) {
            addCriterion("area >", value, "area");
            return this;


        }

        public Criteria andAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("area >=", value, "area");
            return this;


        }

        public Criteria andAreaLessThan(Long value) {
            addCriterion("area <", value, "area");
            return this;


        }

        public Criteria andAreaLessThanOrEqualTo(Long value) {
            addCriterion("area <=", value, "area");
            return this;


        }

        public Criteria andAreaIn(List values) {
            addCriterion("area in", values, "area");
            return this;


        }

        public Criteria andAreaNotIn(List values) {
            addCriterion("area not in", values, "area");
            return this;


        }

        public Criteria andAreaBetween(Long value1, Long value2) {
            addCriterion("area between", value1, value2, "area");
            return this;


        }

        public Criteria andAreaNotBetween(Long value1, Long value2) {
            addCriterion("area not between", value1, value2, "area");
            return this;


        }


    }


}
