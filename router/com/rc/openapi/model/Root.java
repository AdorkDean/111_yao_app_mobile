package com.rc.openapi.model;

import java.util.List;

public class Root {
	private List<Parnter> parnters;
	private List<Method> methods;

	public List<Parnter> getParnters() {
		return parnters;
	}

	public void setParnters(List<Parnter> parnters) {
		this.parnters = parnters;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	/**
	 * 取秘钥
	 * 
	 * @param id
	 * @return
	 */
	public String getParnterid(String id) {
		String key = "";
		for (Parnter parnter : parnters) {
			if (id != null && id.equals(parnter.getId())) {
				key = parnter.getKey();
			}
		}
		return key;
	}

	/**
	 * 取url
	 * 
	 * @param id
	 * @return
	 */
	public String getMethod(String name) {
		String uri = "";
		for (Method method : methods) {
			if (name != null && name.equals(method.getName())) {
				uri = method.getUri();
			}
		}
		return uri;
	}

}
