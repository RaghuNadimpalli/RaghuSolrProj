package com.raghunadimpalli.core.solr.impl;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.raghunadimpalli.core.solr.PersonSolr;
import com.raghunadimpalli.core.solr.SolrServerInitializer;
import com.raghunadimpalli.core.vo.PersonExtendedVO;


public class PersonSolrImpl implements PersonSolr {

	@Autowired
	public SolrServerInitializer solrServerInitializer;
	
	private SolrQuery query;
	
	private SolrServer solrServer;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("$personSolrInst{personRecordCount}")
	private String sqlRecordCount;
	
	private String sqlAllRecords;
	
	public SolrQuery getQuery() {
		return query;
	}

	public void setQuery(SolrQuery query) {
		this.query = query;
	}

	public SolrServer getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getSqlRecordCount() {
		return sqlRecordCount;
	}

	public void setSqlRecordCount(String sqlRecordCount) {
		this.sqlRecordCount = sqlRecordCount;
	}

	public String getSqlAllRecords() {
		return sqlAllRecords;
	}

	public void setSqlAllRecords(String sqlAllRecords) {
		this.sqlAllRecords = sqlAllRecords;
	}

	public SolrServerInitializer getSolrServerInitializer() {
		return solrServerInitializer;
	}

	public void setDatasource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSolrServerInitializer(SolrServerInitializer solrServerInitializer) {
		try{
			this.solrServerInitializer = solrServerInitializer;
			this.solrServer = solrServerInitializer.getPersonSolrInstance();
		}
		catch(Exception e){
			throw new RuntimeException();
		}
	}
	
	public static boolean set(PersonExtendedVO object,String fieldName, String fieldValue) {
		Class<?> clazz = object.getClass();
		while(clazz != null){
			try{
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			}
			catch(NoSuchFieldException e){
				clazz = clazz.getSuperclass();
			}
			catch(Exception e){
				throw new IllegalStateException(e);
			}
		}
		return false;
	}
	
	public int IndexAllData(){
		// TODO Auto-generated method stub
		Connection conn = null;
		ResultSet rs = null;
		int count = 0;
		sqlAllRecords = "select BusinessEntityID as id, PersonType as type, Title as title, FirstName as firstName,"
				+ " MiddleName as middleName, LastName as lastName, Suffix as suffix,EmailPromotion as emailPromotion,"
				+ "AdditionalContactInfo as additionalInfo, ModifiedDate as modifiedDate from Person.Person";
		try
		{
			conn = dataSource.getConnection();
			solrServer = solrServerInitializer.getPersonSolrInstance();
			PreparedStatement stmt = conn.prepareStatement(sqlAllRecords);
			rs = (ResultSet) stmt.executeQuery();
			while(rs.next()) {
				count++;
				PersonExtendedVO personData = new PersonExtendedVO();
				personData.setId(rs.getString("id") == null ? "":rs.getString("id"));
				personData.setType(rs.getString("type") == null ? "":rs.getString("type"));
				personData.setTitle(rs.getString("title") == null ? "":rs.getString("title"));
				personData.setFirstName(rs.getString("firstName") == null ? "":rs.getString("firstName"));
				personData.setMiddleName(rs.getString("middleName") == null ? "":rs.getString("middleName"));
				personData.setLastName(rs.getString("lastName") == null ? "":rs.getString("lastName"));
				personData.setSuffix(rs.getString("suffix") == null ? "":rs.getString("suffix"));
				personData.setEmailPromotion(rs.getString("emailPromotion") == null ? "":rs.getString("emailPromotion"));
				personData.setAdditionalInfo(rs.getString("additionalInfo") == null ? "":rs.getString("additionalInfo"));
				personData.setModifiedDate(rs.getDate("modifiedDate"));
				personData.setModifiedDateText(rs.getDate("modifiedDate") == null ? "":convertDateToDDMMMYYYY(rs.getDate("modifiedDate")));
				solrServer.addBean(personData);
			}
			solrServer.commit();
			stmt.close();
			rs.close();
		}
		catch(Exception e){
			throw new RuntimeException();
		}
		finally{
			if(conn != null){
				try
				{
					conn.close();
				}
				catch(SQLException e){
				}
			}
		}
		return 0;
	}

	public void deleteAllRecords() {
		// TODO Auto-generated method stub
		try
		{
			System.out.println("Getting ready to delete all records");
			solrServer.deleteByQuery("*:*");
			solrServer.commit();
			System.out.println("All records are deleted");
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public int fetchRecordCount() {
		Connection conn = null;
		ResultSet rs = null;
		int numRecords = -1;
		try
		{
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlRecordCount);
			rs = (ResultSet) stmt.executeQuery();
			if(rs.next()) {
				numRecords = rs.getInt("RECORDCOUNT");
				System.out.println("Record Count "+numRecords);
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e){
			throw new RuntimeException();
		}
		finally{
			if(conn != null){
				try
				{
					conn.close();
				}
				catch(SQLException e){
				}
			}
		}
		return numRecords;
	}
	
	private String convertDateToDDMMMYYYY(Date date)
	{
		SimpleDateFormat sdfFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String strDate = sdfFormat.format(date);
		return strDate;
	}
	
	public void genericSearch() 
	{
		try
		{
			System.out.println("Generic Search");
			query = new SolrQuery();
			query.setQuery("*:*");
			QueryResponse rsp = solrServer.query(query);
			List<PersonExtendedVO> beans = rsp.getBeans(PersonExtendedVO.class);
			System.out.println("Total Search Time : "+rsp.getQTime());
			System.out.println("Total Record Count : "+beans.size());
			//http://localhost:8082/personAdmin/select?q=*:*
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
