package br.com.gtcc.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Agendamento;
import br.com.gtcc.repository.AgendamentoRepository;

/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */
@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	public List<Agendamento> buscarTodos()
	{
		return agendamentoRepository.findAll();
	}
	
	public List<Agendamento> listarTodosAtivos()
	{
		return agendamentoRepository.findByAtivo(1);
	}
	
	public Agendamento buscarPorId(Long id) {
	        return agendamentoRepository.findById(id).orElse(null);
	}
	
	public Agendamento adicionar(@Valid Agendamento agendamento) {
	    return agendamentoRepository.saveAndFlush(agendamento);
	}
	
	public Agendamento atualizar(@Valid Agendamento agendamento) {
	    return agendamentoRepository.saveAndFlush(agendamento);
	}
	
	public List<Agendamento> buscarPorData(LocalDate data)
	{
		List<Agendamento> agendamentos = agendamentoRepository.findBydataDefesa(data);
		if(!agendamentos.isEmpty())
			return agendamentos;
		else
			return null;
	}
	
	public Agendamento buscarPorFichaId(Long id)
	{
		List<Agendamento> agendamentos = agendamentoRepository.findByfichaIdentificacao(id);
		if(!agendamentos.isEmpty())
			return agendamentos.get(0);
		else
			return null;
	}

}
