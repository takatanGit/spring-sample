package jp.ac.asojuku.asojobs.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

//import org.springframework.beans.factory.InitializingBean;

public class BookResource implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String bookId;
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private java.time.LocalDate publishedDate;
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.time.LocalDate getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(java.time.LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
