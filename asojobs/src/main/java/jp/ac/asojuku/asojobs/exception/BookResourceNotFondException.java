package jp.ac.asojuku.asojobs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookResourceNotFondException extends RuntimeException{
	
	public BookResourceNotFondException(String bookId){
		super("Book is not found (bookId = "+ bookId +")");
		
	}
}
