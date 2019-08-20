package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gtcc.model.DeclaracaoParticipante;

public interface DeclaracaoParticipanteRepository extends JpaRepository<DeclaracaoParticipante, Long>{
	
	public List<DeclaracaoParticipante> findByNomeParticipanteAndAno(String nome, Integer ano);
	
	public List<DeclaracaoParticipante> findByNomeParticipante(String nome);

}