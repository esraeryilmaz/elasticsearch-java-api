package com.example.first.demo.elasticConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;

import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaElasticClient {

	public static void main(String[] args) throws IOException {

		// CONNECTION : It is responsible to make connection with ElasticSearch from Java client.
		RestHighLevelClient client = new RestHighLevelClient(
						RestClient.builder(new HttpHost("localhost", 9200, "http")));

		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		
		// CREATING INDEX : Creating New Index into ElasticSearch. Run this piece of code just one time.
/*
		CreateIndexRequest request = new CreateIndexRequest("sampleindex");
		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println("response id: " + createIndexResponse.index());
*/
		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

		// DATA INSERTION WAY1 : Inserting a Sample String Value
		IndexRequest indexRequest1 = new IndexRequest("sampleindex");		// Index request to index a typed JSON document into a specific index and make it searchable.
		indexRequest1.id("001");
		indexRequest1.source("SampleKey","SampleValue");
		IndexResponse indexResponse1 = client.index(indexRequest1, RequestOptions.DEFAULT);		// A response of an index operation
		System.out.println("response id: "+indexResponse1.getId());
		System.out.println("response name: "+indexResponse1.getResult().name());

		// DATA INSERTION WAY2 : Inserting a Map data
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("keyOne", 10); 
		map.put("keyTwo", 30);
		map.put("KeyThree", 20);
		IndexRequest indexRequest2 = new IndexRequest("sampleindex");
		indexRequest2.id("002");
		indexRequest2.source(map);
		IndexResponse indexResponse2 = client.index(indexRequest2, RequestOptions.DEFAULT);
		System.out.println("response id: "+indexResponse2.getId());
		System.out.println("response name: "+indexResponse2.getResult().name());

		// DATA INSERTION WAY3 :  Inserting class object's data. Employee class created and after that;
		Employee emp = new Employee("Esra", "Eryılmaz",  LocalDate.now() );
		IndexRequest indexRequest3 = new IndexRequest("sampleindex");
		indexRequest3.id("003");
		indexRequest3.source(new ObjectMapper().writeValueAsString(emp), XContentType.JSON);
		IndexResponse indexResponse3 = client.index(indexRequest3, RequestOptions.DEFAULT);
		System.out.println("response id: "+indexResponse3.getId());
		System.out.println("response name: "+indexResponse3.getResult().name());

		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

		// DATA GETTING
		GetRequest getEmployeeRequest = new GetRequest("sampleindex","003");		// A request to get a document (its source) from an index based on its id.
		GetResponse getResponseGet = client.get(getEmployeeRequest, RequestOptions.DEFAULT);	// The response of a get action.
		String index = getResponseGet.getIndex();
		String type = getResponseGet.getType();
		String id = getResponseGet.getId();

		if(getResponseGet.isExists()) {
			long version = getResponseGet.getVersion();
			String sourceAsString = getResponseGet.getSourceAsString();
			java.util.Map<String, Object> sourceAsMap = new HashMap<>(); 
			sourceAsMap = getResponseGet.getSourceAsMap();
			System.out.println(sourceAsMap);
		}

		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

		// DATA UPDATE WAY1 : Updating a Sample String Value
		UpdateRequest updateRequest1 = new UpdateRequest("sampleindex", "003");
		updateRequest1.doc("firstName", "Zeynep");
		UpdateResponse updateResponse1 = client.update(updateRequest1, RequestOptions.DEFAULT);
		System.out.println("updated response id: "+ updateResponse1.getId());


		// DATA UPDATE WAY2 : Updating Id with particular Map values
		// UpdateRequest -> it will keep previous values and it will update if key matches, if key not matches it will add extra keys into that data.
		// IndexRequest -> it will replace your previous data. (deletes previous data)
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("age","25");
		updateMap.put("sector","Education");
		UpdateRequest updateRequest2 = new UpdateRequest("sampleindex", "003").doc(updateMap);
		UpdateResponse updateResponse2 = client.update(updateRequest2, RequestOptions.DEFAULT);
		System.out.println("updated response id: "+updateResponse2.getId());


		// DATA UPDATE WAY3 : Inserting class object's data
		Employee newEmp = new Employee("Esra2", "Eryılmaz2",  LocalDate.now() );
		IndexRequest request = new IndexRequest("sampleindex");
		request.id("003");
		request.source(new ObjectMapper().writeValueAsString(newEmp), XContentType.JSON);
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		System.out.println("response id:" + indexResponse.getId());
		System.out.println(indexResponse.getResult().name());

		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


		// DATA DELETION WAY1 : Delete particular record
		DeleteRequest deleteRequest = new DeleteRequest("sampleindex","003");
		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println("response id: " + deleteResponse.getId());


		// DATA DELETION WAY2 : Delete entire index
		DeleteIndexRequest delRe = new DeleteIndexRequest("sampleindex");
		client.indices().delete(delRe, RequestOptions.DEFAULT);
		System.out.println("Delete Done ");



		/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */






	}

}
