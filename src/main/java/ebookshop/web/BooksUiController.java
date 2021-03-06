package ebookshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ebookshop.entity.Book;
import ebookshop.service.BookService;

@Controller
@RequestMapping("/books")
public class BooksUiController {
	@Autowired // get bean called bookRepository, which is auto-generated by Spring
	private BookService bookService;

	@GetMapping("/{id}")
	public String showBookDetails(@PathVariable int id, Model model) {
		model.addAttribute("book", bookService.getBook(id).orElse(new Book()));
		return "books";
	}

	@GetMapping("/search")
	public String showAvailableBooksByAuthor(@RequestParam String author, Model model) {
		model.addAttribute("books", bookService.getAvailableBooksByAuthor(author));
		return "query-results";
	}

	@GetMapping
	public String showBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "books";
	}

	@PostMapping
	public String addBook(@ModelAttribute Book book) {
		bookService.createBook(book);
		return "books";
	}

}

