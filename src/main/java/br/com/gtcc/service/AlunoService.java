package br.com.gtcc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.repository.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	public List<Aluno> buscarTodos()
	{
		return alunoRepository.findAll();
	}
	
	public Aluno adicionar(@Valid Aluno aluno) {
        return alunoRepository.saveAndFlush(aluno);
    }
	
	public Aluno buscarPorEmail(String email) {
		
		List<Aluno> alunos = 	alunoRepository.findByEmail(email);
		if(!alunos.isEmpty())
			return alunos.get(0);
		else
			return null;
}
}
