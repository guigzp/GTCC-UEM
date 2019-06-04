package br.com.gtcc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.repository.FichaIdentificacaoRepository;

/**
 * 
 * @author Grupo 01 - Alisson, Alan e Norio
 *
 */

@Service
public class FichaIdentificacaoService {

	@Autowired
	private FichaIdentificacaoRepository fichaIdentificacaoRepository;

	public List<FichaIdentificacao> listarTodos() {
		return fichaIdentificacaoRepository.findAll();
	}

	public List<FichaIdentificacao> buscarPorTituloTrabalho(String tituloTrabalho) {

		List<FichaIdentificacao> fichas = fichaIdentificacaoRepository.findByTituloTrabalho(tituloTrabalho);
		if (!fichas.isEmpty()) {
			return fichas;
		}

		return null;
	}
	
	public FichaIdentificacao adicionar(@Valid FichaIdentificacao ficha)
	{
		return this.fichaIdentificacaoRepository.save(ficha);
	}
}
