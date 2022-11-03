package com.elastic.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.elastic.project.model.LogRecord;
import com.elastic.project.model.LogRecord.SeverityEnum;
import com.elastic.project.service.RecordService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectApplication.class)
public class LogRecordTest {

	@Autowired
	private RecordService recordService;

	/*
	@Autowired
	private ElasticsearchTemplate esTemplate;

	@Before
	public void before() {
		esTemplate.deleteIndex(LogRecord.class);
		esTemplate.createIndex(LogRecord.class);
		esTemplate.putMapping(LogRecord.class);
		esTemplate.refresh(LogRecord.class);
	}
*/

/*
	@Test
	public void testSave() throws ParseException {
		LocalDateTime date1 = LocalDate.parse("2000-03-03").atTime(0,0);
		LogRecord record = new LogRecord(1L, date1,"message1", SeverityEnum.ERROR);

		LogRecord testRecord = recordService.save(record);

		assertNotNull(testRecord.getId());
		assertEquals(testRecord.getDate(), record.getDate());
		assertEquals(testRecord.getMessage(), record.getMessage());
		assertEquals(testRecord.getSeverity(), record.getSeverity());
	}

	@Test
	public void testFindOne() throws ParseException {
		LocalDateTime date1 = LocalDate.parse("2000-03-03").atTime(0,0);
		LogRecord record = new LogRecord(1L, date1,"message1", SeverityEnum.ERROR);
		recordService.save(record);

		Optional<LogRecord> testRecord = recordService.findOne(record.getId());

	}

	@Test
	public void testFindByMessage() throws ParseException {
		LocalDateTime date1 = LocalDate.parse("2000-03-03").atTime(0,0);
		LogRecord record = new LogRecord(1L, date1,"message1", SeverityEnum.ERROR);
		recordService.save(record);

		List<LogRecord> byMessage = recordService.findByMessage(record.getMessage());
//		assertThat(byMessage.size(), is(1));

	}

*/



}
