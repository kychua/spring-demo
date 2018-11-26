package ebookshop.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import ebookshop.dao.BookRepository;
import ebookshop.entity.Book;
import ebookshop.service.BookService;

// Integration test for BookController (with test database)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;
//
	@Autowired
	private BookService bookService;
//
	@Autowired
	private BookController bookController;

	@Test
	public void getAllBooks() throws Exception {
		Book odyssey = new Book(null, "The Odyssey", "Homer", 30, 5);
		Book hamlet = new Book(null, "Hamlet", "William Shakespeare", 32, 3);
		List<Book> books = new ArrayList<Book>();
		Collections.addAll(books, odyssey, hamlet);

		bookRepository.save(odyssey);
		bookRepository.save(hamlet);
//		Mockito.when(bookService.getAllBooks()).thenReturn(books);

		MvcResult result = mockMvc.perform(get("/api/books"))
				                  .andExpect(status().isOk())
				                  .andExpect(jsonPath("$", hasSize(2)))
				                  .andReturn();

		assertThat(result.getResponse().getContentAsString(),
				is(new ObjectMapper().writeValueAsString(books)));

	}

}