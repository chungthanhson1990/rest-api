package com.restfulwebservices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@WebMvcTest(RestfulWSController.class)
public class RestfulWebServicesApplicationTests {

        @Autowired
        private MockMvc mvc;

	@Test
	public void testAPI() throws Exception {
		mvc.perform(get("/version")
      			.contentType(MediaType.APPLICATION_JSON))
      			.andExpect(status().isOk());
	}


}
