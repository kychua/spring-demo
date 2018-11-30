package ebookshop.dao;

import org.springframework.data.repository.CrudRepository;

import ebookshop.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}