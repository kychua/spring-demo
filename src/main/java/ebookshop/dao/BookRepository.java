package ebookshop.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ebookshop.entity.Book;

/*
 *  CrudRepository supports a number of standard queries e.g. findAll, findById, delete.
 *  We can also define our own query methods. Spring Data JPA implements these by parsing method names.
 *    e.g. findByTitle(String title) => "SELECT b FROM book b WHERE b.title = ?"
 *    See https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-from-method-names/
 *  If we want queries that cannot be generated from method name, use
 *  1. @Query: https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-with-the-query-annotation/
 *  2. Named queries: https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-creating-database-queries-with-named-queries/
 */
public interface BookRepository extends CrudRepository<Book, Integer> {

	// implementation created by Spring Data JPA based on method name
	List<Book> findByAuthorAndQtyGreaterThan(String author, int qty);

}