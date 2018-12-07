package ebookshop.dao;

import org.springframework.data.repository.CrudRepository;

import ebookshop.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}