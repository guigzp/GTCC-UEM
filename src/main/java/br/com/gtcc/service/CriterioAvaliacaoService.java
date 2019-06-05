package br.com.gtcc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.CriterioAvaliacao;
import br.com.gtcc.repository.CriterioAvaliacaoRepository;

/**
 * 
 * @author Grupo 01 - Alisson, Alan e Norio
 *
 */

@Service
public class CriterioAvaliacaoService {

	@Autowired
	private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

	public List<CriterioAvaliacao> listarTodos() {
		return criterioAvaliacaoRepository.findAll();
	}

	public CriterioAvaliacao buscarPorId(Long id) {
		return criterioAvaliacaoRepository.findById(id).get();
	}
	
	public CriterioAvaliacao adicionar(@Valid CriterioAvaliacao criterio)
	{
		return this.criterioAvaliacaoRepository.save(criterio);
	}
}
