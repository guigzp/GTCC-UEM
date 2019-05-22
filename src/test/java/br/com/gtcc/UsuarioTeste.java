package br.com.gtcc;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.gtcc.model.Permissao;
import br.com.gtcc.model.Usuario;
import br.com.gtcc.repository.filter.UsuarioFilter;
import br.com.gtcc.service.UsuarioService;

public class UsuarioTeste extends GtccApplicationTests{

	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Test
	public void buscarUsuarioPorId() {
		Usuario usuario = usuarioService.buscarPorId(1L);
		Assertions.assertThat(usuario.getId()).isEqualTo(1L);
	}
	
	@Test
	public void listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        Assertions.assertThat(usuarios.size()).isGreaterThan(0);
    }
	
	@Test
	public void buscarPorEmail() {
		
		String email = "alanlopes4@gmail.com";
		Usuario usuario = usuarioService.buscarPorEmail(email);
		
		Assertions.assertThat(usuario.getNome()).isEqualTo("Alan Lopes de Sousa Freitas");
		Assertions.assertThat(usuario.getNomeUsuario()).isEqualTo("alanlopes4");
		Assertions.assertThat(usuario.getEmail()).isEqualTo("alanlopes4@gmail.com");
		Assertions.assertThat(usuario.getCpf()).isEqualTo("090.614.349-71");
		Assertions.assertThat(usuario.getCargo()).isEqualTo("Gerente");
		Assertions.assertThat(usuario.getEndereco().getCEP()).isEqualTo("09000123");
		Assertions.assertThat(usuario.getEndereco().getCidade()).isEqualTo("Paranavai");
		Assertions.assertThat(usuario.getEndereco().getBairro()).isEqualTo("São Jorge");
		Assertions.assertThat(usuario.getEndereco().getComplemento()).isEqualTo("Casa amarela");
		
		
	}
	
	@Test
	public void adicionar() {
		
		Permissao permissao = new Permissao("ROLE_ESTOQUE");
		Set<Permissao> permissoes = new HashSet<>();
		permissoes.add(permissao);
		Usuario usuario = new Usuario("UsuarioTeste", "usuarioTeste", "usuarioTeste@gmail.com", "123", permissoes);
		usuarioService.adicionar(usuario);
		
		
	}
	
	@Test
	public void atualizar() {
		
		Usuario usuario = usuarioService.buscarPorId(1L);
		usuario.setCargo("Funcionario");
		Usuario usuarioAtualizado = usuarioService.atualizar(usuario);
		Assertions.assertThat(usuarioAtualizado.getCargo()).isEqualTo("Funcionario");
		
	}
	
	@Test
	 public void remover() {
		
		usuarioService.remover(1L);
		Usuario usuario = usuarioService.buscarPorId(1L);
		Assertions.assertThat(usuario.isAtivo()).isEqualTo(0);
		
	 }
	 
	 @Test
	 public void filtrar(){
		 
		 UsuarioFilter usuarioFilter = new UsuarioFilter();
		 usuarioFilter.setNomeUsuario("alanlopes4");
		 List<Usuario> usuariosFiltrados = usuarioService.filtrar(usuarioFilter);
		 
		 Assertions.assertThat(usuariosFiltrados.size()).isGreaterThan(0);
	 }

}
