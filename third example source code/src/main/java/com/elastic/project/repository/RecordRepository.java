package com.elastic.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elastic.project.model.LogRecord;
import com.elastic.project.model.LogRecord.SeverityEnum;

@Repository
public interface RecordRepository extends ElasticsearchRepository<LogRecord, Long> {

	List<LogRecord> findByMessage(String message);

	List<LogRecord> findByDate(LocalDateTime date);
	
	List<LogRecord> findByClassName(String className);

	List<LogRecord> findBySeverity(SeverityEnum severity);
	
	List<LogRecord> findByDateIsBefore(LocalDateTime date);		// finds the given date and before
	
	List<LogRecord> findByDateBetween(LocalDateTime date1, LocalDateTime date2);	// finds between two dates

	List<LogRecord> findByDateOrderByIdAsc(LocalDateTime date);		// finds and sort by desired date from smallest to largest
	
	List<LogRecord> findByDateBetweenOrderByIdDesc(LocalDateTime date1, LocalDateTime date2);	// finds between two dates and sort largest to smallest


}

