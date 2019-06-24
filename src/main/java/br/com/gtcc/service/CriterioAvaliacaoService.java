package br.com.gtcc.service;

import java.util.ArrayList;
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
	
	public void ativar(@Valid CriterioAvaliacao criterio)
	{
		List<CriterioAvaliacao> criteriosAtivos = criterioAvaliacaoRepository.findByAtivoTrue();
		
		//desativa os que estão ativos
		if(criteriosAtivos != null && criteriosAtivos.size() > 0)
		{
			for(CriterioAvaliacao objeto : criteriosAtivos)
			{
				objeto.setAtivo(false);
				this.criterioAvaliacaoRepository.save(objeto);
			}
		}
		
		//ativa o criterio
		criterio.setAtivo(true);
		this.criterioAvaliacaoRepository.save(criterio);
	}

	public CriterioAvaliacao buscarPorId(Long id) {
		return criterioAvaliacaoRepository.findById(id).get();
	}
	
	public CriterioAvaliacao buscarAtivo() {
		
		List<CriterioAvaliacao> criterios = this.criterioAvaliacaoRepository.findByAtivoTrue();
		CriterioAvaliacao criterio = new CriterioAvaliacao();
		criterio.setId(-1L);
		
		return criterios.size() > 0 ? criterios.get(0) : criterio;
	}
	
	public CriterioAvaliacao adicionar(@Valid CriterioAvaliacao criterio)
	{
		return criterioAvaliacaoRepository.saveAndFlush(criterio);
	}
}
