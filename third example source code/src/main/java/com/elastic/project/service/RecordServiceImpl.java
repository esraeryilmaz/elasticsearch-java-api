package com.elastic.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elastic.project.model.LogRecord;
import com.elastic.project.model.LogRecord.SeverityEnum;
import com.elastic.project.repository.RecordRepository;

@Service
public class RecordServiceImpl implements RecordService {

	private RecordRepository recordRepository;

	//@Autowired		// it works without autowired??
	public RecordServiceImpl(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

	@Override
	public LogRecord save(LogRecord record) {
		return recordRepository.save(record);
	}

	@Override
	public void delete(LogRecord record) {
		recordRepository.delete(record);
	}

	@Override
	public Iterable<LogRecord> findAll() {
		return recordRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		recordRepository.deleteById(id);
	}

	@Override
	public List<LogRecord> findByMessage(String message) {
		return recordRepository.findByMessage(message);
	}

	@Override
	public Optional<LogRecord> findOne(Long id) {
		return recordRepository.findById(id);
	}

	@Override
	public List<LogRecord> findByDate(LocalDateTime date) {
		return recordRepository.findByDate(date);
	}

	@Override
	public List<LogRecord> findByDateIsBefore(LocalDateTime date) {
		return recordRepository.findByDateIsBefore(date);
	}

	@Override
	public List<LogRecord> findByDateBetween(LocalDateTime date1, LocalDateTime date2) {
		return recordRepository.findByDateBetween(date1, date2);
	}

	@Override
	public List<LogRecord> findByClassName(String className) {
		return recordRepository.findByClassName(className);
	}

	@Override
	public List<LogRecord> findBySeverity(SeverityEnum severity) {
		return recordRepository.findBySeverity(severity);
	}

	@Override
	public List<LogRecord> findByDateOrderByIdAsc(LocalDateTime date) {
		return recordRepository.findByDateOrderByIdAsc(date);
	}

	@Override
	public List<LogRecord> findByDateBetweenOrderByIdDesc(LocalDateTime date1, LocalDateTime date2) {
		return recordRepository.findByDateBetweenOrderByIdDesc(date1, date2);
	}

	@Override
	public List<LogRecord> findByDateBetweenOrderByIdAsc(LocalDateTime date1, LocalDateTime date2) {
		return recordRepository.findByDateBetweenOrderByIdAsc(date1, date2);
	}




	/*
	@Override
	public List<LogRecord> findByDate(String date) {
		//LocalDateTime dateTime = LocalDate.parse(date).atTime(0,0,0);
		LocalDateTime dateTime = LocalDate.parse(date).atTime(0,0);
		//return recordRepository.findByDateIsBefore(dateTime);
		return recordRepository.findByDate(dateTime);
	}
*/

}
