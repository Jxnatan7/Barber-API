package com.haircut.haircutservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haircut.haircutservice.dto.HaircutRequest;
import com.haircut.haircutservice.model.Haircut;
import com.haircut.haircutservice.repository.HaircutRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HaircutServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HaircutRepository haircutRepository;

	@BeforeAll
	static void beforeAll() {
		mongoDBContainer.start();
	}

	@AfterAll
	static void afterAll() {
		mongoDBContainer.stop();
	}

	@Test
	void shouldCreateHaircut() throws Exception {
		HaircutRequest haircutRequest = getHaircutRequest();
		String haircutRequestString = objectMapper.writeValueAsString(haircutRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/haircut")
						.contentType(MediaType.APPLICATION_JSON)
						.content(haircutRequestString))
				.andExpect(status().isCreated());
        Assertions.assertEquals(1, haircutRepository.findAll().size());
	}

	private HaircutRequest getHaircutRequest() {
		return HaircutRequest.builder()
				.name("Corte americano")
				.price(BigDecimal.valueOf(35))
				.status(true).build();
	}

	@Test
	void shouldGetAllHaircuts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/haircut"))
				.andExpect(status().isOk());
	}
}
