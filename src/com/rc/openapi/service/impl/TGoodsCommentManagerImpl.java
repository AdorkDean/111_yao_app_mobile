package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TGoodsCommentDAO;
import com.rc.openapi.service.TGoodsCommentManager;
import com.rc.openapi.vo.TGoodsComment;
import com.rc.openapi.vo.TGoodsCommentExample;

public class TGoodsCommentManagerImpl  implements TGoodsCommentManager {

    private TGoodsCommentDAO tgoodscommentdao;

    public TGoodsCommentManagerImpl() {
        super();
    }
    public void  setTgoodscommentdao(TGoodsCommentDAO tgoodscommentdao){
        this.tgoodscommentdao=tgoodscommentdao;
    }
    public TGoodsCommentDAO getTgoodscommentdao(){
        return this.tgoodscommentdao;
    }
    public     int countByExample(TGoodsCommentExample example) throws SQLException{
        return tgoodscommentdao. countByExample( example);
    }

    public     int deleteByExample(TGoodsCommentExample example) throws SQLException{
        return tgoodscommentdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tgoodscommentdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TGoodsComment record) throws SQLException{
        return tgoodscommentdao. insert( record);
    }

    public     Long insertSelective(TGoodsComment record) throws SQLException{
        return tgoodscommentdao. insertSelective( record);
    }

    public     List selectByExample(TGoodsCommentExample example) throws SQLException{
        return tgoodscommentdao. selectByExample( example);
    }

    public     TGoodsComment selectByPrimaryKey(Long id) throws SQLException{
        return tgoodscommentdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TGoodsComment record, TGoodsCommentExample example) throws SQLException{
        return tgoodscommentdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TGoodsComment record, TGoodsCommentExample example) throws SQLException{
        return tgoodscommentdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TGoodsComment record) throws SQLException{
        return tgoodscommentdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TGoodsComment record) throws SQLException{
        return tgoodscommentdao. updateByPrimaryKey( record);
    }


}
