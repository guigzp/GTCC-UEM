package br.com.gtcc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gtcc.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public List<Usuario> findByNome(String nome); 
	
	public List<Usuario> findByEmail(String email);
	
	public List<Usuario> findByNomeUsuario(String nome_usuario);
	
	public List<Usuario> findByAtivo(int ativo);
	
}
