package com.jardim.paldea;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
	@DisplayName("Salva o usuario na sessao quando o login e valido")
	void validLoginStoresUserInSession() throws Exception {
		mockMvc.perform(post("/login")
				.param("email", "equipe@paldea.com")
				.param("senha", "paldea123"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/catalogo"))
				.andExpect(request().sessionAttribute("usuario", "Equipe"));
	}

	@Test
	@DisplayName("Redireciona o usuario logado que tenta abrir o login novamente")
	void loggedUserOpeningLoginIsRedirected() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("usuario", "Equipe");

		mockMvc.perform(get("/login").session(session))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/catalogo"));
	}

	@Test
	@DisplayName("Retorna 404 quando uma planta nao existe")
	void searchMissingPlantReturnsNotFound() throws Exception {
		mockMvc.perform(get("/plantas/buscar").param("id", "999"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Retorna 400 quando a busca recebe ID invalido")
	void searchInvalidIdReturnsBadRequest() throws Exception {
		mockMvc.perform(get("/plantas/buscar").param("id", "abc"))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Retorna 200 para o catalogo")
	void catalogReturnsOk() throws Exception {
		mockMvc.perform(get("/catalogo"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Retorna 200 ao cadastrar uma planta valida")
	void createValidPlantReturnsOk() throws Exception {
		mockMvc.perform(post("/plantas/inserir")
						.param("nome", "Orquidea Teste")
						.param("categoria", "Flores")
						.param("descricao", "Flor criada para validar o cadastro.")
						.param("preco", "39,90"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Retorna 400 quando o preco do cadastro e invalido")
	void createInvalidPriceReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/plantas/inserir")
						.param("nome", "Orquidea Teste")
						.param("categoria", "Flores")
						.param("descricao", "Flor criada para validar o cadastro.")
						.param("preco", "valor-invalido"))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Retorna 404 ao atualizar planta inexistente")
	void updateMissingPlantReturnsNotFound() throws Exception {
		mockMvc.perform(post("/plantas/atualizar")
						.param("id", "999")
						.param("nome", "Orquidea Teste")
						.param("categoria", "Flores")
						.param("descricao", "Flor criada para validar a atualizacao.")
						.param("preco", "39,90"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Retorna 404 ao apagar planta inexistente")
	void deleteMissingPlantReturnsNotFound() throws Exception {
		mockMvc.perform(post("/plantas/apagar").param("id", "999"))
				.andExpect(status().isNotFound());
	}
}
