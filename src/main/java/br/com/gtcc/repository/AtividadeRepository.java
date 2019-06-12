package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Atividade;

/**
 * 
 * @author Grupo 03 - Ana Claudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

	public List<Atividade> findByAno(int ano);
	
	public List<Atividade> findByAnoAndFase(int ano, int fase);
}
