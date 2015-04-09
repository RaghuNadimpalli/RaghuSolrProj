package com.raghunadimpalli.common.core.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.raghunadimpalli.cc.core.constants.MVCContants;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public class MVCComponentHelper {
	
	
	private static Logger log = Logger.getLogger(MVCComponentHelper.class);
	private static Map<String,String> userMap = new HashMap<String, String>();
	
	public static String getComponentId(HttpServletRequest request) throws RuntimeException{
		String componentId = request.getParameter(MVCContants.COMPONENT_ID.getValue());
		if(componentId == null){
			throw new RuntimeException("Component id can't be null");
		}
		return componentId;
	}
	
	public static String getActionServiceIdentifier(HttpServletRequest request) throws RuntimeException{
		return request.getParameter(MVCContants.ACTION_SERVICE_IDENTIFIER.getValue());
	}
	
	public static String getSubComponentId(HttpServletRequest request) throws RuntimeException{
		String componentId = request.getParameter(MVCContants.SUB_COMPONENT_ID.getValue());
		if(componentId == null){
			throw new RuntimeException("Subcomponent id can't be null");
		}
		return componentId;
	}
	
//	public static EXPORT getExportFormat(HttpServletRequest request) throws RuntimeException{
//		String exportFormat = request.getParameter(EXPORT.EXPORT_FORMAT.getValue());
//		if(exportFormat == null){
//			exportFormat = "excel";
//		}
//		exportFormat = exportFormat.toUpperCase();
//		
//		return EXPORT.valueOf(exportFormat);
//	}
//	
//	public static void main(String args[]){
//		System.out.println(EXPORT.valueOf("EXCEL"));
//	}
	
	public static String getRequestorId(HttpServletRequest request) throws RuntimeException{
		String requestId = request.getParameter(MVCContants.REQUESTOR_ID.getValue());
		return requestId;
	}
	
	public static void populateUserDetails(String userDetails) throws ApplicationException{
		StringTokenizer tokens = new StringTokenizer(userDetails,",");
		while(tokens.hasMoreElements()){
			String token = tokens.nextToken();
			int breakIndex = token.indexOf("||");
			if(breakIndex == -1){
				throw new ApplicationException("Invalid token found in "+token+" , please correct the user details");
			}	
			String mapKey = token.substring(0,breakIndex);
			String mapValue = token.substring(breakIndex+2, token.length());
			userMap.put(mapKey, mapValue);
		}
		if(log.isInfoEnabled()){
			log.info("User Crediential Map "+userMap);
		}
	}
	
	public static String getUserRole(String userName){
		return userMap.get(userName);
	}
}