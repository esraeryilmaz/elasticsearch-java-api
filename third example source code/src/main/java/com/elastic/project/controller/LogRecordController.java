package com.elastic.project.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.elastic.project.model.LogRecord.SeverityEnum;
import com.elastic.project.service.RecordService;


/*
 * Instead of RequestMapping you can use DeleteMapping, PostMapping...
 */
@RestController
@RequestMapping(path = "/records/log")
public class LogRecordController {

	@Autowired
	private RecordService recordService;

	// EX : localhost:8080/records/log/id/1
	@GetMapping("/id/{id}")
	public Optional<LogRecord> getById(@PathVariable String id) {
		long longId = Long.parseLong(id);  
		return recordService.findOne(longId);
	}


	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LogRecord> insertLog(@RequestBody LogRecord log) {
		return new ResponseEntity<LogRecord>(recordService.save(log), HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteLog(@PathVariable String id) {
		long longId = Long.parseLong(id);
		recordService.deleteById(longId);
	}


	// EX : localhost:8080/records/log/search
	@GetMapping("/search")
	public Iterable<LogRecord> searchAll() {
		return recordService.findAll();
	}

	// EX : http://localhost:8080/records/log/message/verbose detail for id 1632884948
	@GetMapping(path = "/message/{message}")
	public List<LogRecord> searchMessage(@PathVariable String message) {
		return recordService.findByMessage(message);
	}

/*
	// EX : localhost:8080/records/log/date/2012-02-03T19:58:39
	@GetMapping(path = "/date/{date}")
	public ResponseEntity<List<LogRecord>> searchDate(@PathVariable String date) throws ParseException {
		LocalDateTime dateTime = null;
		if(isValidDate(date)) {
			dateTime = parseDate(date);
		}
		else {
			//
		}
		List<LogRecord> dateList = recordService.findByDate(dateTime);
		return ResponseEntity.ok(dateList);
	}
*/

	// EX : localhost:8080/records/log/date/2012-02-03T19:58:39
	// Spring already parsed the date with the following
	@GetMapping(path = "/date/{date}")
	public ResponseEntity<List<LogRecord>> searchDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
													LocalDateTime date) {
		System.out.println("date " +date);
		List<LogRecord> dateList = recordService.findByDate(date);
		return ResponseEntity.ok(dateList);
	}

	// EX : localhost:8080/records/log/date?start=2012-02-03T19:58:39
	@GetMapping(path = "/date")
	public ResponseEntity<List<LogRecord>> searchDate2(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
													LocalDateTime date) {
		System.out.println("date " +date);
		List<LogRecord> dateList = recordService.findByDate(date);
		return ResponseEntity.ok(dateList);
	}

	// EX : localhost:8080/records/log/betweendates/2012-02-03T18:30:00/2012-02-03T18:41:00
	@GetMapping(path = "/betweendates/{date1}/{date2}")
	public ResponseEntity<List<LogRecord>> searchBetweenDates(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
																LocalDateTime date1,
																@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
																LocalDateTime date2) {
		System.out.println("date1 :  " +date1);
		System.out.println("date2 :  " +date2);
		List<LogRecord> dateList = recordService.findByDateBetweenOrderByIdAsc(date1, date2);
		return ResponseEntity.ok(dateList);
	}


	// EX : localhost:8080/records/log/class/SampleClass0
	@GetMapping(path = "/class/{className}")
	public List<LogRecord> searchClass(@PathVariable String className) {
		return recordService.findByClassName(className);
	}

	// EX : localhost:8080/records/log/severity/DEBUG
	@GetMapping(path = "/severity/{severity}")
	public List<LogRecord> searchSeverity(@PathVariable String severity) {
		SeverityEnum severityEnum = null;
		for(SeverityEnum s : SeverityEnum.values())
	           if (s.name().equals(severity)) 
	              severityEnum = s;
		return recordService.findBySeverity(severityEnum);
	}




	/*
	public LocalDateTime parseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        dateFormat.setLenient(false);
        Date d = dateFormat.parse(date.trim());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDateTime l = d.toInstant()
        					.atZone(defaultZoneId)
        					.toLocalDateTime();
        return l;
	}

	public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            Date d = dateFormat.parse(inDate.trim());
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDateTime l = d.toInstant()
            					.atZone(defaultZoneId)
            					.toLocalDateTime();
            System.out.println(l);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
*/



}
