package com.rc.openapi.serviceuriV5.receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.bean.BaseResponse;
import com.rc.openapi.service.CLocationCityManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.StringIsNumericUtil;
import com.rc.openapi.util.StringUtil;
import com.rc.openapi.vo.CLocationCity;
import com.rc.openapi.vo.CLocationCityExample;

/**
 * 
 * 修改收货地址  手动 和地图定位
 *
 */
public class UpdateReceiverAddressURIService extends BaseURIService{
	private static final long serialVersionUID = -8834901444162635401L;
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statusCode = "0";
		String errorMessage = "服务器繁忙,请稍后重试";
		boolean checkFlag = true;
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");// 收货地址id
		try {
			String sessionId = request.getParameter("sessionId");// 会员sessionId
			String mobile = request.getParameter("mobile");// 手机号
			String zipCode = request.getParameter("zipCode");// 邮编
			String receiver = request.getParameter("receiver");// 收货人
			String isDefault = request.getParameter("isDefault");// 是否默认1:是;0:否
			String citycode = request.getParameter("citycode");// 地区编码
			String cityName= request.getParameter("cityName");//城市地区名称
			String longitude = request.getParameter("longitude");// 经度
			String latitude = request.getParameter("latitude");// 纬度
			String locationAddress = request.getParameter("locationAddress");// 定位地址
			String address = request.getParameter("address");// 详细地址
			String areaId = request.getParameter("areaId");// 地区ID  手动添加收获地址 需要传递该值
			Map<String, String> publicMap = new HashMap<String, String>();



//-----------added by ws on 2016-6-21------------------
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String homePageConfigId = sys.getKeys("public_service_url");
//			if( StringUtil.isNull(longitude) || StringUtil.isNull( latitude ) || StringUtil.isNull( locationAddress ) )
//			{
				longitude = "0.0";
				latitude = "0.0";
				locationAddress = cityName.split("-")[cityName.split("-").length-1];
				try {
					Map parasM = new HashMap<String, String>();
					parasM.put("area", cityName);
					parasM.put("locationAddress", "");
					parasM.put("address", address);
					String locationRes = ClientSubmitPublic.getPublicService(parasM, homePageConfigId + "aMapGeoURIService");
					log.info("[geoLocation] geo locationRes:" + locationRes );

					BaseResponse baseResponse = ConstantUtil.fromJson(locationRes, BaseResponse.class);
					if (null != baseResponse) {
						MorphDynaBean morphDynaBean = (MorphDynaBean)baseResponse.getResult();

						String location = StringUtil.transferStr( morphDynaBean.get("location") );
						if( !StringUtil.isNull( location ) )
						{
							String[] locationArrs =  location.split(",");
							if( null != locationArrs && locationArrs.length >= 2 )
							{
								longitude = locationArrs[0];
								latitude = locationArrs[1];
								log.info("[geoLocation] geo longitude:" + longitude );
								log.info("[geoLocation] geo latitude:" + latitude );

							}
						}

					}

				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
				}
//---------- /added by ws on 2016-6-21------------------

			if(!StringUtils.hasText(id)){
				errorMessage = "收货地址信息不存在";
				checkFlag= false;
			}else{
				publicMap.put("id", id);
			}
			if(checkFlag&&!StringUtils.hasText(sessionId)){
				errorMessage = "请重新登陆";
				checkFlag= false;
			}else{
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long memberId = tmembermanager.getMemberId(sessionId);
				if(memberId!=null&&memberId.longValue()>0){
					publicMap.put("member_id", String.valueOf(memberId));
				}
			}
			if(checkFlag&&!StringUtils.hasText(mobile)){
				errorMessage = "请填写手机号";
				checkFlag= false;
			}else{
				publicMap.put("mobile", mobile);
			}

			if(checkFlag&&!StringUtils.hasText(receiver)){
				errorMessage = "请填写收货人";
				checkFlag= false;
			}else{
				publicMap.put("receiver", receiver);
			}
			
			if (StringUtils.hasText(isDefault) && StringIsNumericUtil.isNumeric(isDefault)
					&& Integer.valueOf(isDefault) == 1) {
				publicMap.put("isDefault", isDefault);
			} else {
				publicMap.put("isDefault", "0");
			}
			publicMap.put("longitude", longitude);
			publicMap.put("latitude", latitude);
			
			if(StringUtils.hasText(locationAddress)){
				publicMap.put("locationAddress", locationAddress);
			}
			
			
			if(checkFlag&&!StringUtils.hasText(address)){
				errorMessage = "请填写详细地址";
				checkFlag= false;
			}else{
				publicMap.put("address", address);
			}
			publicMap.put("zipCode", zipCode);

			if(checkFlag&&!StringUtils.hasText(citycode)){
				errorMessage = "请完善城市地区信息";
				checkFlag = false;
			}else{
				CLocationCityManager cLocationCityManager = (CLocationCityManager) getSpringBean("clocationcitymanager");
				if(StringUtils.hasText(longitude)&&StringUtils.hasText(latitude)){
					if(!StringUtils.hasText(cityName)){
						errorMessage = "请完善城市地区信息";
						checkFlag = false;
					}
					if(checkFlag){
						publicMap.put("cityName", cityName);
						CLocationCityExample cLocationCityExample = new CLocationCityExample();
						cLocationCityExample.createCriteria().andCitycodeEqualTo(citycode);
						cLocationCityExample.setOrderByClause(" parentid desc");
						List<CLocationCity> cityList = cLocationCityManager.selectByExample(cLocationCityExample);
						if(cityList!=null&&cityList.size()>0){
							publicMap.put("areaId", String.valueOf(cityList.get(0).getId()));
						}else{
							errorMessage = "请选择城市";
							checkFlag = false;
						}
					}
					
				}else{
					if(!StringUtils.hasText(areaId)){
						errorMessage = "请选择所在区县";
						checkFlag = false;
					}else{
						publicMap.put("cityName", cLocationCityManager.selectAreaName(Long.valueOf(areaId)));
						publicMap.put("areaId", areaId);
					}
				}
			}
			publicMap.put("lonlatToAddressFlag", "1");// 是否根据经纬度 反解析地区信息  1表示否   其他表示不用
			if(checkFlag){
				String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+ "updateReceiverAddress");
				JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
				if (jsonObject != null && jsonObject.get("statusCode") != null && jsonObject.get("statusCode").equals("1")) {
					statusCode = "1";// 成功
					errorMessage = "提交成功";
				} else {
					statusCode = "0";// 失败
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("id", id);
		result.put("statusCode", statusCode);
		result.put("errorMessage", errorMessage);
		JSONObject json = JSONObject.fromObject(result);
		ConstantUtil.reJSON(json.toString(), request, response);
		return;
	}
	
	
}
