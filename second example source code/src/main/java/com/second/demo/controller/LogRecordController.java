package com.second.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.second.demo.model.LogRecord;
import com.second.demo.service.RecordService;


@RestController
@RequestMapping("/records/log")
public class LogRecordController {

	@Autowired
	private RecordService recordService;


	// EX : localhost:8080/records/log/0001
	@GetMapping("/{id}")
	public LogRecord getById(@PathVariable String id) {
		return recordService.findOne(id);
	}






}
