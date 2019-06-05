package br.com.gtcc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.gtcc.controller.AlunoController;

public class AlunoTest extends GtccApplicationTests{
	
	private MockMvc mockMvc;
	
	@Autowired
	private AlunoController alunoController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(alunoController).build();
	}
	
	@Test
	public void testPOSTSaveAlunoController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/alunos/cadastrar")
				.param("ra", "99659")
				.param("nome", "Alan Lopes de Sousa Freitas")
				.param("email", "alanlopes4@gmail.com")
				.param("nomeUsuario", "alanlopes4")
				.param("telefone", "(44) 1111-11111")
				.param("senha", "123")
				).andExpect(MockMvcResultMatchers.redirectedUrl("/alunos/cadastrar"));
	}
	
	

}
