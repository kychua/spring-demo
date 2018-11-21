package ebookshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ebookshop.dao.BookRepository;
import ebookshop.entity.Book;

@Controller // handles incoming requests
@RequestMapping(path="/demo")
public class BookController {
	@Autowired // get bean called bookRepository, which is auto-generated by Spring
	private BookRepository bookRepository;

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Book> getAllBooks() {
		// @ResponseBody means the returned object forms the body of the HTTP response
		// HTTP response does not support a Java object, so it is converted to JSON/XML automatically
		// Thus we get the results in JSON/XML format in the HTTP response
		return bookRepository.findAll();
	}

	@GetMapping(path="/queryauthor")
	public String getAvailableBooksByAuthor(@RequestParam String author, Model model) {
		model.addAttribute("books", bookRepository.findByAuthorAndQtyGreaterThan(author, 0));
		return "query-results";
	}

	@GetMapping(path="/sayhi")
	public String sayHi(@RequestParam(value="name", defaultValue="World") String name, Model model) {
		model.addAttribute("name", name); // else we get null instead of World when no name is supplied
		return "say-hi"; // returned String is a view name (say-hi.html populated with values in model)
	}

}