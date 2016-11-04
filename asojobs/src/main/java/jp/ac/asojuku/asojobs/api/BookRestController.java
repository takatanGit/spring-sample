package jp.ac.asojuku.asojobs.api;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.asojuku.asojobs.beans.Book;
import jp.ac.asojuku.asojobs.beans.BookCriteria;
import jp.ac.asojuku.asojobs.beans.BookResource;
import jp.ac.asojuku.asojobs.beans.BookResourceQuery;
import jp.ac.asojuku.asojobs.exception.BookResourceNotFondException;
import jp.ac.asojuku.asojobs.service.BookService;

@RestController
@RequestMapping("books")
public class BookRestController {
	@Autowired
	BookService bookService;
	
	@RequestMapping(path = "{bookId}",method = RequestMethod.GET)
	public BookResource getBook(@PathVariable String bookId){
		Book book =  bookService.find(bookId);
		//書籍がない場合は、スローする
		if(book == null){
			throw new BookResourceNotFondException(bookId);
		}
		BookResource resource = new BookResource();
		resource.setBookId(book.getBookId());
		resource.setName(book.getName());
		resource.setPublishedDate(book.getPublishedDate());
		return resource;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createBook(
			@Validated @RequestBody BookResource newResource){
		Book newBook  = new Book();
		newBook.setName(newResource.getName());
		newBook.setPublishedDate(newResource.getPublishedDate());
		newBook.setBookId(newResource.getBookId());
		
		Book createBook = bookService.create(newBook);
		String resourceUri = 
				"http://localhost:8082/asojobs/books/" + createBook.getBookId();
		return ResponseEntity.created(URI.create(resourceUri)).build();
	}
	@RequestMapping(path = "{bookId}",method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void put(@PathVariable String bookId,
					@Validated @RequestBody BookResource resource){
		Book book = new Book();
		book.setBookId(bookId);
		book.setName(resource.getName());
		book.setPublishedDate(resource.getPublishedDate());
		bookService.update(book);
	}
	@RequestMapping(path = "{bookId}",method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String bookId){
		bookService.delete(bookId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<BookResource> searchBooks(@Validated BookResourceQuery query){
		
		BookCriteria criteria = new BookCriteria();
		
		criteria.setName(query.getName());
		criteria.setPublishedDate(query.getPublishedDate());
		
		List<Book> books = bookService.findAllByCriteria(criteria);
		
		return  books.stream().map(book ->{
			BookResource resource = new BookResource();
			resource.setBookId(book.getBookId());
			resource.setName(book.getName());
			resource.setPublishedDate(book.getPublishedDate());
			return resource;
		}).collect(Collectors.toList());
	}
	
}
