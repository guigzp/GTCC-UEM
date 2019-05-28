package br.com.gtcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Atividade;
import br.com.gtcc.repository.atividade.AtividadeRepositoryQuery;

/**
 * 
 * @author Grupo 03 - Ana Claudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>, AtividadeRepositoryQuery{

}
