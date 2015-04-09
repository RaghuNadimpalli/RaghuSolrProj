package com.raghunadimpalli.cc.core.http.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class ClientMultiThreadedExecution {

	public static void main(String[] args) throws Exception{
		
		//Create an HttpClient with the ThreadSafeClientConnManager.
		//This Connection manager must be used if more than one thread will be using the HttpClient
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm)
				.build();
		try
		{
			//create an array of URIs to perform GETs on
			String[] urlList = {
					"http://localhost:8085/CommonComponents/data.html?view=true&compId=mvc.custinfo",
					"http://localhost:8085/CommonComponents/data.html?view=true&compId=mvc.custinfo2",
					"http://localhost:8085/CommonComponents/data.html?view=true&compId=mvc.custinfo4",
			};
			
			MultiRequestCallable multiRequestCallable = new MultiRequestCallable(httpClient);
			multiRequestCallable.requestUrls(urlList);
			multiRequestCallable.getResponseForUrls();
		}
		finally
		{
			httpClient.close();
		}

	}

}
