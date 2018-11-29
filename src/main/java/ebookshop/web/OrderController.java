package ebookshop.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebookshop.dao.BookRepository;
import ebookshop.dao.CustomerRepository;
import ebookshop.dao.OrderRepository;
import ebookshop.entity.Book;
import ebookshop.entity.Customer;
import ebookshop.entity.Order;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BookRepository bookRepository;

	// READ
	// ------------------------------------------------------------------------------
	@GetMapping
	public Iterable<Order> getAllOrders() {
		// @ResponseBody means the returned object forms the body of the HTTP response
		// HTTP response does not support a Java object, so it is converted to JSON/XML automatically
		// Thus we get the results in JSON/XML format in the HTTP response
		return orderRepository.findAll();
	}

	// @PathVariable to handle dynamic id
	@GetMapping(path="/{id}")
	public Optional<Order> getOrder(@PathVariable int id) {
		return orderRepository.findById(id);
	}

	// CREATE
	// ------------------------------------------------------------------------------
	@PostMapping
	public ResponseEntity<?> addOrder(@RequestParam int bookId, @RequestParam int customerId) {
		Customer customer = customerRepository.findById(customerId).get();
		Book book = bookRepository.findById(bookId).get();
		Order order = new Order(null, book, customer);
		orderRepository.save(order);
		return ResponseEntity.ok().build();
	}


	// DELETE
	// ------------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeOrder(@PathVariable int id) {
		orderRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<?> clearOrders() {
		orderRepository.deleteAll();
		return ResponseEntity.ok().build();
	}

}