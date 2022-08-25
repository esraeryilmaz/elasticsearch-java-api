package com.second.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.stereotype.Component;


// Develop Model for our project

/*
 * Elasticsearch’teki her bir kayıt doküman olarak geçmektedir. 
 * Bunu belirtmek için model, @Document anotasyonu ile işaretlenip 
 * kaydedileceği index’i belirtmek için ise indexName parametresi veriliyor.
 */

//@Component
@Document(indexName = "records", type = "log")
public class LogRecord {

	@Id
	private String id;
	private Date date;
	private String message;
	private SeverityEnum severity;

	public enum SeverityEnum {
		INFO,
		ERROR,
		WARNING,
		DEBUG
	};
	
	

	public LogRecord() {
		// Empty constructor
	}

	public LogRecord(String id, Date date, String message, SeverityEnum severity) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "LogRecord [date=" + 
				date + ", message=" + 
				message + ", severity=" + 
				severity + "]";
	}

	// getters and setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public SeverityEnum getSeverity() {
		return severity;
	}



	public void setSeverity(SeverityEnum severity) {
		this.severity = severity;
	}





}
