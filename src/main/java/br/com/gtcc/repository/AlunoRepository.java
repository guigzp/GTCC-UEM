package br.com.gtcc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Aluno;


/**
 * Classe responsável pelo acesso a dados dos usuários
 * @author Alan Lopes
 *
 */
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	/**
	 * Busca todos os usuários de acordo com o nome
	 * @param nome
	 * @return lista de usuários
	 */
	public List<Aluno> findByNome(String nome); 
	
	/**
	 * Busca todos os usuários de acordo com o email
	 * @param email 
	 * @return
	 */
	public List<Aluno> findByEmail(String email);
	
	public List<Aluno> findByRa(Integer ra);
	
	public List<Aluno> findByAtivo(int ativo);
	
	public List<Aluno> findByNomeUsuario(String nome_usuariicho);

	
	@Query("SELECT COUNT(aluno) FROM Aluno aluno")
    public Long countRows();
	
	
}
