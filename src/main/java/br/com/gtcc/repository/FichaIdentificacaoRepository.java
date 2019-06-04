package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.model.FichaIdentificacao;

/**
 * 
 * @author Grupo 03 - Alisson, Alan e Norio
 *
 */

@Repository
public interface FichaIdentificacaoRepository extends JpaRepository<FichaIdentificacao, Long> {

	public List<FichaIdentificacao> findByTituloTrabalho(String tituloTrabalho);
	
	public List<FichaIdentificacao> findByAluno(Aluno aluno);
}
