package com.jardim.paldea;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaldeaApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Retorna 400 quando o login chega sem senha")
	void loginWithoutPasswordReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/login")
						.param("email", "equipe@paldea.com")
						.param("senha", ""))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Retorna 404 quando uma planta nao existe")
	void searchMissingPlantReturnsNotFound() throws Exception {
		mockMvc.perform(get("/plantas/buscar").param("id", "999"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Retorna 200 para o catalogo")
	void catalogReturnsOk() throws Exception {
		mockMvc.perform(get("/catalogo"))
				.andExpect(status().isOk());
	}
}
