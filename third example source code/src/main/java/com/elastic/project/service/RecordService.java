package com.elastic.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.elastic.project.model.LogRecord;
import com.elastic.project.model.LogRecord.SeverityEnum;


public interface RecordService {

	LogRecord save(LogRecord record);
	void delete(LogRecord record);
	Optional<LogRecord> findOne(Long id);
	Iterable<LogRecord> findAll();
	//List<LogRecord> findByDate(String date);

	List<LogRecord> findByMessage(String message);
	
	List<LogRecord> findByDate(LocalDateTime date);
	
	List<LogRecord> findByClassName(String className);

	List<LogRecord> findBySeverity(SeverityEnum severity);
	
	List<LogRecord> findByDateIsBefore(LocalDateTime date);
	
	List<LogRecord> findByDateBetween(LocalDateTime date1, LocalDateTime date2);

	List<LogRecord> findByDateOrderByIdAsc(LocalDateTime date);

	List<LogRecord> findByDateBetweenOrderByIdDesc(LocalDateTime date1, LocalDateTime date2);


}

