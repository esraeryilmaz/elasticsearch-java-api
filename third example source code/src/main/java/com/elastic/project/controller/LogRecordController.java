package com.elastic.project.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elastic.project.model.LogRecord;
import com.elastic.project.service.RecordService;

@RestController
@RequestMapping(path = "/records/log")
public class LogRecordController {

	@Autowired
	private RecordService recordService;

	// EX : localhost:8080/records/log/id/1
	@GetMapping("/id/{id}")
	public Optional<LogRecord> getById(@PathVariable String id) {
		long l = Long.parseLong(id);  
		return recordService.findOne(l);
	}

	/*
	 * Instead of RequestMapping you can use DeleteMapping, PostMapping...
	 */


	@ResponseBody
//	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LogRecord> insertLog(@RequestBody LogRecord log) {
		return new ResponseEntity<LogRecord>(recordService.save(log), HttpStatus.CREATED);
	}

	// EX : localhost:8080/records/log/search
	// normalde findAll iterable object döndürüyor, fakat HTTP olarak basılabilir değil, bu sebeple liste atıldı.
	@GetMapping("/search")
	public Iterable<LogRecord> searchAll() {
		return recordService.findAll();
	}


	// EX : localhost:8080/records/log/date?date=2012-02-03
	@GetMapping(path = "/date")
	public ResponseEntity<List<LogRecord>> searchDate(@RequestParam("date") String date) {
		LocalDateTime dateTime = LocalDate.parse(date).atTime(18,40,36);		// !
		List<LogRecord> dateList = recordService.findByDate(dateTime);
		return ResponseEntity.ok(dateList);
	}




}
