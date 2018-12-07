package ebookshop;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

// Test for Application + server, no database
@RunWith(SpringRunner.class)
@SpringBootTest // look for main config e.g. @SpringBootApplication and start application context
@AutoConfigureMockMvc
public class ApplicationTest {
	@Autowired
	private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
    	this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("My Name is so and so. This is my HOME.")));
    }

}