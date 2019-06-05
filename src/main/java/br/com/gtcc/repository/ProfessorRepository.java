package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gtcc.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	
	List<Professor> findByEmail(String email);


	@Query("SELECT COUNT(professor) FROM Professor professor")
    Long countRows();

}