package br.com.gtcc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Professor;
import br.com.gtcc.repository.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	public List<Professor> buscarTodos()
	{
		return professorRepository.findAll();
	}
	
	public Professor adicionar(@Valid Professor professor) {
        return professorRepository.saveAndFlush(professor);
    }
	
	public Professor buscarPorEmail(String email) {
		
		List<Professor> professores = 	professorRepository.findByEmail(email);
		if(!professores.isEmpty())
			return professores.get(0);
		else
			return null;
}
}
