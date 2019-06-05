package br.com.gtcc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.CriterioAvaliacao;

/**
 * 
 * @author Grupo 03 - Alisson, Alan e Norio
 *
 */

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {

	
}
