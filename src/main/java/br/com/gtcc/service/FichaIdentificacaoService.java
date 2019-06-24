package br.com.gtcc.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Aluno;

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
	public List<FichaIdentificacao> listarPorAno(Integer ano){
		return fichaIdentificacaoRepository.findByAno(ano);
	}
	
	public List<Aluno> listarTodosAlunos(){
		
		List<Aluno> alunos =  new ArrayList<>();
		
		for(FichaIdentificacao fichaIdentificacao : fichaIdentificacaoRepository.findAll()){
			alunos.add(fichaIdentificacao.getAluno());
		}
		
		return alunos;
	}
	
	
	
	
	public FichaIdentificacao buscar(Long id)
	{
		return this.fichaIdentificacaoRepository.getOne(id);
	}
	
	public void deletar (FichaIdentificacao ficha) {
		this.fichaIdentificacaoRepository.delete(ficha);
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
