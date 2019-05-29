package br.com.gtcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Atividade;
import br.com.gtcc.repository.AtividadeRepository;

/**
 * 
 * @author Grupo 03 - Ana Claudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */

@Service
public class AtividadeService {
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	/**
	 * Responsável por retornar todos as atividades do banco de dados.
	 * @return
	 */
	public List<Atividade> listarTodos() {
        return atividadeRepository.findAll();
    }
	
	/**
	 * Função para buscar atividades de acordo com o ano.
	 * @param ano
	 * @return
	 */
	public List<Atividade> buscarPorAno(int ano) {
		
		List<Atividade> atividades = atividadeRepository.findByAno(ano);
		if(!atividades.isEmpty())
			return atividades;
		else
			return null;
}
}
