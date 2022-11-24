package com.elastic.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elastic.project.model.LogRecord;
import com.elastic.project.model.LogRecord.SeverityEnum;
import com.elastic.project.service.RecordService;

@SpringBootApplication
public class ProjectApplication  implements CommandLineRunner {

	@Autowired
	private RecordService service;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// proje her çalıştığında, okuyup kaydetmek birkaç dk sürüyor
		//ReadLogFile();
		//ReadLogFileInRealTime();
		ReadLogFilev2();
/*
		String time = "19:11:02";
		System.out.println(checkIfTimeIsValid(time));
*/



		// search verbose detail for id 1632884948
		List<LogRecord> findMessage = service.findByMessage("verbose detail for id 1632884948");
		System.out.println("findByMessage(\"verbose detail for id 1632884948\") -> " + findMessage);

		LocalDateTime date1 = LocalDate.parse("2012-02-03").atTime(19,58,39);
		List<LogRecord> findDate = service.findByDate(date1);
		System.out.println("findByDate(2012-02-03T19:58:39) -> " + findDate);
		System.out.println("findOne(300L) -> " +service.findOne(300L));


		LocalDateTime date2 = LocalDate.parse("2012-02-03").atTime(18,40,0);
		LocalDateTime date3 = LocalDate.parse("2012-02-03").atTime(18,56,0);
		List<LogRecord> findDateBetween0 = service.findByDateBetween(date2, date3);
		System.out.println("findByDateBetween(2012-02-03T18:40:00, 2012-02-03T18:56:00 ) -> ");
		System.out.println(findDateBetween0.size() + " data");
		for (LogRecord logRecord : findDateBetween0) {
			System.out.println(logRecord);
		}


		List<LogRecord> findDateByIdAsc = service.findByDateOrderByIdAsc(date1);
		System.out.println("findByDateOrderByIdAsc(2012-02-03T19:58:39 ) -> ");
		System.out.println(findDateByIdAsc.size() + " data");
		for (LogRecord logRecord : findDateByIdAsc) {
			System.out.println(logRecord);
		}

		List<LogRecord> findDateBetween = service.findByDateBetweenOrderByIdDesc(date2, date3);
		System.out.println("findByDateBetweenOrderByIdDesc(2012-02-03T18:40:00, 2012-02-03T18:56:00) -> ");
		System.out.println(findDateBetween.size() + " data");
		for (LogRecord logRecord : findDateBetween) {
			System.out.println(logRecord);
		}

	}

	// It does a few error checks while reading the file
	// So I can parse it the way I want
	public void ReadLogFilev2() {
		String fileName = "C:\\Users\\esra\\Desktop\\elastic v7 with log file\\src\\main\\java\\com\\elastic\\project\\app.log";	// File location must be given
		Long id = 1L;

		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] arrOfData = data.split(" ", 5);

				int length = arrOfData.length;
				if(length == 5) {
					String severityString = arrOfData[3].substring( 1, arrOfData[3].length() - 1 );
					SeverityEnum severity = null;

					boolean b = checkIfDateIsValid(arrOfData[0]) && checkIfTimeIsValid(arrOfData[1]) && checkIfSeverityIsValid(severityString);
					System.out.println(b);

					if( b ) {
						LocalDate localDate = LocalDate.parse(arrOfData[0]);
						LocalTime localTime = LocalTime.parse(arrOfData[1]);
						LocalDateTime date = LocalDateTime.of(localDate, localTime);
						for(SeverityEnum s : SeverityEnum.values())
					           if (s.name().equals(severityString)) 
					              severity = s;
						LogRecord log = new LogRecord(id, date, arrOfData[2], severity, arrOfData[4]);
						System.out.println(log);
						service.save(log);
						id = ++id;
					}
					else {
						continue;
					}
				}
				else {
					continue;
				}

			}
			myReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}


	public void ReadLogFile() {
		String fileName = "C:\\Users\\esra\\Desktop\\elastic v7 with log file\\src\\main\\java\\com\\elastic\\project\\app.log";	// File location must be given
		Long id = 1L;

		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				String[] arrOfData = data.split(" ", 5);
				//for (String a : arrOfData)
		         //   System.out.println(a);

				LocalDate localDate = LocalDate.parse(arrOfData[0]);
				LocalTime localTime = LocalTime.parse(arrOfData[1]);
				LocalDateTime date = LocalDateTime.of(localDate, localTime);

				String severityString = arrOfData[3].substring( 1, arrOfData[3].length() - 1 );
				SeverityEnum severity = null;
				for(SeverityEnum s : SeverityEnum.values())
			           if (s.name().equals(severityString)) 
			              severity = s;
				LogRecord log = new LogRecord(id, date, arrOfData[2], severity, arrOfData[4]);
				System.out.println(log);
				service.save(log);
				id = ++id;
			}
			myReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static boolean checkIfDateIsValid(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			format.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean checkIfTimeIsValid(String time) {
		//Pattern p = Pattern.compile(".*([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].*");
		Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");
		Matcher m = p.matcher(time);
		if(m.matches()){
			return true;
		}else{
			return false;
		}
	}

	public static boolean checkIfSeverityIsValid(String severityString) {
		for(SeverityEnum s : SeverityEnum.values()) {
           if (s.name().equals(severityString)) {
        	   return true;
           }
		}
		return false;
	}


/*
	public void ReadLogFileInRealTime() throws IOException, InterruptedException {
		String fileName = "C:\\Users\\esra\\Desktop\\elastic v7 with log file\\src\\main\\java\\com\\elastic\\project\\app.log";
		Long id = 1L;

		BufferedReader file;
		String line;
		
		try {
			file = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// Initial file read
		try {
			while((line = file.readLine()) != null) {
				System.out.println(line);
				
				String[] arrOfData = line.split(" ", 5);
				LocalDate localDate = LocalDate.parse(arrOfData[0]);
				LocalTime localTime = LocalTime.parse(arrOfData[1]);
				LocalDateTime date = LocalDateTime.of(localDate, localTime);

				String severityString = arrOfData[3].substring( 1, arrOfData[3].length() - 1 );
				SeverityEnum severity = null;
				for(SeverityEnum s : SeverityEnum.values())
			           if (s.name().equals(severityString)) 
			              severity = s;
				LogRecord log = new LogRecord(id, date, arrOfData[2], severity, arrOfData[4]);
				System.out.println(log);
				service.save(log);
				id = ++id;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Continuous file read
		try {
			while(true) {
				while((line = file.readLine()) != null) {
					System.out.println(line);

					String[] arrOfData = line.split(" ", 5);
					LocalDate localDate = LocalDate.parse(arrOfData[0]);
					LocalTime localTime = LocalTime.parse(arrOfData[1]);
					LocalDateTime date = LocalDateTime.of(localDate, localTime);

					String severityString = arrOfData[3].substring( 1, arrOfData[3].length() - 1 );
					SeverityEnum severity = null;
					for(SeverityEnum s : SeverityEnum.values())
				           if (s.name().equals(severityString)) 
				              severity = s;
					LogRecord log = new LogRecord(id, date, arrOfData[2], severity, arrOfData[4]);
					System.out.println(log);
					service.save(log);
					id = ++id;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/


/*
	//
	public void ReadLogFileInRealTime() throws IOException, InterruptedException {
		String fileName = "C:\\Users\\esra\\Desktop\\elastic v7 with log file\\src\\main\\java\\com\\elastic\\project\\app.log";
		Long id = 1L;

		FileReader fr = new FileReader(fileName); 
		BufferedReader br = new BufferedReader(fr);
		String line;

		while (true) {
			line = br.readLine();
			if (line == null) {
				//wait until there is more of the file for us to read
				Thread.sleep(1000);
			}
			else {
				//do something interesting with the line
				String[] arrOfData = line.split(" ", 5);
				LocalDate localDate = LocalDate.parse(arrOfData[0]);
				LocalTime localTime = LocalTime.parse(arrOfData[1]);
				LocalDateTime date = LocalDateTime.of(localDate, localTime);

				String severityString = arrOfData[3].substring( 1, arrOfData[3].length() - 1 );
				SeverityEnum severity = null;
				for(SeverityEnum s : SeverityEnum.values())
			           if (s.name().equals(severityString)) 
			              severity = s;
				LogRecord log = new LogRecord(id, date, arrOfData[2], severity, arrOfData[4]);
				System.out.println(log);
				service.save(log);
				id = ++id;
			}
			//br.close();
		}
	}
*/





}




