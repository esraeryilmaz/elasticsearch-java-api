package com.second.demo;

import com.second.demo.model.LogRecord;
import com.second.demo.model.LogRecord.SeverityEnum;
import com.second.demo.service.RecordService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

// Develop one Test Application to unit test our code.

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class LogRecordTest {

	@Autowired
	private RecordService recordService;

	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Before
	public void before() {
		esTemplate.deleteIndex(LogRecord.class);
		esTemplate.createIndex(LogRecord.class);
		esTemplate.putMapping(LogRecord.class);
		esTemplate.refresh(LogRecord.class);
	}

	@Test
	public void testSave() throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = dateformat.parse("01/01/2001");

		LogRecord record = new LogRecord("0001",date1,"message1", SeverityEnum.ERROR);

		LogRecord testRecord = recordService.save(record);

		assertNotNull(testRecord.getId());
		assertEquals(testRecord.getDate(), record.getDate());
		assertEquals(testRecord.getMessage(), record.getMessage());
		assertEquals(testRecord.getSeverity(), record.getSeverity());

	}

	@Test
	public void testFindOne() throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = dateformat.parse("01/01/2001");
		LogRecord record = new LogRecord("0001",date1,"message1", SeverityEnum.ERROR);

		recordService.save(record);

		LogRecord testRecord = recordService.findOne(record.getId());
		assertNotNull(testRecord.getId());
		assertEquals(testRecord.getDate(), record.getDate());
		assertEquals(testRecord.getMessage(), record.getMessage());
		assertEquals(testRecord.getSeverity(), record.getSeverity());
	}


	@Test
	public void testFindByMessage() throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = dateformat.parse("01/01/2001");
		LogRecord record = new LogRecord("0001",date1,"message1", SeverityEnum.ERROR);

		recordService.save(record);

		List<LogRecord> byMessage =recordService.findByMessage(record.getMessage());
		assertThat(byMessage.size(), is(1));


	}

	@Test
	public void testFindByDate() throws ParseException {
		List<LogRecord> recordList = new ArrayList<LogRecord>();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = dateformat.parse("01/01/2001");
		Date date2 = dateformat.parse("02/02/2002");

		recordList.add(new LogRecord("0001",date1,"message1", SeverityEnum.ERROR));
		recordList.add(new LogRecord("0002",date1,"message2", SeverityEnum.DEBUG));
		recordList.add(new LogRecord("0003",date1,"message3", SeverityEnum.INFO));
		recordList.add(new LogRecord("0004",date2,"message4", SeverityEnum.WARNING));

		for (LogRecord record : recordList) {
			recordService.save(record);
		}

		Page<LogRecord> byDate1 = recordService.findByDate(date1, new PageRequest(0,10));
		assertThat(byDate1.getTotalElements(),is(3L));

		Page<LogRecord> byDate2 = recordService.findByDate(date2, new PageRequest(0,10));
		assertThat(byDate2.getTotalElements(),is(1L));
	}


	@Test
	public void testDelete() throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = dateformat.parse("01/01/2001");
		LogRecord record = new LogRecord("0001",date1,"message1", SeverityEnum.ERROR);

		recordService.save(record);
		recordService.delete(record);

		LogRecord testRecord = recordService.findOne(record.getId());
		assertNull(testRecord);
	}





}
