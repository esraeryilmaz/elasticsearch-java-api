package com.second.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.second.demo.model.LogRecord;



public interface RecordService {

	LogRecord save(LogRecord record);
	void delete(LogRecord record);
	LogRecord findOne(String id);
	Iterable<LogRecord> findAll();
	Page<LogRecord> findByDate(Date date, PageRequest pageRequest);
	List<LogRecord> findByMessage(String message);




}
