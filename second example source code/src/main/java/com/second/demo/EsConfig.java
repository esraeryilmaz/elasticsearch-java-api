package com.second.demo;

import java.net.InetAddress;
//import java.net.UnknownHostException;
//import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


// Develop SpringBoot Configuration. Connect to Elastic Search cluster via "TransportClient"

/*
 * Oluşturulacak olan repository’lerin istenilen işlevleri sağlayabilmesi için uygulamanın 
 * ayağa kalktığı sınıf (class EsConfig) @EnableElasticsearchRepositories anotasyonu ile işaretlenip parametre olarak 
 * repository’nin yer aldığı paketin yolu verilir. Böylelikle Spring tarafından gerekli konfigürasyonlar yapılacaktır.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.second.demo.repository")
public class EsConfig {

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;



	@Bean
	public Client client() throws Exception {
		Settings esSettings = Settings.builder()
				.put("cluster.name", EsClusterName)
				.build();

		//https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
		return TransportClient.builder()
				.settings(esSettings)
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}


}



