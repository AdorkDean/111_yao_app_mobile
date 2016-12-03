package com.rc.openapi.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rc.openapi.dao.TMemberAccountDAO;
import com.rc.openapi.dao.TMemberDAO;
import com.rc.openapi.dao.TMemberSessionDAO;
import com.rc.openapi.dao.TMemberThreeBindingDAO;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.MD5;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberAccount;
import com.rc.openapi.vo.TMemberExample;
import com.rc.openapi.vo.TMemberSession;
import com.rc.openapi.vo.TMemberSessionExample;
import com.rc.openapi.vo.TMemberThreeBinding;

public class TMemberManagerImpl  implements TMemberManager {

    private TMemberDAO tmemberdao;
    private TMemberSessionDAO tmembersessiondao;
    private TMemberAccountDAO tmemberaccountdao;
    private TMemberThreeBindingDAO tmemberthreebindingdao;
    
    public void  setTmemberthreebindingdao(TMemberThreeBindingDAO tmemberthreebindingdao){
        this.tmemberthreebindingdao=tmemberthreebindingdao;
    }
    public TMemberThreeBindingDAO getTmemberthreebindingdao(){
        return this.tmemberthreebindingdao;
    }
    public void  setTmemberaccountdao(TMemberAccountDAO tmemberaccountdao){
        this.tmemberaccountdao=tmemberaccountdao;
    }
    public TMemberAccountDAO getTmemberaccountdao(){
        return this.tmemberaccountdao;
    }
    public TMemberSessionDAO getTmembersessiondao() {
		return tmembersessiondao;
	}
	public void setTmembersessiondao(TMemberSessionDAO tmembersessiondao) {
		this.tmembersessiondao = tmembersessiondao;
	}
	public TMemberManagerImpl() {
        super();
    }
    public void  setTmemberdao(TMemberDAO tmemberdao){
        this.tmemberdao=tmemberdao;
    }
    public TMemberDAO getTmemberdao(){
        return this.tmemberdao;
    }
    public     int countByExample(TMemberExample example) throws SQLException{
        return tmemberdao. countByExample( example);
    }

    public     int deleteByExample(TMemberExample example) throws SQLException{
        return tmemberdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tmemberdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TMember record) throws SQLException{
        return tmemberdao. insert( record);
    }

    public     Long insertSelective(TMember record) throws SQLException{
        return tmemberdao. insertSelective( record);
    }

    public     List selectByExample(TMemberExample example) throws SQLException{
        return tmemberdao. selectByExample( example);
    }

    public     TMember selectByPrimaryKey(Long id) throws SQLException{
        return tmemberdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TMember record, TMemberExample example) throws SQLException{
        return tmemberdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TMember record, TMemberExample example) throws SQLException{
        return tmemberdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TMember record) throws SQLException{
        return tmemberdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TMember record) throws SQLException{
        return tmemberdao. updateByPrimaryKey( record);
    }
	public Long getMemberId(String session) throws SQLException {
		TMemberSessionExample dms = new TMemberSessionExample();
		dms.createCriteria().andSessionidEqualTo(session);
		List list = tmembersessiondao.selectByExample(dms);
		if(null != list && list.size()==1){
			TMemberSession ds = (TMemberSession) list.get(0); 
			return ds.getUserid();
		}
		return -1l;
	}
	
	//用户注册
	public Long insertSelectiveAndSession(TMember member, Map map)
			throws SQLException {

		Long id = tmemberdao.insertSelective(member);  //添加用户
		TMemberAccount account = new TMemberAccount();
		account.setMemberId(id);
		account.setFreezeAmount(new BigDecimal(0));
		account.setRemainingAmount(new BigDecimal(0));
		account.setTotalAmount(new BigDecimal(0));
		account.setWaitAmount(new BigDecimal(0));
		tmemberaccountdao.insertSelective(account);  //添加用户资金表
		TMemberSession memberSession = new TMemberSession();
		memberSession.setUserid(id);
		Long time = member.getRegisterDate().getTime();
		String sessionId = MD5.MD5(time.toString()+id.toString());
		memberSession.setSessionid(sessionId);
		tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
		map.put("sessionId", sessionId);
		return id;
	}
	
	
	//用户登录
	public int updateMemberToSession(TMember member, TMemberExample example,
			Map map) throws SQLException {
		TMember mb = new TMember();
		mb.setLastDate(new Date());
		mb.setLastIp(member.getLastIp());
		tmemberdao.updateByExampleSelective(mb, example);
		TMemberSessionExample  tms = new TMemberSessionExample();
		tms.createCriteria().andUseridEqualTo(member.getId());
	    List list =	tmembersessiondao.selectByExample(tms);
	    if(null != list && list.size()==1){
	    	TMemberSession memberSession = (TMemberSession) list.get(0);
	    	map.put("sessionId", memberSession.getSessionid());
	    }else{
	    	TMemberSession memberSession = new TMemberSession();
			Long time = member.getRegisterDate().getTime();
			String sessionId = MD5.MD5(time.toString()+member.getId());
			memberSession.setSessionid(sessionId);
			memberSession.setUserid(member.getId());
			tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
			map.put("sessionId", sessionId);
	    }
		return 0;
	}
	
	//用户登录
		public int updateMember(TMember member, TMemberExample example,
				Map map) throws SQLException {
			TMember mb = new TMember();
			mb.setLastDate(new Date());
			mb.setLastIp(member.getLastIp());
			mb.setPassword(member.getPassword());
			tmemberdao.updateByExampleSelective(mb, example);
			TMemberSessionExample  tms = new TMemberSessionExample();
			tms.createCriteria().andUseridEqualTo(member.getId());
		    List list =	tmembersessiondao.selectByExample(tms);
		    if(null != list && list.size()==1){
		    	TMemberSession memberSession = (TMemberSession) list.get(0);
		    	map.put("sessionId", memberSession.getSessionid());
		    }else{
		    	TMemberSession memberSession = new TMemberSession();
				Long time = member.getRegisterDate().getTime();
				String sessionId = MD5.MD5(time.toString()+member.getId());
				memberSession.setSessionid(sessionId);
				memberSession.setUserid(member.getId());
				tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
				map.put("sessionId", sessionId);
		    }
			return 0;
		}
		
	    //WX登录
		public int updateWXMember(TMember member, TMemberExample example,
				Map map,String imgUrl) throws SQLException {
			TMember mb = new TMember();
			mb.setLastDate(new Date());
			mb.setLastIp(member.getLastIp());
			if (null != imgUrl && !("").equals(imgUrl)) {
				if(null != member.getHeadPortrait() && !("").equals(member.getHeadPortrait())){
					if(!imgUrl.equals(member.getHeadPortrait())){
						mb.setHeadPortrait(imgUrl);
					}
				}else{
					mb.setHeadPortrait(imgUrl);
				}
			}
			tmemberdao.updateByExampleSelective(mb, example);
			TMemberSessionExample  tms = new TMemberSessionExample();
			tms.createCriteria().andUseridEqualTo(member.getId());
		    List list =	tmembersessiondao.selectByExample(tms);
		    if(null != list && list.size()==1){
		    	TMemberSession memberSession = (TMemberSession) list.get(0);
		    	map.put("sessionId", memberSession.getSessionid());
		    }else{
		    	TMemberSession memberSession = new TMemberSession();
				Long time = member.getRegisterDate().getTime();
				String sessionId = MD5.MD5(time.toString()+member.getId());
				memberSession.setSessionid(sessionId);
				memberSession.setUserid(member.getId());
				tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
				map.put("sessionId", sessionId);
		    }
			return 0;
		}
	@Override
	public int updateMemberPassword(TMember member, TMemberExample example,
			Map map) throws SQLException {
		TMember mb = new TMember();
		mb.setPassword(member.getPassword());
		int i = tmemberdao.updateByExampleSelective(mb,example);
		if(i>0){
			TMemberSessionExample  tms = new TMemberSessionExample();
			tms.createCriteria().andUseridEqualTo(member.getId());
		    List list =	tmembersessiondao.selectByExample(tms);
		    if(null != list && list.size()==1){
		    	TMemberSession memberSession = (TMemberSession) list.get(0);
		    	map.put("sessionId", memberSession.getSessionid());
		    }else{
		    	TMemberSession memberSession = new TMemberSession();
				Long time = member.getRegisterDate().getTime();
				String sessionId = MD5.MD5(time.toString()+member.getId());
				memberSession.setUserid(member.getId());
				memberSession.setSessionid(sessionId);
				tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
				map.put("sessionId", sessionId);
		    }
		    map.put("statusCode", "1");
		}else{
			map.put("statusCode", "5");
		}
		return 0;
	}
    //第一次联合登录
	public Long insertMemberBinding(TMember member, Map map,String uuid)
			throws SQLException {
		Long id = tmemberdao.insertSelective(member);  //添加用户
		TMemberThreeBinding binding = new TMemberThreeBinding();
		binding.setBindingUuid(uuid);
		binding.setCreateDate(new Date());
		binding.setMemberId(id);
		binding.setSource(0);
		tmemberthreebindingdao.insertSelective(binding);
		TMemberAccount account = new TMemberAccount();
		account.setMemberId(id);
		account.setFreezeAmount(new BigDecimal(0));
		account.setRemainingAmount(new BigDecimal(0));
		account.setTotalAmount(new BigDecimal(0));
		account.setWaitAmount(new BigDecimal(0));
		tmemberaccountdao.insertSelective(account);  //添加用户资金表
		TMemberSession memberSession = new TMemberSession();
		memberSession.setUserid(id);
		Long time = member.getRegisterDate().getTime();
		String sessionId = MD5.MD5(time.toString()+id.toString());
		memberSession.setSessionid(sessionId);
		tmembersessiondao.insertSelective(memberSession);  //添加APP登录session凭证
		map.put("sessionId", sessionId);
		return id;
	}


}
