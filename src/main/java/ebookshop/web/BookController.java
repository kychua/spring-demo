package ebookshop.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebookshop.entity.Book;
import ebookshop.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookService bookService;

	// READ
	// ------------------------------------------------------------------------------
	@GetMapping
	public Iterable<Book> getAllBooks() {
		// @ResponseBody means the returned object forms the body of the HTTP response
		// HTTP response does not support a Java object, so it is converted to JSON/XML automatically
		// Thus we get the results in JSON/XML format in the HTTP response
		return bookService.getAllBooks();
	}

	// @PathVariable to handle dynamic id
	@GetMapping(path="/{id}")
	public Optional<Book> getBook(@PathVariable int id) {
		return bookService.getBook(id);
	}

	// CREATE
	// ------------------------------------------------------------------------------
	@PostMapping
	public ResponseEntity<?> addBook(@ModelAttribute Book book) {
		bookService.createBook(book);
		return ResponseEntity.ok().build();
	}

	// UPDATE
	// ------------------------------------------------------------------------------
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable int id, @Valid @RequestBody Book bookDetails) {
		return bookService.updateBook(id, bookDetails);
	}

	// DELETE
	// ------------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeBook(@PathVariable int id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok().build();
	}


//	@GetMapping(path="/sayhi")
//	public String sayHi(@RequestParam(value="name", defaultValue="World") String name, Model model) {
//		model.addAttribute("name", name); // else we get null instead of World when no name is supplied
//		return "say-hi"; // returned String is a view name (say-hi.html populated with values in model)
//	}

}