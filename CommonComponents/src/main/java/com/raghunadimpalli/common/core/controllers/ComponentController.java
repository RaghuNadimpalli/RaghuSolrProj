package com.raghunadimpalli.common.core.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.raghunadimpalli.cc.core.components.DefaultComponentParams;
import com.raghunadimpalli.cc.core.constants.MVCContants;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.cc.core.exceptions.NoComponentBuilderFoundException;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderInterceptor;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderResolver;
import com.raghunadimpalli.common.core.abstracts.ComponentDataBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.core.abstracts.ComponentResponse;
import com.raghunadimpalli.common.core.abstracts.CrudComponentBuilder;
import com.raghunadimpalli.common.core.helpers.MVCComponentHelper;

@Controller
@RequestMapping(value={"*.html","*.pdf","*.xlsx"})
public class ComponentController {
	
	@Autowired
	private ComponentBuilderResolver componentBuilderResolver;
	
	@Autowired
	private ComponentBuilderInterceptor componentBuilderinterceptor;
	
	private static Logger log = Logger.getLogger(ComponentController.class);

	public ComponentBuilderResolver getComponentBuilderResolver() {
		return componentBuilderResolver;
	}

	public void setComponentBuilderResolver(
			ComponentBuilderResolver componentBuilderResolver) {
		this.componentBuilderResolver = componentBuilderResolver;
	}

	public ComponentBuilderInterceptor getComponentBuilderinterceptor() {
		return componentBuilderinterceptor;
	}

	public void setComponentBuilderinterceptor(
			ComponentBuilderInterceptor componentBuilderinterceptor) {
		this.componentBuilderinterceptor = componentBuilderinterceptor;
	}
	
	public ModelAndView defaultRequestHandler(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return null;
	}
	
	@RequestMapping(params = "reqHandler")
	public @ResponseBody ModelAndView requestHandler(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException{
		
		try
		{
			String componentId = MVCComponentHelper.getComponentId(request);
			ComponentParams params = new DefaultComponentParams(request,response,null);
			List data = null;
			boolean viewResult = false;
			ComponentDataBuilder dataBuilder = componentBuilderResolver
					.resolveDataBuilder(componentId, params);
			Object compData = PerformJsonToJavaConversion(dataBuilder,jsonData);
			params = new DefaultComponentParams(request, response, compData);
			if(dataBuilder instanceof ComponentDataBuilder){
				ComponentDataBuilder crudBuilder = (ComponentDataBuilder) dataBuilder;
				data = (List) crudBuilder.handleComponentRequests(params.getParameter("subCompId").toString(), params);
				if(data != null && data.size() == 1){
					if(data.get(0) instanceof ComponentResponse){
						return getModelMap((ComponentResponse)data.get(0));
					}
				}
			}
			
			if(data != null){
				viewResult = true;
			}
			
			return getModelMap(data,"BuildComponentData", viewResult);
		}
		catch(Exception e){
			if(log.isDebugEnabled()){
				log.debug("Exception while trying to retrieve the data "+e.getMessage());
				log.debug(e.getStackTrace());
			}
			e.printStackTrace();
			return getModelMapError("Error while trying to retrieve the data "+e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "exports",method=RequestMethod.GET)
	public List export(@RequestBody String jsonData,Model model,HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		long t1 = System.currentTimeMillis();
		List data = new ArrayList();
		try{
			if(log.isDebugEnabled()){
				log.debug("Component Controller - Started execution of view at "+t1);
			}
			String componentId = MVCComponentHelper.getComponentId(request);
			if(log.isInfoEnabled()){
				log.info("Json String data for the request "+jsonData);
			}
			Object compData = PerformJsonToJavaConversion(null,jsonData);
			ComponentParams params = new DefaultComponentParams(request,response,compData);
			
			if(log.isDebugEnabled()){
				log.debug("Retrieving builder for component id"+componentId);
			}
			
			ComponentDataBuilder dataBuilder = componentBuilderResolver.resolveDataBuilder(componentId, params);
			if(dataBuilder != null && dataBuilder instanceof ComponentDataBuilder){
				ComponentDataBuilder componentBuilder = (ComponentDataBuilder) dataBuilder;
				if(log.isDebugEnabled()){
					log.debug("Found builder for component Id "+componentId+" wuth class name as "+componentBuilder.getClass().getSimpleName());
				}
				data = (List) componentBuilder.buildComponentData(params);
				model.addAttribute(MVCContants.EXPORT_DATA.getValue(), data);
			}
			else{
				throw new NoComponentBuilderFoundException("Builder can't be located. Have you defined compId for the request ?");
			}
			
			if(log.isDebugEnabled())
				log.debug("ComponentController = Total time for populating view data "+(System.currentTimeMillis() - t1));
		}
		catch(ApplicationException e){
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping(params = "view", method=RequestMethod.POST)
	public @ResponseBody ModelAndView view(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		long t1 = System.currentTimeMillis();
		try{
			if(log.isDebugEnabled()){
				log.debug("ComponentController - Started execution of view at "+t1);
			}
			String componentId = MVCComponentHelper.getComponentId(request);
			if(log.isInfoEnabled()){
				log.info("Json String data for the request "+jsonData);
			}
			Object compData = PerformJsonToJavaConversion(null,jsonData);
			ComponentParams params = new DefaultComponentParams(request,response,compData);
			List data = null;
			List<Map<String, Object>> modelMaps = new ArrayList<Map<String,Object>>();
			boolean viewResult = false;
			if(log.isDebugEnabled()){
				log.debug("Retrieving builder for component id"+componentId);
			}
			
			ComponentDataBuilder dataBuilder = componentBuilderResolver.resolveDataBuilder(componentId, params);
			if(dataBuilder != null && dataBuilder instanceof ComponentDataBuilder){
				ComponentDataBuilder componentBuilder = (ComponentDataBuilder) dataBuilder;
				if(log.isDebugEnabled()){
					log.debug("Found builder for component Id "+componentId+" wuth class name as "+componentBuilder.getClass().getSimpleName());
				}
				data = (List) componentBuilder.buildComponentData(params);
			}
			else{
				throw new NoComponentBuilderFoundException("Builder can't be located. Have you defined compId for the request ?");
			}
			
			if(data != null){
				viewResult = true;
			}
			if(log.isDebugEnabled())
				log.debug("ComponentController = Total time for populating view data "+(System.currentTimeMillis() - t1));
			
			return getModelMap(data,modelMaps, "BuildComponentData",viewResult,params);
		}
		catch(IllegalArgumentException e){
			if(log.isDebugEnabled()) {
				log.debug("Exception while trying to perform json to java operation");
				log.debug(e.getStackTrace());
			}
			return getModelMapError(e.getMessage());
		}
		catch(ApplicationException e){
			if(log.isDebugEnabled()) {
				log.debug("Exception while trying to retrieve data");
				log.debug(e.getStackTrace());
			}
			e.printStackTrace();
			return getModelMapError("Error while trying to retrieve the data "+e.getMessage());
		}
	}
	
	@RequestMapping(params="create")
	public @ResponseBody ModelAndView create(@RequestBody String jsonData,@RequestBody String addData, HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		try{
			ComponentResponse createResult = null;
			ComponentParams params = null;
			String componentId = MVCComponentHelper.getComponentId(request);
			ComponentDataBuilder dataBuilder = componentBuilderResolver.resolveDataBuilder(componentId, params);
			if(dataBuilder instanceof CrudComponentBuilder){
				CrudComponentBuilder crudBuilder = (CrudComponentBuilder) dataBuilder;
				Object compData = PerformJsonToJavaConversion(crudBuilder,jsonData);
				params = new DefaultComponentParams(request,response,compData);
				Object addFormObject = PerformJsonToJavaConversion(crudBuilder,jsonData);
				createResult = crudBuilder.create(addFormObject, params);
			}
			return getModelMap(createResult);
		}
		catch(Exception e){
			e.printStackTrace();
			if(log.isDebugEnabled()){
				log.debug("Exception while performing insert operation");
				log.debug(e.getStackTrace());
			}
			return getModelMapError("Error while trying to perform create operation. "+e.getMessage());
		}
	}
	
	
	@RequestMapping(params="update")
	public @ResponseBody ModelAndView update(@RequestBody String jsonData, HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		try{
			ComponentResponse createResult = null;
			ComponentParams params = null;
			String componentId = MVCComponentHelper.getComponentId(request);
			ComponentDataBuilder dataBuilder = componentBuilderResolver.resolveDataBuilder(componentId, params);
			if(dataBuilder instanceof CrudComponentBuilder){
				CrudComponentBuilder crudBuilder = (CrudComponentBuilder) dataBuilder;
				Object compData = PerformJsonToJavaConversion(crudBuilder,jsonData);
				params = new DefaultComponentParams(request,response,compData);
				Object addFormObject = PerformJsonToJavaConversion(crudBuilder,jsonData);
				createResult = crudBuilder.update(addFormObject, params);
			}
			return getModelMap(createResult);
		}
		catch(Exception e){
			e.printStackTrace();
			if(log.isDebugEnabled()){
				log.debug("Exception while performing insert operation");
				log.debug(e.getStackTrace());
			}
			return getModelMapError("Error while trying to perform create operation. "+e.getMessage());
		}
	}
	
	@RequestMapping(params="delete")
	public @ResponseBody ModelAndView delete(@RequestBody String jsonData, HttpServletRequest request,
				HttpServletResponse response) throws ApplicationException{
		try{
			ComponentResponse createResult = null;
			ComponentParams params = null;
			String componentId = MVCComponentHelper.getComponentId(request);
			ComponentDataBuilder dataBuilder = componentBuilderResolver.resolveDataBuilder(componentId, params);
			if(dataBuilder instanceof CrudComponentBuilder){
				CrudComponentBuilder crudBuilder = (CrudComponentBuilder) dataBuilder;
				Object compData = PerformJsonToJavaConversion(crudBuilder,jsonData);
				params = new DefaultComponentParams(request,response,compData);
				Object addFormObject = PerformJsonToJavaConversion(crudBuilder,jsonData);
				createResult = crudBuilder.delete(addFormObject, params);
			}
			return getModelMap(createResult);
		}
		catch(ApplicationException e){
			e.printStackTrace();
			if(log.isDebugEnabled()){
				log.debug("Exception while performing insert operation");
				log.debug(e.getStackTrace());
			}
			return getModelMapError("Error while trying to perform create operation. "+e.getMessage());
		}
	}
	
	@RequestMapping(params="logout")
	public @ResponseBody ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException{
		return new ModelAndView("logout.jsp");
	}
	
	@SuppressWarnings("unchecked")
	private Object PerformJsonToJavaConversion(ComponentDataBuilder componentBuilder,String jsonData){
		Class clz = componentBuilder!=null ? componentBuilder.getComponentDataTransferObject() : Map.class;
		ObjectMapper mapper = new ObjectMapper();
		Object javaObject = new Object();
		try{
			if(jsonData!= null && jsonData.length() > 0 && jsonData.contains("&")){
				Map<String, String> map = new HashMap<String, String>();
				StringTokenizer st = new StringTokenizer(jsonData,"&");
				while(st.hasMoreElements()){
					String token = st.nextToken();
					int chopIndex = token.indexOf("=");
					map.put(token.substring(0,chopIndex),token.substring(chopIndex+1,token.length()));
				}
				javaObject = map;
				System.out.println(map);
			}
			else if(jsonData !=null && jsonData.length() > 0){
				javaObject = mapper.readValue(jsonData, clz);
				System.out.println(javaObject);
			}
		}
		catch(IOException e){
			throw new IllegalArgumentException("Couldn't parse json into componentDataParameters",e);
		}
		return javaObject;
	}
	
	private ModelAndView getModelMap(List data,String operation,boolean operationResult){
		if(data!=null && operationResult){
			ModelAndView modelMap = new ModelAndView();
			modelMap.setView(new MappingJacksonJsonView());
			modelMap.addObject("totalCount",data.size());
			modelMap.addObject("dataobj", data);
			modelMap.addObject("success", operationResult);
			modelMap.addObject("tableAction", "");
			modelMap.addObject("msg", operation+"  operation invoked successfully !!");
			return modelMap;
		}
		return getModelMapError("Error while trying to perform \""+operation+"\"operation !!");
	}
	
	private ModelAndView getModelMap(List data, List<Map<String,Object>> modelMaps,String operation,boolean operationResult,ComponentParams param){
		if(data!=null && operationResult){
			ModelAndView modelMap = new ModelAndView();
			modelMap.setView(new MappingJacksonJsonView());
			Long totalCount = param.getTotalCount()!=0 ? param.getTotalCount() : data.size();
			modelMap.addObject("totalCount",totalCount);
			modelMap.addObject("dataobj", data);
			modelMap.addObject("success", operationResult);
			modelMap.addObject("action", modelMaps);
			modelMap.addObject("msg", operation+"  operation invoked successfully !!");
			return modelMap;
		}
		return getModelMapError("Error while trying to perform \""+operation+"\"operation !!");
	}
	
	private ModelAndView getModelMap(ComponentResponse operationResult){
		if(operationResult!=null && operationResult.getExecutionStatus()){
			ModelAndView modelMap = new ModelAndView();
			modelMap.setView(new MappingJacksonJsonView());
			if(operationResult.isResponseAvailable()){
				modelMap.addObject("dataobj",operationResult.getResponseParameters());
			}
			modelMap.addObject("success", operationResult.getExecutionStatus());
			modelMap.addObject("message", operationResult.getExecutionMessage());
			return modelMap;
		}
		return getModelMapError(operationResult.getExecutionMessage());
	}
	
	private ModelAndView getModelMapError(String msg){
		ModelAndView modelMap = new ModelAndView();
		modelMap.setView(new MappingJacksonJsonView());
		modelMap.addObject("success", false);
		modelMap.addObject("message", msg);
		return modelMap;
	}
}
