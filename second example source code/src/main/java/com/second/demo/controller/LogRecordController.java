package com.second.demo.controller;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

	/*
	 * Instead of RequestMapping you can use DeleteMapping, PostMapping...
	 */


	// EX : curl -H "Content-Type: application/json" -X POST -d '{"id":"0008", "date":978300000000,"message":"message8","severity":"ERROR"}' http://localhost:8080/records/log
	@ResponseBody
//	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LogRecord> insertLog(@RequestBody LogRecord log) {
		return new ResponseEntity<LogRecord>(recordService.save(log), HttpStatus.CREATED);
	}

	// EX : localhost:8080/records/log/0009
	// If the id's are the same as below, there is no need to write them in pathvariable.
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<LogRecord> deleteLog(@PathVariable("id") String id) {
		LogRecord tmp = recordService.findOne(id);
		recordService.delete(tmp);
		return new ResponseEntity<LogRecord>(HttpStatus.NO_CONTENT);
	}





	/*
	// EX : curl -H "Content-Type: application/json" -X GET http://localhost:8080/records/log
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<LogRecord>> getAll() {
		return new ResponseEntity<List<LogRecord>>((List<LogRecord>) recordService.findAll(), HttpStatus.OK);
	}
	*/


	/*
	@RequestMapping("/")
	public @ResponseBody String greeting() {
		return "Hello Elastic";
	}
	*/




}
