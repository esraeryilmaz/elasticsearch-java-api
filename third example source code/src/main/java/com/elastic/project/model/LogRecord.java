package com.elastic.project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "record")
public class LogRecord {

	@Id
	@Field(type = FieldType.Long)
	private Long id;

	// ex : 2017-03-07T12:09:04
	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime date;

	@Field(type = FieldType.Text)
	private String className;

	@Field(type = FieldType.Keyword)
	private SeverityEnum severity;

	@Field(type = FieldType.Text)
	private String message;

	public enum SeverityEnum {
		INFO,
		FATAL,
		TRACE,
		ERROR,
		WARN,
		DEBUG
	};


	public LogRecord(Long id, LocalDateTime date, String className, SeverityEnum severity, String message) {
		this.id = id;
		this.date = date;
		this.className = className;
		this.severity = severity;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "LogRecord [id=" + id + ", date=" + date + ", className=" + className + ", severity=" + severity
				+ ", message=" + message + "]";
	}


}
