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

import ebookshop.dao.CustomerRepository;
import ebookshop.entity.Customer;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	// READ
	// ------------------------------------------------------------------------------
	@GetMapping
	public Iterable<Customer> getAllCustomers() {
		// @ResponseBody means the returned object forms the body of the HTTP response
		// HTTP response does not support a Java object, so it is converted to JSON/XML automatically
		// Thus we get the results in JSON/XML format in the HTTP response
		return customerRepository.findAll();
	}

	// @PathVariable to handle dynamic id
	@GetMapping(path="/{id}")
	public Optional<Customer> getCustomer(@PathVariable int id) {
		return customerRepository.findById(id);
	}

	// CREATE
	// ------------------------------------------------------------------------------
	@PostMapping
	public ResponseEntity<?> addCustomer(@ModelAttribute Customer customer) {
		customerRepository.save(customer);
		return ResponseEntity.ok().build();
	}

	// UPDATE
	// ------------------------------------------------------------------------------
	@PutMapping("/{id}")
	public Customer updateCustomer(@PathVariable int id, @Valid @RequestBody Customer customerDetails) {
		Customer customer = customerRepository.findById(id).get();
		customer.setName(customerDetails.getName());
		customer.setEmail(customerDetails.getEmail());
		customerRepository.save(customer);
		return customer;
	}

	// DELETE
	// ------------------------------------------------------------------------------
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeCustomer(@PathVariable int id) {
		customerRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}