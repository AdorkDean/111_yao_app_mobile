package com.rc.openapi.vo;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rc.app.framework.webapp.model.BaseModel;

public class TCategoryImgExample  extends BaseModel{

    protected String orderByClause;

    protected List oredCriteria;

    public TCategoryImgExample() {
        oredCriteria = new ArrayList();


    }

    protected TCategoryImgExample(TCategoryImgExample example) {
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

        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return this;


        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return this;


        }

        public Criteria andCategoryIdEqualTo(Long value) {
            addCriterion("category_id =", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdNotEqualTo(Long value) {
            addCriterion("category_id <>", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdGreaterThan(Long value) {
            addCriterion("category_id >", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("category_id >=", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdLessThan(Long value) {
            addCriterion("category_id <", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdLessThanOrEqualTo(Long value) {
            addCriterion("category_id <=", value, "categoryId");
            return this;


        }

        public Criteria andCategoryIdIn(List values) {
            addCriterion("category_id in", values, "categoryId");
            return this;


        }

        public Criteria andCategoryIdNotIn(List values) {
            addCriterion("category_id not in", values, "categoryId");
            return this;


        }

        public Criteria andCategoryIdBetween(Long value1, Long value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return this;


        }

        public Criteria andCategoryIdNotBetween(Long value1, Long value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return this;


        }

        public Criteria andImgUrlIsNull() {
            addCriterion("img_url is null");
            return this;


        }

        public Criteria andImgUrlIsNotNull() {
            addCriterion("img_url is not null");
            return this;


        }

        public Criteria andImgUrlEqualTo(String value) {
            addCriterion("img_url =", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlNotEqualTo(String value) {
            addCriterion("img_url <>", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlGreaterThan(String value) {
            addCriterion("img_url >", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("img_url >=", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlLessThan(String value) {
            addCriterion("img_url <", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlLessThanOrEqualTo(String value) {
            addCriterion("img_url <=", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlLike(String value) {
            addCriterion("img_url like", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlNotLike(String value) {
            addCriterion("img_url not like", value, "imgUrl");
            return this;


        }

        public Criteria andImgUrlIn(List values) {
            addCriterion("img_url in", values, "imgUrl");
            return this;


        }

        public Criteria andImgUrlNotIn(List values) {
            addCriterion("img_url not in", values, "imgUrl");
            return this;


        }

        public Criteria andImgUrlBetween(String value1, String value2) {
            addCriterion("img_url between", value1, value2, "imgUrl");
            return this;


        }

        public Criteria andImgUrlNotBetween(String value1, String value2) {
            addCriterion("img_url not between", value1, value2, "imgUrl");
            return this;


        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return this;


        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return this;


        }

        public Criteria andIsShowEqualTo(Integer value) {
            addCriterion("is_show =", value, "isShow");
            return this;


        }

        public Criteria andIsShowNotEqualTo(Integer value) {
            addCriterion("is_show <>", value, "isShow");
            return this;


        }

        public Criteria andIsShowGreaterThan(Integer value) {
            addCriterion("is_show >", value, "isShow");
            return this;


        }

        public Criteria andIsShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_show >=", value, "isShow");
            return this;


        }

        public Criteria andIsShowLessThan(Integer value) {
            addCriterion("is_show <", value, "isShow");
            return this;


        }

        public Criteria andIsShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_show <=", value, "isShow");
            return this;


        }

        public Criteria andIsShowIn(List values) {
            addCriterion("is_show in", values, "isShow");
            return this;


        }

        public Criteria andIsShowNotIn(List values) {
            addCriterion("is_show not in", values, "isShow");
            return this;


        }

        public Criteria andIsShowBetween(Integer value1, Integer value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return this;


        }

        public Criteria andIsShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return this;


        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return this;


        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return this;


        }

        public Criteria andIsDeleteEqualTo(Integer value) {
            addCriterion("is_delete =", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteNotEqualTo(Integer value) {
            addCriterion("is_delete <>", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteGreaterThan(Integer value) {
            addCriterion("is_delete >", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_delete >=", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteLessThan(Integer value) {
            addCriterion("is_delete <", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteLessThanOrEqualTo(Integer value) {
            addCriterion("is_delete <=", value, "isDelete");
            return this;


        }

        public Criteria andIsDeleteIn(List values) {
            addCriterion("is_delete in", values, "isDelete");
            return this;


        }

        public Criteria andIsDeleteNotIn(List values) {
            addCriterion("is_delete not in", values, "isDelete");
            return this;


        }

        public Criteria andIsDeleteBetween(Integer value1, Integer value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return this;


        }

        public Criteria andIsDeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return this;


        }

        public Criteria andIsWapIsNull() {
            addCriterion("is_wap is null");
            return this;


        }

        public Criteria andIsWapIsNotNull() {
            addCriterion("is_wap is not null");
            return this;


        }

        public Criteria andIsWapEqualTo(Integer value) {
            addCriterion("is_wap =", value, "isWap");
            return this;


        }

        public Criteria andIsWapNotEqualTo(Integer value) {
            addCriterion("is_wap <>", value, "isWap");
            return this;


        }

        public Criteria andIsWapGreaterThan(Integer value) {
            addCriterion("is_wap >", value, "isWap");
            return this;


        }

        public Criteria andIsWapGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_wap >=", value, "isWap");
            return this;


        }

        public Criteria andIsWapLessThan(Integer value) {
            addCriterion("is_wap <", value, "isWap");
            return this;


        }

        public Criteria andIsWapLessThanOrEqualTo(Integer value) {
            addCriterion("is_wap <=", value, "isWap");
            return this;


        }

        public Criteria andIsWapIn(List values) {
            addCriterion("is_wap in", values, "isWap");
            return this;


        }

        public Criteria andIsWapNotIn(List values) {
            addCriterion("is_wap not in", values, "isWap");
            return this;


        }

        public Criteria andIsWapBetween(Integer value1, Integer value2) {
            addCriterion("is_wap between", value1, value2, "isWap");
            return this;


        }

        public Criteria andIsWapNotBetween(Integer value1, Integer value2) {
            addCriterion("is_wap not between", value1, value2, "isWap");
            return this;


        }

        public Criteria andIsAppIsNull() {
            addCriterion("is_app is null");
            return this;


        }

        public Criteria andIsAppIsNotNull() {
            addCriterion("is_app is not null");
            return this;


        }

        public Criteria andIsAppEqualTo(Integer value) {
            addCriterion("is_app =", value, "isApp");
            return this;


        }

        public Criteria andIsAppNotEqualTo(Integer value) {
            addCriterion("is_app <>", value, "isApp");
            return this;


        }

        public Criteria andIsAppGreaterThan(Integer value) {
            addCriterion("is_app >", value, "isApp");
            return this;


        }

        public Criteria andIsAppGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_app >=", value, "isApp");
            return this;


        }

        public Criteria andIsAppLessThan(Integer value) {
            addCriterion("is_app <", value, "isApp");
            return this;


        }

        public Criteria andIsAppLessThanOrEqualTo(Integer value) {
            addCriterion("is_app <=", value, "isApp");
            return this;


        }

        public Criteria andIsAppIn(List values) {
            addCriterion("is_app in", values, "isApp");
            return this;


        }

        public Criteria andIsAppNotIn(List values) {
            addCriterion("is_app not in", values, "isApp");
            return this;


        }

        public Criteria andIsAppBetween(Integer value1, Integer value2) {
            addCriterion("is_app between", value1, value2, "isApp");
            return this;


        }

        public Criteria andIsAppNotBetween(Integer value1, Integer value2) {
            addCriterion("is_app not between", value1, value2, "isApp");
            return this;


        }

        public Criteria andIsPcIsNull() {
            addCriterion("is_pc is null");
            return this;


        }

        public Criteria andIsPcIsNotNull() {
            addCriterion("is_pc is not null");
            return this;


        }

        public Criteria andIsPcEqualTo(Integer value) {
            addCriterion("is_pc =", value, "isPc");
            return this;


        }

        public Criteria andIsPcNotEqualTo(Integer value) {
            addCriterion("is_pc <>", value, "isPc");
            return this;


        }

        public Criteria andIsPcGreaterThan(Integer value) {
            addCriterion("is_pc >", value, "isPc");
            return this;


        }

        public Criteria andIsPcGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_pc >=", value, "isPc");
            return this;


        }

        public Criteria andIsPcLessThan(Integer value) {
            addCriterion("is_pc <", value, "isPc");
            return this;


        }

        public Criteria andIsPcLessThanOrEqualTo(Integer value) {
            addCriterion("is_pc <=", value, "isPc");
            return this;


        }

        public Criteria andIsPcIn(List values) {
            addCriterion("is_pc in", values, "isPc");
            return this;


        }

        public Criteria andIsPcNotIn(List values) {
            addCriterion("is_pc not in", values, "isPc");
            return this;


        }

        public Criteria andIsPcBetween(Integer value1, Integer value2) {
            addCriterion("is_pc between", value1, value2, "isPc");
            return this;


        }

        public Criteria andIsPcNotBetween(Integer value1, Integer value2) {
            addCriterion("is_pc not between", value1, value2, "isPc");
            return this;


        }

        public Criteria andCreateDtIsNull() {
            addCriterion("create_dt is null");
            return this;


        }

        public Criteria andCreateDtIsNotNull() {
            addCriterion("create_dt is not null");
            return this;


        }

        public Criteria andCreateDtEqualTo(Date value) {
            addCriterion("create_dt =", value, "createDt");
            return this;


        }

        public Criteria andCreateDtNotEqualTo(Date value) {
            addCriterion("create_dt <>", value, "createDt");
            return this;


        }

        public Criteria andCreateDtGreaterThan(Date value) {
            addCriterion("create_dt >", value, "createDt");
            return this;


        }

        public Criteria andCreateDtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_dt >=", value, "createDt");
            return this;


        }

        public Criteria andCreateDtLessThan(Date value) {
            addCriterion("create_dt <", value, "createDt");
            return this;


        }

        public Criteria andCreateDtLessThanOrEqualTo(Date value) {
            addCriterion("create_dt <=", value, "createDt");
            return this;


        }

        public Criteria andCreateDtIn(List values) {
            addCriterion("create_dt in", values, "createDt");
            return this;


        }

        public Criteria andCreateDtNotIn(List values) {
            addCriterion("create_dt not in", values, "createDt");
            return this;


        }

        public Criteria andCreateDtBetween(Date value1, Date value2) {
            addCriterion("create_dt between", value1, value2, "createDt");
            return this;


        }

        public Criteria andCreateDtNotBetween(Date value1, Date value2) {
            addCriterion("create_dt not between", value1, value2, "createDt");
            return this;


        }


    }


}
