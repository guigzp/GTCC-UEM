package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gtcc.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
	public List<Professor> findByEmail(String email);

}