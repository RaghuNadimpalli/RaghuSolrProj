package com.raghunadimpalli.cc.core.components;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.raghunadimpalli.cc.core.constants.MVCContants;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;

public class DefaultComponentParams implements ComponentParams {
	
	private Long totalCount;
	private Map<String, Object> componentMap = new HashMap<String, Object>();
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	
	public DefaultComponentParams(){
		
	}
	
	public DefaultComponentParams(HttpServletRequest req, HttpServletResponse res, Object jsonData){
		@SuppressWarnings("unchecked")
		Enumeration<String> en = req.getParameterNames();
		while(en.hasMoreElements()){
			String key = en.nextElement();
			String value = req.getParameter(key);
			componentMap.put(key, value);
		}
		Enumeration<String> enAttr = req.getAttributeNames();
		while(enAttr.hasMoreElements()){
			String key = enAttr.nextElement();
			Object value = (Object) req.getAttribute(key);
			componentMap.put(key, value);
		}
		HttpSession session = req.getSession(true);
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = session.getAttributeNames();
		while(attributeNames.hasMoreElements()){
			String key = String.valueOf(attributeNames.nextElement());
			Object value = session.getAttribute(key);
			componentMap.put(key, value);
		}
		if(jsonData!=null){
			componentMap.put(MVCContants.HTTP_PAYLOAD.getValue(), jsonData);
		}
	}

	@Override
	public String getParameter(String key) {
		if(componentMap.get(key)!=null)
			return (String) componentMap.get(key);
		return null;
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParamData(String key) {
		return this.paramMap.get(key);
	}

	@Override
	public void setParamData(String key, Object Value) {
		this.paramMap.put(key, Value);
	}

	@Override
	public Object getHttpPayloadData() {
		return this.componentMap.get(MVCContants.HTTP_PAYLOAD.getValue());
	}

	@Override
	public Long getTotalCount() {
		return this.totalCount == null ? 0:this.totalCount;
	}

	@Override
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;

	}

}
