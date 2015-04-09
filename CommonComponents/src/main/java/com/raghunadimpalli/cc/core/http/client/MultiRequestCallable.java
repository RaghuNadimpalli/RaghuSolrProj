package com.raghunadimpalli.cc.core.http.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class MultiRequestCallable {

	private final int CORE_POOL_SIZE = 4;
	private int MAX_POOL_SIZE = 8;
	private int KEEP_ALIVE_TIME = 5000;
	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private Map<String, Future<SolrXMLResponse>> responseList;
	private ExecutorService executorService;
	
	public MultiRequestCallable(CloseableHttpClient httpClient){
		this.executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		responseList = new ConcurrentHashMap<String, Future<SolrXMLResponse>>();
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
	}
	
	public void requestUrls(String[] urlsToCall){
		for(String url:urlsToCall){
			final HttpGet httpget = new HttpGet(url);
			Future<SolrXMLResponse> response = executorService.submit(new Callable<SolrXMLResponse>() {
				
				@Override
				public SolrXMLResponse call() throws Exception{
					CloseableHttpResponse response = httpClient.execute(httpget, context);
					SolrXMLResponse xmlResponse = new SolrXMLResponse();
					try
					{
						HttpEntity entity = response.getEntity();
						if(entity != null){
							//byte[] bytes = EntityUtils.toByteArray(entity);
							//System.out.println(id + " - "+bytes.length + " bytes read");
							InputStream in = entity.getContent();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							StringBuilder out = new StringBuilder();
							String line;
							while((line = reader.readLine()) != null){
								out.append(line);
							}
							String xmlResponseString = out.toString();
							xmlResponse.setXmlString(xmlResponseString);
							reader.close();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						response.close();
					}
					return xmlResponse;
				}
			});
			responseList.put(url, response);
		}
	}
	
	public Map<String, SolrXMLResponse> getResponseForUrls(){
		Iterator<Entry<String, Future<SolrXMLResponse>>> it = responseList.entrySet().iterator();
		Map<String, SolrXMLResponse> response = new HashMap<String, SolrXMLResponse>();
		try{
			while (it.hasNext()){
				Map.Entry pairs = (Map.Entry) it.next();
				Future<SolrXMLResponse> future = (Future) pairs.getValue();
				String key = pairs.getKey().toString();
				SolrXMLResponse value = future.get();
				response.put(key, value);
				System.out.print(key+" ******> " + future.get()+"\n\n\n\n");
			}
		}
		catch (InterruptedException | ExecutionException e){
			e.printStackTrace();
		}
		finally{
			this.executorService.shutdown();
		}
		return response;
	}
}