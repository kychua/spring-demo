package ebookshop.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebookshop.dao.OrderRepository;
import ebookshop.entity.Order;
import ebookshop.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderService orderService;

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
	public ResponseEntity<?> getOrder(@PathVariable int id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// CREATE
	// ------------------------------------------------------------------------------
	@PostMapping
	public ResponseEntity<?> addOrder(@RequestParam int bookId, @RequestParam int customerId) {
		log.info("Request to place order: book " + bookId + ", customer " + customerId);
		try {
			boolean hasOrdered = orderService.placeOrder(bookId, customerId);
			if (!hasOrdered) {
				return ResponseEntity.badRequest().body("No available copies for book " + bookId);
			} else {
				return ResponseEntity.ok().build();
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid book " + bookId + " or invalid customer " + customerId);
		}
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