package br.com.gtcc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Agendamento;

/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	public List<Agendamento> findByfichaIdentificacao(Long id);
	
	public List<Agendamento> findByAtivo(int ativo);
	
	public List<Agendamento> findBydataDefesa(LocalDate data);

	public List<Agendamento> findByDataDefesaLessThanEqual(LocalDate data);
}
