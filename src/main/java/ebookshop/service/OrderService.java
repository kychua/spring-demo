package ebookshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebookshop.dao.CustomerRepository;
import ebookshop.dao.OrderRepository;
import ebookshop.entity.Book;
import ebookshop.entity.Customer;
import ebookshop.entity.Order;

@Service
public class OrderService {

	@Autowired
	private BookService bookService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	public boolean isValidOrder(int bookId, int customerId) {
		Book book = bookService.getBook(bookId).orElseThrow(IllegalArgumentException::new);
		return book.getQty() > 0;
	}

	public boolean placeOrder(int bookId, int customerId) {
		Book book = bookService.getBook(bookId).orElseThrow(IllegalArgumentException::new);
		Customer customer = customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
		if (book.getQty() <= 0) {
			return false;
		}

		bookService.removeCopy(book);

		Order order = new Order(null, book, customer);
		orderRepository.save(order);
		return true;
	}

}