package ayaz.bro.library;

import ayaz.bro.library.controllers.ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ClientController clientController;
	@Test
	void basePage() throws Exception{
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome to our library")));
	}
	@Test
	public void loginTest() throws Exception{
		this.mockMvc.perform(get("/client"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	@Test
	public void badData() throws Exception
	{
		this.mockMvc.perform(post("/login").param("user", "Alfred"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
//	@Test
//	public void correctLoginTest() throws Exception{
//		this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("zarik02789@gmail.com").password("r322z756k"))
//				.andDo(print())
//				.andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrl("/"));
//	}


}
