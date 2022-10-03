package com.generali.burritoorderingservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BurritoOrderingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private InputStream loadFile(String path) throws IOException {
		return new ClassPathResource(path).getInputStream();
	}

	@Test
	public void testApplicationRunning() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
				.andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		assertEquals("Burrito Ordering Service Application is running.", response);
	}

	@Test
	public void testBasicOrderFlow() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/orders")
				.content(loadFile("basic-order-request.json").readAllBytes())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();

		mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
				.andExpect(content().json(StreamUtils.copyToString(loadFile("basic-order-response.json"),
						Charset.defaultCharset())))
				.andReturn();
	}

	/*
		TODO: Add more tests for the following use cases:
		 1. Test exception handling for GET and POST.
		 2. Test for incomplete transactions, those that do not update all tables, will fail instead of leaving partial
		 	update.
		 3. Test queries on pre-seeded data using schema.sql and data.sql files.
	*/
}
