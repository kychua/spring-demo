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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import ebookshop.entity.Book;
import ebookshop.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	public void getAllBooks() throws Exception {
		Book odyssey = new Book(null, "The Odyssey", "Homer", 30, 5);
		Book hamlet = new Book(null, "Hamlet", "William Shakespeare", 32, 3);
		List<Book> books = new ArrayList<Book>();
		Collections.addAll(books, odyssey, hamlet);

		Mockito.when(bookService.getAllBooks()).thenReturn(books);

		MvcResult result = mockMvc.perform(get("/api/books"))
				                  .andExpect(status().isOk())
				                  .andExpect(jsonPath("$", hasSize(2)))
				                  .andReturn();

		assertThat(result.getResponse().getContentAsString(),
				is(new ObjectMapper().writeValueAsString(books)));
		System.out.println(books);
		System.out.println(result.getResponse().getContentAsString());

	}

}