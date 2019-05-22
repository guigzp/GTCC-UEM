package br.com.gtcc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.PerfilUsuario;
import br.com.gtcc.repository.perfilUsuario.PerfilUsuarioRepositoryQuery;


/**
 * Classe responsável pelo acesso a dados dos perfis de usuario
 * @author Gustavo Mendes
 *
 */
@Repository
public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long>, PerfilUsuarioRepositoryQuery{
	
	/**
	 * Busca todos os perfis de acordo com o nome
	 * @param nome
	 * @return lista de usuários
	 */
	public List<PerfilUsuario> findByNome(String nome); 
	
	/**
	 * Busca todos os perfis que estão ativos
	 * @param ativo
	 * @return
	 */
	public List<PerfilUsuario> findByAtivo(int ativo);
	
	
	
}
