package com.rc.openapi.serviceuriV2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;

import com.rc.commons.security.BASE64Decoder;
import com.rc.openapi.service.TPrescriptionImageManager;
import com.rc.openapi.service.TPrescriptionManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TPrescription;
import com.rc.openapi.vo.TPrescriptionImage;

/**
 * 修改病例接口
 * 
 * @author user00
 * @createTime 2015-7-25 上午11:12:06
 */
public class EditPrescriptionURIService extends BaseURIService {

	private static final long serialVersionUID = 3810393917175631043L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "0";
		String message = "";
		TMemberManagerImpl man = (TMemberManagerImpl) getSpringBean("tmembermanager");
		TPrescriptionImageManager tPrescriptionImageManager = (TPrescriptionImageManager) getSpringBean("tprescriptionimagemanager");
		TPrescriptionManager tPrescriptionManager = (TPrescriptionManager) getSpringBean("tprescriptionmanager");
		try {
			String sessionid = request.getParameter("sessionid");
			String diseaseDescrip = request.getParameter("diseaseDescrip");// 病情描述
			String phone = request.getParameter("phone");
			String id = request.getParameter("id");
			List<String> imgDataList = new ArrayList<String>();
			if (id == null || "".equals(id)) {
				message = "病例ID不能为空";
			} else {
				for (int i = 1; i <= 9; i++) {
					if (request.getParameter("img" + (i)) != null && !"".equals(request.getParameter("img" + (i)))) {
						imgDataList.add(request.getParameter("img" + (i)));
					}
				}
				if (sessionid != null && !"".equals(sessionid.trim())) {
					long userId = man.getMemberId(sessionid);
					if (userId > 0) {// 存在
						List<String> imgUrl = new ArrayList<String>();
						if (imgDataList != null && imgDataList.size() > 0) {
							imgUrl = uploadImages(imgDataList);
						}
						TPrescription tPrescription = tPrescriptionManager.selectByPrimaryKey(Long.valueOf(id));
						if (tPrescription == null) {
							message = "请选择正确的 病例信息";
						} else {
							if (diseaseDescrip != null && !"".equals(diseaseDescrip)) {
								tPrescription.setDiseaseDescrip(diseaseDescrip);
							}
							if (phone != null && !"".equals(phone)) {
								tPrescription.setPhone(phone);
							}
							if (imgUrl != null & imgUrl.size() > 0) {
								tPrescription.setImageUrl(imgUrl.get(0));
							}
							tPrescription.setModifyDate(new Date());
							tPrescriptionManager.updatePrescription(tPrescription);
							TPrescriptionImage tPrescriptionImage = null;
							for (String string : imgUrl) {
								tPrescriptionImage = new TPrescriptionImage();
								tPrescriptionImage.setPath(string);
								tPrescriptionImage.setModifyDate(new Date());
								tPrescriptionImage.setPrescriptionId(tPrescription.getId());
								tPrescriptionImageManager.savePrescriptionImage(tPrescriptionImage);
							}
							statusCode = "1";
							message = "保存成功";
						}
					}
				} else {
					message = "请先登录";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		map.put("statusCode", statusCode);
		map.put("message", message);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	/**
	 * 上传图片
	 * 
	 * @param imgDataList
	 * @return
	 * @throws IOException
	 */
	private List<String> uploadImages(List<String> imgDataList) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
		BASE64Decoder decoder = new BASE64Decoder();
		List<String> returnPaths = new ArrayList<String>();
		for (int i = 0; i < imgDataList.size(); i++) {

			// 此处拼图片的路径
			String _subPath = sdf.format(new Date());
			String diskPath = InfoUtil.getInstance().getInfo("config", "image.prescription.path");
			String savePath = this.getServletContext().getRealPath(diskPath) + "/" + _subPath + "/";
			File f = new File(savePath);
			if (!f.exists() && !f.isDirectory()) {
				f.mkdirs();
			}
			String str = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis() + i)) + ".jpg";

			byte[] b = decoder.decodeBuffer(imgDataList.get(i));
			for (int j = 0; j < b.length; ++j) {
				if (b[j] < 0) {// 调整异常数据
					b[j] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(savePath + str);
			out.write(b);
			out.flush();
			out.close();
			returnPaths.add(diskPath + "/" + _subPath + "/" + str);
		}
		return returnPaths;
	}

	public static void main(String[] args) throws IOException {
		String string = "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";
		// 对字节数组字符串进行Base64解码并生成图片
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(string);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = "E:\\temp\\a.jpg";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
