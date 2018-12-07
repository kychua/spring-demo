package ebookshop;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ebookshop.web.GreetingController;

// Unit tests for GreetingController, no database, no server
@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class) // instantiates only web layer, not whole context
public class WebLayerTest {
	@Autowired
	private MockMvc mockMvc;

//	@MockBean
//	private BookRepository bookRepository;
//
//	@MockBean
//	private BookService bookService;

	@Test
	public void shouldReturnDefaultPage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("My Name is so and so. This is my HOME.")));
	}

	@Test
	public void greeting_withoutName_returnsDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/greeting"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.content").value("Hello, World!"));

	}

	@Test
	public void greeting_withName_returnsCustomMessage() throws Exception {
		this.mockMvc.perform(get("/greeting").param("name", "John"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content").value("Hello, John!"));
	}
}