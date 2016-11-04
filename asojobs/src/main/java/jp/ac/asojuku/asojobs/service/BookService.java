package jp.ac.asojuku.asojobs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

import jp.ac.asojuku.asojobs.beans.Book;
import jp.ac.asojuku.asojobs.beans.BookCriteria;
import jp.ac.asojuku.asojobs.beans.BookResource;

@Service
public class BookService {
	private final Map<String,Book> bookRepository = new ConcurrentHashMap<String,Book>();
	
	@PostConstruct
	public void loadDummyData(){
		Book book = new Book();
		book.setBookId("00000000-0000-0000-0000-00000000");
		book.setName("書籍名");
		book.setPublishedDate(LocalDate.of(2010, 4, 20));
		bookRepository.put(book.getBookId(), book);
	}
	public Book find(String bookId){
		Book book =  bookRepository.get(bookId);
		return book;
	}
	public Book create(Book book){
		String bookId = UUID.randomUUID().toString();
		book.setBookId(bookId);
		bookRepository.put(bookId, book);
		return book;
	}
	public Book update(Book book){
		return bookRepository.put(book.getBookId(), book);
	}
	public Book delete(String bookId){
		return bookRepository.remove(bookId);
	}
	public List<Book> findAllByCriteria(BookCriteria criteria){
		return bookRepository.values().stream()
				.filter(book -> 
						(criteria.getName() == null
							|| book.getName().contains(criteria.getName())) &&
						(criteria.getPublishedDate() == null
							|| book.getPublishedDate().equals(criteria.getPublishedDate())))
						.sorted((o1,o2) ->
							o1.getPublishedDate().compareTo(o2.getPublishedDate()))
						.collect(Collectors.toList());
	}
}
