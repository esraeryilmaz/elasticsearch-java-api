package com.second.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.second.demo.model.LogRecord;

// Develop Elasticsearch Repository for our project

/*
 * Repository interface’i oluşturuldu. Bunun için ElasticSearchRepository interface’ini 
 * extend etmemiz gerekiyor. Generic type’ları ilgili doküman ve bu dokümanın primary key tipi 
 * olarak belirtiyoruz. Bu adımlar sonucunda Elasticsearch üzerinde CRUD işlemleri yapabilir düzeye geliniyor.
 */

@Repository
public interface RecordRepository extends ElasticsearchRepository<LogRecord, String> {

	Page<LogRecord> findByDate(Date date, Pageable pageable);
	List<LogRecord> findByMessage(String message);

	// You can also define a custom query with @Query annotation and insert a JSON query in the parameters.

}
