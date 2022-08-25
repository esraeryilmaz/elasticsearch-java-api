package com.second.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.second.demo.model.LogRecord;
import com.second.demo.model.LogRecord.SeverityEnum;
import com.second.demo.service.RecordService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ElasticsearchOperations es;

	@Autowired
	private RecordService recordService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		printElasticSearchInfo();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

		Date date1 = dateformat.parse("01/01/2001");

		recordService.save(new LogRecord("0001",date1,"message1",SeverityEnum.ERROR));
		recordService.save(new LogRecord("0002",date1,"message2",SeverityEnum.DEBUG));
		recordService.save(new LogRecord("0003",date1,"message2", SeverityEnum.INFO));

		// search message2
		List<LogRecord> records = recordService.findByMessage("message2");
		records.forEach(x -> System.out.println(x));




	}


	// Useful for debug, print elastic search details
	private void printElasticSearchInfo() {
		System.out.println("----------ElasticSearch----------");
		Client client = es.getClient();
		Map<String, String> asMap = client.settings().getAsMap();

		asMap.forEach((k, v) -> {
			System.out.println(k + " = " + v);
		});
		System.out.println("----------ElasticSearch----------");
	}
	
}
