package ebookshop.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import ebookshop.entity.Book;
import ebookshop.service.BookService;

// Tests for BookService, using embedded database
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BookService.class) // DataJpaTest only scans entities and repositories
public class BookServiceTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	@Before
	public void setUp() {
		Book odyssey = new Book(null, "The Odyssey", "Homer", 30, 5);
//		Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(odyssey));
		bookRepository.save(odyssey);
	}

	@Test
	public void getBook_validId_returnsBook() {
		Book found = bookService.getBook(1).get();
		assertThat(found.getTitle()).isEqualTo("The Odyssey");
	}
}