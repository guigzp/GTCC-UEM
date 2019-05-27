package br.com.gtcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Atividade;
import br.com.gtcc.repository.AtividadeRepository;

/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */

@Service
public class AtividadeService {
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	/**
	 * Responsável por retornar todos as atividades do banco de dados
	 * @return
	 */
	public List<Atividade> listarTodos() {
        return atividadeRepository.findAll();
    }

}
