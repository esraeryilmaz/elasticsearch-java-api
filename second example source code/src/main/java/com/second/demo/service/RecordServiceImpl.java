package com.second.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.second.demo.model.LogRecord;
import com.second.demo.repository.RecordRepository;

//Develop Service component for our project

@Service
public class RecordServiceImpl implements RecordService {

	private RecordRepository recordRepository;

	@Autowired
	public void setRecordRepository(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

	
	public LogRecord save(LogRecord record) {
		return recordRepository.save(record);
	}

	
	public void delete(LogRecord record) {
		recordRepository.delete(record);
	}

	
	public LogRecord findOne(String id) {
		return recordRepository.findOne(id);
	}

	
	public Iterable<LogRecord> findAll() {
		return recordRepository.findAll();
	}

	
	public Page<LogRecord> findByDate(Date date, PageRequest pageRequest) {
		return recordRepository.findByDate(date, pageRequest);
	}

	
	public List<LogRecord> findByMessage(String message) {
		return recordRepository.findByMessage(message);
	}




	//@Override

}
